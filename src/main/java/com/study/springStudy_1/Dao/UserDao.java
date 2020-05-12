package com.study.springStudy_1.Dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.study.springStudy_1.domain.User;

public interface UserDao {

	void setDataSource(DataSource dataSource);

	List<User> getAll();

	void add(User user);

	User get(String id);

	void deleteAll();
	
	int getCount() throws SQLException, ClassNotFoundException;

	void update(User user);

	

}