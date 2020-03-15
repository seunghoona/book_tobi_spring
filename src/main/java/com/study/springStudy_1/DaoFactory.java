package com.study.springStudy_1;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

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
		return new UserDao (dataSource());
	}

	/*
	 * @Bean public ConnectionMaker connectionMaker() { //DI 주입 //운영 개발 로컬 접속정보를 쉽게
	 * 주입하여 변경할 수 있다. return new LocalDBConnectionMaker(); return new
	 * DConnectionMaker(); }
	 */
}
