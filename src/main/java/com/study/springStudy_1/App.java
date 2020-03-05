package com.study.springStudy_1;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args ) throws ClassNotFoundException, SQLException
	{
		
  ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
  UserDao dao  = context.getBean("userDao",UserDao.class);
  
  User user = new User();
  user.setId("asdj1");
  user.setName("나승후");
  user.setPassword("1");
  
  dao.add(user);
  System.out.println(user.getId()+"등록성공");
  
  User user2= dao.get(user.getId());
  System.out.println(user2.getName());
  System.out.println(user.getId()+"조회성공");
  
	}
}
