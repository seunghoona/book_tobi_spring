package com.study.springStudy_1;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcContext {
	private DataSource dataSouce;

	public void setDataSouce(DataSource dataSouce) {
		this.dataSouce = dataSouce;
	}
	
	public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException{
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c= this.dataSouce.getConnection();
			ps = stmt.makePreparedStatement(c);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e ;
		}finally {
			if(ps != null) { try {  } catch (Exception e) {} }
			if(c != null)  { try {  } catch (Exception e) {} }
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요 	: 변하지 않는 부분을 분리시키기  
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method	:executeSql
	 * @date	: 2020. 3. 8.
	 * @author	: naseu
	 * @history	:
	 * ---------------- --------------- -------------------------------------------------
	 * 변경일				작성자			변경내역
	 * ---------------- --------------- -------------------------------------------------
	 * 2020. 3. 8.		naseu			최초작성
	 * ----------------------------------------------------------------------------------
	 */
	public void executeSql(final String query) throws SQLException{
		/*this.jdbcContext.workWithStatementStrategy(*/
		workWithStatementStrategy(
			new StatementStrategy() {
				@Override
				public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
					return c.prepareStatement(query);
				}
			});
	}
/*	
	public <T> Class<T> workWithStatementStrategyRead(StatementStrategy stmt, Class<T> className) throws SQLException, InstantiationException, IllegalAccessException{
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			c= this.dataSouce.getConnection();
			ps = stmt.makePreparedStatement(c);
			rs = ps.executeQuery();
			
			T obj  = className.newInstance();
			if(rs.next()) {
				Field[] filed = className.getDeclaredFields();
				for(Field f:filed) {
					int i =1; 
					obj.getClass())
					 rs.getString(i);
				}
			}
			return className;
		} catch (SQLException e) {
			throw e ;
		}finally {
			if(ps != null) { try {  } catch (Exception e) {} }
			if(c != null)  { try {  } catch (Exception e) {} }
		}
	}*/
	
}
