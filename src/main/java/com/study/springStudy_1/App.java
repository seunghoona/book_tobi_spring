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
		
		//UserDAO는 추상클래스이므로 객체를 직접 생성할 수 없다 
		//그러므로 상속한 클래스를 주입 해주어야한다.
	  UserDao dao = new UserDao.DUserDao();
	  
	  User user = new User();
	  user.setId("xlzcvkj");
	  user.setName("나승후");
	  user.setPassword("1");
	  
	  dao.add(user);
	  System.out.println(user.getId()+"등록성공");
	  
	  User user2= dao.get(user.getId());
	  System.out.println(user2.getName());
	  System.out.println(user.getId()+"조회성공");
	  
	}
}
