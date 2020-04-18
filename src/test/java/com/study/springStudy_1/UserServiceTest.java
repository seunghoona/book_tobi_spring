package com.study.springStudy_1;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.study.springStudy_1.User.Level;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
public class UserServiceTest {
	
	@Autowired
	UserService userService;

	List<User> users = null;
	
	@Autowired
	UserDao userDao;
	
	@Before
	public void setUp() {
		users = Arrays.asList(
					new User("bumjin","박범진"  ,"1" ,User.Level.BASIC,49,49),
					new User("coytouch","강명성","2" ,User.Level.BASIC,50,50),
					new User("drwins","신승환"  ,"3" ,User.Level.SILVER,60,40),
					new User("eadnite1","이상호","4" ,User.Level.SILVER,60,60),
					new User("freen","오민규"   ,"5" ,User.Level.GOLD,100,100)
				);
	}
	
	@Test
	//NULL체크 
	public void bean() {
		assertNotNull(this.userDao);
	}
	
	@Test
	@Ignore
	public void upgradeLevels() throws SQLException, ClassNotFoundException {
		
		userDao.deleteAll();
		
		
		for(User user: users) {
			userDao.add(user);
		} 
		userService.upgradeLevels();
		List<User> users2 =userDao.getAll();

		checkLevel(users2.get(0), User.Level.BASIC);
		checkLevel(users2.get(1), User.Level.SILVER);
		checkLevel(users2.get(2), User.Level.GOLD);
		checkLevel(users2.get(3), User.Level.GOLD);
		checkLevel(users2.get(4), User.Level.GOLD);
		
	}
	
	
	private void checkLevel(User user, Level expectedLevel) throws ClassNotFoundException, SQLException {
		User userUpdate = userDao.get(user.getId());
		assertThat(userUpdate.getLevel(), is(expectedLevel));
	}
	
	
	@Test
	public void add() throws SQLException, ClassNotFoundException {
		userDao.deleteAll();
		
		User userWithLevel    = users.get(4); //GOLDE 레벨 
		User userWithoutLevel = users.get(0); //레벨이 비어있는 사용자 로직에 따라 등록중에 Basic 레벨도 설정되어야한다.
		
		userService.add(userWithLevel);
		userService.add(userWithoutLevel);
		
		User userWithLevelRead    = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());
		
		
		assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
		assertThat(userWithoutLevelRead.getLevel(), is(userWithoutLevelRead.getLevel()));
		
	}
}
