package com.study.springStudy_1;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;

import com.study.springStudy_1.Mail.DummyMailSender;

@Configuration
public class DaoFactory {
	
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(oracle.jdbc.driver.OracleDriver.class);
		dataSource.setUrl("Jdbc:oracle:thin:@nacinaci.cafe24.com:1522:xe");
		dataSource.setUsername("hr");
		dataSource.setPassword("hr");
		return dataSource;
	}
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDaoJdbc();
		userDao.setDataSource(dataSource());
		return userDao;
	}
	
	//트랜잭션 Bean 추가
	@Bean
	public PlatformTransactionManager transactionManager () {
		return new DataSourceTransactionManager(dataSource());
	}
	
	@Bean
	public UserService UserService() {
		UserService userService = new UserService();
		userService.setUserDao(userDao());
		userService.setTransactionManager(transactionManager());
		userService.setMailSender(mailSender());
		return userService;
	}
	
	@Bean 
	public User user() {
		return new User();
	}
	
	@Bean
	public UserLevelUpgradePolicy userLevelUpgradePolicy() {
		return new UserLevelDefault();
	}
	
	@Bean
	public MailSender mailSender() {
		/*MailSender javaMailSenderImpl = new JavaMailSenderImpl();*/
		MailSender mailSender =  new DummyMailSender();
		/*mailSender.host();*/
		return mailSender;
	}
	
	/*
	 * @Bean public ConnectionMaker connectionMaker() { //DI 주입 //운영 개발 로컬 접속정보를 쉽게
	 * 주입하여 변경할 수 있다. return new LocalDBConnectionMaker(); return new
	 * DConnectionMaker(); }
	 */
}
