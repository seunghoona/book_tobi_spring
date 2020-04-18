package com.study.springStudy_1;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.study.springStudy_1.User.Level;

public class UserService {
	UserDao userDao;
	
	@Autowired
	UserLevelUpgradePolicy UserLevelUpgradePolicy;
	
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50 ;
	public static final int MIN_RECOMMEND_FOR_GOLD  = 30 ;
	
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		for(User user: users) {
			
			if(canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
	}

	//업그레이드가 가능한가? 
	private boolean canUpgradeLevel(User user) {
		return UserLevelUpgradePolicy.canUpgradeLevel(user);
	}
	
	private void upgradeLevel(User user) {
		UserLevelUpgradePolicy.upgradeLevel(user);
	}

	public void add(User user) throws SQLException {
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}

}
