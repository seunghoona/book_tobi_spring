package com.study.springStudy_1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class UserDao {


	private DataSource dataSource;

	public UserDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	public void add(User user) throws  SQLException {
		Connection c = null;
		PreparedStatement ps =null; 

		try {
			c = dataSource.getConnection();

			ps = c.prepareStatement("INSERT INTO USERTB VALUES(?,?,?)");

			ps.setString(1, user.getId());
			ps.setString(2, user.getName());
			ps.setString(3, user.getPassword());

			ps.executeUpdate();

		}catch (SQLException e) {
			throw e ;
		}finally {
			if(ps == null) { ps.close(); }
			if(c == null) { c.close(); }
		}

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
		//선정한 전략 클래스의 오브젝트 생성
		StatementStrategy st = new DeleteAllStatement();
		//컨텍스트 호출 전략 오브젝트 전달
		jdbcContextWithStatementStrategy(st);
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
	
	/**
	 * <pre>
	 * 1. 개요 	:전략패턴
	 * 2. 처리내용 : 클라이언트에 들어갈 내용을 분리 시키기 
	 * </pre>
	 * @Method	:jdbcContextWithStatementStrategy
	 * @date	: 2020. 3. 15.
	 * @author	: naseu
	 * @history	:
	 * ---------------- --------------- -------------------------------------------------
	 * 변경일				작성자			변경내역
	 * ---------------- --------------- -------------------------------------------------
	 * 2020. 3. 15.		naseu			최초작성
	 * ----------------------------------------------------------------------------------
	 */
	public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c  = dataSource.getConnection();
			
			//전략적 패턴 
			ps = stmt.makePreparedStatement(c);
			
			ps.executeUpdate();
		}catch (SQLException e) {
			throw e ;
		}finally {
			if(ps == null) { ps.close(); }
			if(c == null) { c.close(); }
		}
	}

}
