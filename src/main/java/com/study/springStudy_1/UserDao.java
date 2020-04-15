package com.study.springStudy_1;

import java.sql.SQLException;

import javax.sql.DataSource;

public interface UserDao {

	void setDataSource(DataSource dataSource);

	void add(User user) throws SQLException;

	User get(String id) throws SQLException, ClassNotFoundException;

	void deleteAll() throws SQLException;
	
	int getCount() throws SQLException, ClassNotFoundException;

	void update(User user);
	

}