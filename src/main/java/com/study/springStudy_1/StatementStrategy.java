package com.study.springStudy_1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//전략 패턴의 시작 
public interface StatementStrategy {
	
	PreparedStatement makePreparedStatement(Connection c) throws SQLException;
	
}
