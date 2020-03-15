package com.study.springStudy_1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class UserDaoDeleteAll extends UserDao {


	public UserDaoDeleteAll(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * 상속을 통한 분리 
	 */
	@Override
	protected PreparedStatement makeStatement(Connection c) throws SQLException {
		PreparedStatement ps = c.prepareStatement("DELETE FROM USERTB");
		return ps;
	}

}
