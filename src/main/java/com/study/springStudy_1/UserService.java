package com.study.springStudy_1;

import java.sql.SQLException;

import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;

import com.study.springStudy_1.Dao.UserDao;
import com.study.springStudy_1.domain.User;

public interface UserService {

	void upgradeLevels();
	void add(User user) throws SQLException;

}