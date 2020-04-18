package com.study.springStudy_1;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

public interface UserDao {

	void setDataSource(DataSource dataSource);

	List<User> getAll();

	void add(User user) throws SQLException;

	User get(String id) throws SQLException, ClassNotFoundException;

	void deleteAll() throws SQLException;
	
	int getCount() throws SQLException, ClassNotFoundException;

	void update(User user);

	

}