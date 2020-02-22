package com.study.springStudy_1;

public class DaoFactory {
	
	public UserDao userDao() {
		UserDao userDao = new UserDao(new DConnectionMaker());
		return userDao;
	}
	
}
