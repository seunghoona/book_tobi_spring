package com.study.springStudy_1;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.study.springStudy_1.User.Level;

public class UserService {
	@Autowired
	UserDao userDao;
	
	
	UserLevelUpgradePolicy userLevelUpgradePolicy = new UserLevelDefault();
	
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50 ;
	public static final int MIN_RECOMMEND_FOR_GOLD  = 30 ;
	
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		for(User user: users) {
			//
			if(userLevelUpgradePolicy.canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
	}

	//업그레이드가 가능한가? 

	protected void upgradeLevel(User user) {
		userLevelUpgradePolicy.upgradeLevel(user,userDao);
	}

	public void add(User user) throws SQLException {
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}

}
