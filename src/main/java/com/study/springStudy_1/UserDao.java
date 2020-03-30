package com.study.springStudy_1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class UserDao {

	private JdbcContext jdbcContext;
	
	private DataSource dataSource;

	public void setJdbcContext(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}
	//수정자 메소드이면서 JDBCContext에 대한 생성 DI 작업을 동시에 수행한다. 
	public void setDataSource(DataSource dataSource) {
		this.jdbcContext= new JdbcContext(); //jdbc 생성(IOC)
		this.jdbcContext.setDataSouce (dataSource);
		
		//아직 jdbcContext를 적용하지 않는 메소드를 위해 저장 
		this.dataSource =dataSource;
	}

	public void add(User user) throws  SQLException {
		this.jdbcContext.workWithStatementStrategy(new StatementStrategy (){

			@Override
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {

				PreparedStatement ps = c.prepareStatement("INSERT INTO USERTB VALUES(?,?,?)");
				ps.setString(1, user.getId());
				ps.setString(2, user.getName());
				ps.setString(3, user.getPassword());
				return ps;
			}
		});
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
		this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
			
			@Override
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement("DELETE FROM USERTB");
				return ps;
			}
		});
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
