package com.study.springStudy_1;

import static com.study.springStudy_1.userService.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static com.study.springStudy_1.userService.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import com.study.springStudy_1.TestUserService.TestUserServiceServiceException;
import com.study.springStudy_1.Dao.UserDao;
import com.study.springStudy_1.config.DaoFactory;
import com.study.springStudy_1.domain.User;
import com.study.springStudy_1.domain.User.Level;
import com.study.springStudy_1.userLevelUpgradePolicyImpl.UserLevelDefault;
import com.study.springStudy_1.userService.Transactionhandler;
import com.study.springStudy_1.userService.UserServiceImpl;
import com.study.springStudy_1.userService.UserServiceTx;
import com.sun.mail.iap.Argument;;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
public class UserServiceTest {
	
	
	
	static class MockMailSender implements MailSender {
		List<String> request = new ArrayList<String>();
		
		public List<String> getRequest() {
			return request;
		}

		@Override
		public void send(SimpleMailMessage simpleMessage) throws MailException {
			request.add(simpleMessage.getTo()[0]);
		}

		@Override
		public void send(SimpleMailMessage... simpleMessages) throws MailException {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	static class MockUserDao implements UserDao{

		private List<User> users;
		private List<User> updated = new ArrayList();
	
		public MockUserDao(List<User> users) {
			this.users = users;
		}

		@Override
		public List<User> getAll() {
			return this.users;
		}
		
		@Override
		public void update(User user) {
			updated.add(user);
		}
		
		@Override public void add(User user) { throw new UnsupportedOperationException(); }
		@Override public User get(String id) { throw new UnsupportedOperationException(); }
		@Override public void deleteAll() { throw new UnsupportedOperationException(); }
		@Override public int getCount() throws SQLException, ClassNotFoundException { throw new UnsupportedOperationException(); }
		@Override public void setDataSource(DataSource dataSource) { throw new UnsupportedOperationException(); }
//		
	}
	
	
	@Autowired
	UserService userService;

	List<User> users = null;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	UserLevelUpgradePolicy userLeveUpgradePlicy;
	
	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Autowired
	MailSender mailSender;
	
	
	
	@Before
	public void setUp() {
		users = Arrays.asList(
					new User("bumjin","박범진"  ,"1" ,User.Level.BASIC     ,MIN_LOGCOUNT_FOR_SILVER-1  ,49,"a"),
					new User("coytouch","강명성","2" ,User.Level.BASIC     ,MIN_LOGCOUNT_FOR_SILVER    ,50,"b"),
					new User("drwins","신승환"  ,"3" ,User.Level.SILVER    ,MIN_LOGCOUNT_FOR_SILVER+10 ,60,"c"),
					new User("eadnite1","이상호","4" ,User.Level.SILVER    ,60                         ,MIN_RECOMMEND_FOR_GOLD-1,"d"),
					new User("freen","오민규"   ,"5" ,User.Level.GOLD      ,100                        ,MIN_RECOMMEND_FOR_GOLD,"e")
				);
		
	}
	
	@Test
	@Ignore
	public void mockUpgradeLevels() throws Exception{
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		UserLevelDefault userLevelDefault = new UserLevelDefault();
		
		//목업생성
		UserDao mockUserDao = Mockito.mock(UserDao.class);

		when(mockUserDao.getAll()).thenReturn(this.users);

		userServiceImpl.setUserDao(mockUserDao);
		userServiceImpl.setUserLevelUpgradePolicy(userLevelDefault);
		userLevelDefault.setUserDao(mockUserDao);
		
		
		userServiceImpl.upgradeLevels();
		verify(mockUserDao,times(3)).update(any(User.class));
		verify(mockUserDao).update(users.get(1));
		assertThat(users.get(1).getLevel(),is(Level.SILVER) );
		verify(mockUserDao).update(users.get(3));
		assertThat(users.get(3).getLevel(),is(Level.GOLD) );
		
		
	}
	
	
	@Test
	@Ignore
	public void upgradeLevels() throws SQLException, ClassNotFoundException {
		
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		MockUserDao mockUserDao = new MockUserDao(this.users);

		UserLevelDefault userLevelUpgradePolicy = new UserLevelDefault();

		userServiceImpl.setUserDao(mockUserDao);
		userServiceImpl.setUserLevelUpgradePolicy(userLevelUpgradePolicy);
		userServiceImpl.upgradeLevels();

		List<User> updated = mockUserDao.getAll();
		
		assertThat(updated.size(), is(5));
		
		
		checkUserAndLevel(updated.get(0),"bumjin"   ,Level.BASIC);
		checkUserAndLevel(updated.get(1),"coytouch" ,Level.SILVER);
		
		
		
		List<User> request = mockUserDao.getAll();
		assertThat(request.size(), is(5));
		assertThat(request.get(0).getEmail(), is(users.get(0).getEmail()));
		assertThat(request.get(1).getEmail(), is(users.get(1).getEmail()));
		
	}
	
	private void checkUserAndLevel(User user, String expectedId, Level expectedLevel) {

		assertThat(user.getId()   , is(expectedId));
		assertThat(user.getLevel(), is(expectedLevel));
	}

	//기존방식에서 아래방식으로 변경 
	private void checkLevel(User user, Level expectedLevel) throws ClassNotFoundException, SQLException {
		User userUpdate = userDao.get(user.getId());
		assertThat(userUpdate.getLevel(), is(expectedLevel));
	}
	
	private void checkLevelUpgraded(User user, boolean upgraded) {
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
	

	@Test
	public void UpgradeAllorNothing() {
		TestUserService testUserService = new TestUserService(users.get(3).getId());		
		testUserService.setUserDao(this.userDao);
		testUserService.setMailSender(mailSender);
		testUserService.setUserLevelUpgradePolicy(this.userLeveUpgradePlicy);
		
		
		//강제 예외를 발생시키기 위해서 TEST에서 직접적으로 객체를 생성한경우 nULL 문제가 발생하였다 해당 문제를 해결하기 위해서 
		//직접 해당 객체를 주입해주었다.
		/*UserServiceTx userServiceTx = new UserServiceTx();
		userServiceTx.setTransacitionManager(this.transactionManager);
		userServiceTx.setUserService(testUserService);*/
		
		Transactionhandler txHandler = new Transactionhandler();
		txHandler.setTarget(testUserService);
		txHandler.setTransactionManager(transactionManager);
		txHandler.setPattern("upgradeLevels");
		
		UserService txUserService = (UserService)Proxy.newProxyInstance(
				getClass().getClassLoader(),
				new Class[] {UserService.class}, txHandler);
		
		userDao.deleteAll();
		for(User user: users) userDao.add(user);
		
		try {
			testUserService.upgradeLevels();
			
			fail("TestUserServiceExcpetion expected");
			//실젝 오류가 발생하여 3번째 데이터는 오류가 나서 처리되지 않으 
		}catch(TestUserServiceServiceException e) {}
		//현재 이전 업그레이드가 이루어졌는지 체크 
		checkLevelUpgraded(users.get(1), true);
	}
}
