package com.study.springStudy_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection c = DriverManager.getConnection("Jdbc:oracle:thin:@nacinaci.cafe24.com:1522:xe","hr","hr");
		return c;
	}
}
