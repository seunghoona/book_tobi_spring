package com.study.springStudy_1;

import static com.study.springStudy_1.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static com.study.springStudy_1.UserService.MIN_RECOMMEND_FOR_GOLD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;;

public class UserLevelDefault implements UserLevelUpgradePolicy {

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
	public void upgradeLevel(User user, UserDao userDao) {
		user.upgradeLevel();
		userDao.update(user);
	}

}
