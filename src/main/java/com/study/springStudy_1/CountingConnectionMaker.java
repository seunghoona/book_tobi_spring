package com.study.springStudy_1;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
	
	int counter =0;
	private ConnectionMaker realConnectionMaker;
	
	public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
		this.realConnectionMaker = realConnectionMaker;
	}

	@Override
	public Connection makeConnction() throws ClassNotFoundException, SQLException {
		this.counter++;
		return realConnectionMaker.makeConnction();
	}

	public int getCounter() {
		return this.counter;
	}
	
}
