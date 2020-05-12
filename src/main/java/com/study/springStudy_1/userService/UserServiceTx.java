package com.study.springStudy_1.userService;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.study.springStudy_1.UserLevelUpgradePolicy;
import com.study.springStudy_1.UserService;
import com.study.springStudy_1.Dao.UserDao;
import com.study.springStudy_1.domain.User;

public class UserServiceTx implements UserService {

	@Autowired
	PlatformTransactionManager transactionManager;

	@Autowired
	UserService userService;

	public void setTransacitionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	@Override
	public void upgradeLevels() {
		System.out.println("트랜잭션 진입여부 =========================");
		TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			userService.upgradeLevels();
			this.transactionManager.commit(status);
		} catch (Exception e) {
			this.transactionManager.rollback(status);
			throw e;
		} finally {
			// DB 커넥션을 안전하게 닫느다.
		}
		System.out.println("트랜잭션 진입여부 종료=========================");
	}

	@Override
	public void add(User user) throws SQLException {
		userService.add(user);
	}

}
