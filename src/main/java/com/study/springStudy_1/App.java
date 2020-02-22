package com.study.springStudy_1;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args ) throws ClassNotFoundException, SQLException
	{
		
	  UserDao dao = new DaoFactory().userDao();
	  
	  User user = new User();
	  user.setId("10101010");
	  user.setName("나승후");
	  user.setPassword("1");
	  
	  dao.add(user);
	  System.out.println(user.getId()+"등록성공");
	  
	  User user2= dao.get(user.getId());
	  System.out.println(user2.getName());
	  System.out.println(user.getId()+"조회성공");
	  
	}
}
