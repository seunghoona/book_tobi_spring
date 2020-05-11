package com.study.springStudy_1;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.study.springStudy_1.User.Level;

public class UserService {

	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;

	@Autowired
	UserDao userDao;

	@Autowired
	UserLevelUpgradePolicy userLevelUpgradePolicy;

	@Autowired
	PlatformTransactionManager transactionManager;

	@Autowired
	private MailSender mailSender;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
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

	public void upgradeLevels() {
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			List<User> users = userDao.getAll();
			for (User user : users) {

				if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
					this.upgradeLevel(user);
				}
			}
			this.transactionManager.commit(status);
		} catch (Exception e) {
			this.transactionManager.rollback(status);
			throw e;
		} finally {
			// DB 커넥션을 안전하게 닫느다.
		}
	}

	// 업그레이드가 가능한가?

	protected void upgradeLevel(User user) {
		userLevelUpgradePolicy.upgradeLevel(user);
		sendUpgradeEmail(user);
	}

	public void add(User user) throws SQLException {
		if (user.getLevel() == null)
			user.setLevel(Level.BASIC);
		userDao.add(user);
	}

}
