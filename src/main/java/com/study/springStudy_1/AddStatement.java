package com.study.springStudy_1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStatement implements StatementStrategy{
	
	User user;

	public AddStatement(User user) {
		super();
		this.user = user;
	}

	@Override
	public PreparedStatement makePreparedStatement(Connection c) throws SQLException {

		PreparedStatement ps = c.prepareStatement("INSERT INTO USERTB VALUES(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		return ps;
	}
	
	
}
