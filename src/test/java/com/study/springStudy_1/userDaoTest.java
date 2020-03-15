package com.study.springStudy_1;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
//테스트 메소드에서 애플리케이션 컨텍스트 구성이나 상태를 변경한다는 것을 알려줌
public class userDaoTest {
	
	@Autowired
	private UserDao dao;
	
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setUp() {
		 user1 = new User("test1","김승후","1");
		 user2 = new User("test2","이승후","2");
		 user3 = new User("test3","박승후","3");
	}
	
	@Test
	public void delAllAndAddCount() throws SQLException, ClassNotFoundException {
		

		//전체데이터 삭제 
		dao.deleteAll();
		
		assertThat(dao.getCount(), is(0));
		dao.add(user1);

		assertThat(dao.getCount(), is(1));
		dao.add(user2);

		assertThat(dao.getCount(), is(2));
		dao.add(user3);
		
		assertThat(dao.getCount(), is(3));
		
		User userget1 = dao.get(user1.getId());
		User userget2 = dao.get(user2.getId());
		
		System.out.println(userget1.getName());
		System.out.println(userget2.getName());
		
		// get 메소드 테스트 
		// 예외조건에 대한 테스트 
		assertThat(userget1.getName(), is(user1.getName()));
		assertThat(userget1.getPassword(), is(user1.getPassword()));
		
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPassword(), is(user2.getPassword()));
		
		
	}
	
	
}
