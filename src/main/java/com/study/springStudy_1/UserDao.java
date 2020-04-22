package com.study.springStudy_1;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

public interface UserDao {

	void setDataSource(DataSource dataSource);

	List<User> getAll();

	void add(User user);

	User get(String id);

	void deleteAll();
	
	int getCount() throws SQLException, ClassNotFoundException;

	void update(User user);

	

}