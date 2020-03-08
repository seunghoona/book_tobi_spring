package com.study.springStudy_1;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args ) throws ClassNotFoundException, SQLException
	{
		
		/*
		 * AnnotationConfigApplicationContext context = new
		 * AnnotationConfigApplicationContext(CountingDaoFactory.class); UserDao dao =
		 * context.getBean("userDao",UserDao.class);
		 * 
		 * User user = new User(); user.setId("asdj213453"); user.setName("나승후");
		 * user.setPassword("1");
		 * 
		 * dao.add(user); System.out.println(user.getId()+"등록성공");
		 * 
		 * User user2= dao.get(user.getId()); System.out.println(user2.getName());
		 * System.out.println(user.getId()+"조회성공");
		 * 
		 * 
		 * CountingConnectionMaker ccm =
		 * context.getBean("connectionMaker",CountingConnectionMaker.class); //총 2번의
		 * DB연결을 하였기에 가운트가 최종 2번이 나오게 된다. System.out.println(ccm.counter);
		 */
	
		/*
		 * ApplicationContext context = new
		 * GenericXmlApplicationContext("applicationContext.xml"); UserDao dao =
		 * context.getBean("userDao",UserDao.class); User user = new User();
		 * user.setId("6alsdjlk"); user.setName("나승후"); user.setPassword("1");
		 * 
		 * dao.add(user); System.out.println(user.getId()+"등록성공");
		 * 
		 * User user2= dao.get(user.getId()); System.out.println(user2.getName());
		 * System.out.println(user.getId()+"조회성공");
		 */	
		
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao = context.getBean("userDao",UserDao.class);
		User user = new User();
		user.setId("test3");
		user.setName("나승후");
		user.setPassword("1");
		
		dao.add(user);
		System.out.println(user.getId()+"등록성공");
		
		User user2= dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user.getId()+"조회성공");

	}
}
