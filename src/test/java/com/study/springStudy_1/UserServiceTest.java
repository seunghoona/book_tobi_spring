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
	public void upgradeLevels() throws SQLException, ClassNotFoundException {
		
		userDao.deleteAll();
		
		for(User user: users) {
			userDao.add(user);
		} 
		userService.upgradeLevels();
		List<User> users2 =userDao.getAll();
		
		for(User user : users2) {
			System.out.println(user);
		}
		
		
		checkLevelUpgraded(users2.get(0), false);
		checkLevelUpgraded(users2.get(1), true);
		checkLevelUpgraded(users2.get(2), false);
		checkLevelUpgraded(users2.get(3), true);
		checkLevelUpgraded(users2.get(4), false);
		
	}
	
	//기존방식에서 아래방식으로 변경 
	private void checkLevel(User user, Level expectedLevel) throws ClassNotFoundException, SQLException {
		User userUpdate = userDao.get(user.getId());
		assertThat(userUpdate.getLevel(), is(expectedLevel));
	}
	
	private void checkLevelUpgraded(User user, boolean upgraded) throws ClassNotFoundException, SQLException {
		User userUpdate = userDao.get(user.getId());
		
		if(upgraded) {
			//조회한 유저의 정보가 다음 레벨과 같은가 
			assertThat(userUpdate.getLevel().nextLevel(), is(user.getLevel().nextLevel()));
		}else {
			//조회한 현재 레벨이  지금 현재 레벨과 같은가 
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
	}
	
	
	@Test
	@Ignore
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
