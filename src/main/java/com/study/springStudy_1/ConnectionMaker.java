package com.study.springStudy_1;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
	public Connection makeConnction() throws ClassNotFoundException, SQLException ;
}