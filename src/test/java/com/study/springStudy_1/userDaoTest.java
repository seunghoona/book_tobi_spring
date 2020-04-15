package com.study.springStudy_1;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
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
		 user1 = new User("test1","김승후","1",User.Level.BASIC,1,0);
		 user2 = new User("test2","이승후","2",User.Level.SILVER,55,10);
		 user3 = new User("test3","박승후","3",User.Level.GOLD,100,40);
	}
	
	@Test
	@Ignore
	public void delAllAndAddCount() throws SQLException, ClassNotFoundException {
		

		//전체데이터 삭제 
		dao.deleteAll();
		
		assertThat(dao.getCount(), is(0));
		System.out.println(user1);
		dao.add(user1);

		assertThat(dao.getCount(), is(1));
		dao.add(user2);

		assertThat(dao.getCount(), is(2));
		dao.add(user3);
		
		assertThat(dao.getCount(), is(3));
		
		User userget1 = dao.get(user1.getId());
		checkSameUser(userget1, user1);
		User userget2 = dao.get(user2.getId());
		checkSameUser(userget2, user2);
	}
	
	// 사용자 수정을 테스트
	public void update() throws SQLException, ClassNotFoundException {
		dao.deleteAll();
		
		dao.add(user1);
		
		user1.setId("test7");
		user1.setName("반세기");
		user1.setPassword("9");
		user1.setLevel(User.Level.GOLD);
		user1.setLogin(3);
		user1.setRecommend(42);
		dao.update(user1);
		
		User user2 = dao.get(user1.getId());
		checkSameUser(user1, user2);
		
	}
	
	
	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId()          , is(user2.getId()));
		assertThat(user1.getName()        , is(user2.getName()));
		assertThat(user1.getPassword()    , is(user2.getPassword()));
		assertThat(user1.getLevel()       , is(user2.getLevel()));
		assertThat(user1.getLogin()       , is(user2.getLogin()));
		assertThat(user1.getRecommend()   , is(user2.getRecommend()));
	}
	
}
