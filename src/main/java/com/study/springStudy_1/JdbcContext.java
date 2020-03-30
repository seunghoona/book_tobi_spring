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
					obj.getClass()
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
