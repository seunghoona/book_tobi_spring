package com.study.springStudy_1;

public interface UserLevelUpgradePolicy {
	boolean canUpgradeLevel(User user);
	void upgradeLevel(User user);
}
