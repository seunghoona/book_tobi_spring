package com.study.springStudy_1;

import java.sql.SQLException;
import java.util.List;

import com.study.springStudy_1.User.Level;

public class UserService {
	UserDao userDao;
	
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
		User.Level currentLevel = user.getLevel();
		switch(currentLevel) {
			case BASIC  :return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
			case SILVER :return (user.getLogin() >= MIN_RECOMMEND_FOR_GOLD);
			case GOLD   :return false;
			default     :throw new IllegalArgumentException();
		}
	}
	
	private void upgradeLevel(User user) {
		/*
		 * if(user.getLevel() == Level.BASIC) user.setLevel(User.Level.SILVER); else
		 * if(user.getLevel() == Level.BASIC) user.setLevel(User.Level.GOLD);
		 * userDao.update(user);
		 */
		user.upgradeLevel();
		userDao.update(user);
	}

	public void add(User user) throws SQLException {
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}

}
