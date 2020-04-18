package com.study.springStudy_1;



import org.springframework.beans.factory.annotation.Autowired;

public class UserLeveEvent implements UserLevelUpgradePolicy {
	
	private final int MIN_LOGCOUNT_FOR_SILVER = 100;
	private final int MIN_RECOMMEND_FOR_GOLD = 100;
	
	@Autowired
	UserDao userDao;
	
	@Override
	//업그레이드가 가능한가? 
	public boolean canUpgradeLevel(User user) {
		User.Level currentLevel = user.getLevel();
		switch(currentLevel) {
			case BASIC  :return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
			case SILVER :return (user.getLogin() >= MIN_RECOMMEND_FOR_GOLD);
			case GOLD   :return false;
			default     :throw new IllegalArgumentException();
		}
	}

	@Override
	public void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
	}

}
