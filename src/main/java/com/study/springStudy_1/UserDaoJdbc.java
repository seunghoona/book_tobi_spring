package com.study.springStudy_1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.study.springStudy_1.User.Level;

public class UserDaoJdbc implements UserDao {

	private JdbcTemplate jdbcTemplate;
	
	private DataSource dataSource;


	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate= new JdbcTemplate(dataSource); 
	}

	private RowMapper<User> userMapper = 
			new RowMapper<User>() {
				
				@Override
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					User user = new User();
					user.setId(rs.getString("ID"));
					user.setName(rs.getString("NAME"));
					user.setPassword(rs.getString("PASSWORD"));
					user.setLevel(Level.valueOf(rs.getInt("LEVELINT")));
					user.setLogin(rs.getInt("LOGIN"));
					user.setRecommend(rs.getInt("RECOMMEND"));
					return user;
				}
			};
	

	@Override
	public List<User> getAll() {
		return this.jdbcTemplate.query("SELECT * FROM USERTB ORDER BY ID",this.userMapper);
	}
		
			
	@Override
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
		
		this.jdbcTemplate.update("INSERT INTO USERTB(ID,NAME,PASSWORD,LOGIN,RECOMMEND,LEVELINT) VALUES(?,?,?,?,?,?)"
				,user.getId(),user.getName(),user.getPassword() ,user.getLogin(),user.getRecommend(),user.getLevel().getValue());
	}

	@Override
	public User get(String id) throws SQLException, ClassNotFoundException {
		return this.jdbcTemplate.queryForObject("SELECT * FROM USERTB WHERE ID = ? ", new Object[] {id}, this.userMapper);
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
	@Override
	public void deleteAll() throws SQLException {
		/* this.jdbcTemplate.executeSql("DELETE FROM USERTB"); */
		//방법 1
		/*
		 * this.jdbcTemplate.update(new PreparedStatementCreator() {
		 * 
		 * @Override public PreparedStatement createPreparedStatement(Connection con)
		 * throws SQLException {
		 * 
		 * return con.prepareStatement("DELETE FROM USERTB"); } });
		 */
		
		//방법 2 
		/* this.jdbcTemplate.update("DELETE FROM USERTB"); */
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
	@Override
	public int getCount() throws SQLException, ClassNotFoundException {

		/*
		 * return this.jdbcTemplate.query(new PreparedStatementCreator() {
		 * 
		 * @Override public PreparedStatement createPreparedStatement(Connection con)
		 * throws SQLException {
		 * 
		 * return con.prepareStatement("SELECT COUNT(*) FROM USERTB"); } }, new
		 * ResultSetExtractor<Integer>() {
		 * 
		 * @Override public Integer extractData(ResultSet rs) throws SQLException,
		 * DataAccessException { rs.next(); return rs.getInt(1); }
		 * 
		 * });
		 */
		
			return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USERTB",Integer.class);
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		this.jdbcTemplate.update("UPDATE USERTB SET "
				+ " name     = ?"
				+ ",password = ?"
				+ ",login    = ?"
				+ ",recommend= ?"
				+ ",levelint = ?"
				+ "where id = ?",
				 user.getName()
				,user.getPassword()
				,user.getRecommend()
				,user.getLogin()
				,user.getLevel().getValue()
				,user.getId());
	}

}
