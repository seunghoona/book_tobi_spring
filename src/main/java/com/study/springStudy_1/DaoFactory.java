package com.study.springStudy_1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
	@Bean
	public UserDao userDao() {
		return new UserDao(connectionMaker());
	}

	@Bean
	public ConnectionMaker connectionMaker() {
		//DI 주입 
		//운영 개발 로컬 접속정보를 쉽게 주입하여 변경할 수 있다. 
		return new LocalDBConnectionMaker();
		/* return new DConnectionMaker(); */
	}
	
}
