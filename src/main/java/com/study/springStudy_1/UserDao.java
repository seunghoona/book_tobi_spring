package com.study.springStudy_1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

public class UserDao {

	private JdbcTemplate jdbcTemplate;
	
	private DataSource dataSource;


	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate= new JdbcTemplate(dataSource); 
		
		//아직 jdbcContext를 적용하지 않는 메소드를 위해 저장 
		this.dataSource =dataSource;
	}

	public void add(User user) throws  SQLException {
		
		/*
		 * this.jdbcTemplate.workWithStatementStrategy(new StatementStrategy() {
		 * 
		 * @Override public PreparedStatement makePreparedStatement(Connection c) throws
		 * SQLException {
		 * 
		 * PreparedStatement ps =
		 * c.prepareStatement("INSERT INTO USERTB VALUES(?,?,?)"); ps.setString(1,
		 * user.getId()); ps.setString(2, user.getName()); ps.setString(3,
		 * user.getPassword()); return ps; } });
		 */
		
		this.jdbcTemplate.update("INSERT INTO USERTB VALUES(?,?,?)",user.getId(),user.getName(),user.getPassword());
	}

	public User get(String id) throws SQLException, ClassNotFoundException {

		Connection c = dataSource.getConnection();

		PreparedStatement ps = c.prepareStatement("SELECT * FROM USERTB WHERE ID = ?");


		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();

		User user =null;

		if(rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}

		rs.close();
		ps.close();
		c.close();

		return user;
	}

	/**
	 * <pre>
	 * 1. 개요 	: 삭제 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method	:deleteAll
	 * @date	: 2020. 3. 8.
	 * @author	: naseu
	 * @throws SQLException 
	 * @history	:
	 * ---------------- --------------- -------------------------------------------------
	 * 변경일				작성자			변경내역
	 * ---------------- --------------- -------------------------------------------------
	 * 2020. 3. 8.		naseu			최초작성
	 * ----------------------------------------------------------------------------------
	 */
	public void deleteAll() throws SQLException {
		/* this.jdbcTemplate.executeSql("DELETE FROM USERTB"); */
		//방법 1
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				
				return con.prepareStatement("DELETE FROM USERTB");
			}
		});
		
		//방법 2 
		this.jdbcTemplate.update("DELETE FROM USERTB");
	}

	/**
	 * <pre>
	 * 1. 개요 	: 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method	:getCount
	 * @date	: 2020. 3. 8.
	 * @author	: naseu
	 * @history	:
	 * ---------------- --------------- -------------------------------------------------
	 * 변경일				작성자			변경내역
	 * ---------------- --------------- -------------------------------------------------
	 * 2020. 3. 8.		naseu			최초작성
	 * ----------------------------------------------------------------------------------
	 */
	public int getCount() throws SQLException, ClassNotFoundException {

		Connection c = dataSource.getConnection();
		int count =0;
		PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM USERTB");

		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			count = rs.getInt(1);
		}

		rs.close();
		ps.close();
		c.close();
		return count;
	}



}
