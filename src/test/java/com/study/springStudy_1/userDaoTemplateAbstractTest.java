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
public class userDaoTemplateAbstractTest {
	
	@Autowired
	private UserDao userDao;
	
	private User user1;

	@Before
	public void setUp() {
		 user1 = new User("test2","김승후","1");
	}
	
	
	
	@Test
	public void delAllAndAddCount() throws SQLException, ClassNotFoundException {
		
		//전체데이터 삭제 
		userDao.deleteAll();
		
		assertThat(userDao.getCount(), is(0));
		userDao.add(user1);
		
	}
	
	
}