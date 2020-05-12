package com.study.springStudy_1.userService;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.study.springStudy_1.UserLevelUpgradePolicy;
import com.study.springStudy_1.UserService;
import com.study.springStudy_1.Dao.UserDao;
import com.study.springStudy_1.domain.User;
import com.study.springStudy_1.domain.User.Level;

public class UserServiceImpl implements UserService {

	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;

	@Autowired
	UserDao userDao;

	@Autowired
	UserLevelUpgradePolicy userLevelUpgradePolicy;

	@Autowired
	private MailSender mailSender;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
		this.userLevelUpgradePolicy = userLevelUpgradePolicy;
	}

	private void sendUpgradeEmail(User user) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		try {
			mailMessage.setTo(user.getEmail());
			mailMessage.setFrom("userAdmin@ksug.org");
			mailMessage.setSubject("업그레이드 안내 ");
			mailMessage.setText("사용자님의 등급이 " + user.getLevel().name());

			this.mailSender.send(mailMessage);
		} catch (Exception e) {
			new RuntimeException();
		}

	}

	@Override
	public void upgradeLevels() {
		updateLevelsInternal();
	}

	private void updateLevelsInternal() {
		List<User> users = userDao.getAll();
		for (User user : users) {

			if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
				this.upgradeLevel(user);
			}
		}
	}

	// 업그레이드가 가능한가?
	protected void upgradeLevel(User user) {
		userLevelUpgradePolicy.upgradeLevel(user);
		sendUpgradeEmail(user);
	}

	/* (non-Javadoc)
	 * @see com.study.springStudy_1.UserService1#add(com.study.springStudy_1.domain.User)
	 */
	@Override
	public void add(User user) throws SQLException {
		if (user.getLevel() == null)
			user.setLevel(Level.BASIC);
		userDao.add(user);
	}

}
