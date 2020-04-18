package com.study.springStudy_1;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.study.springStudy_1.User.Level;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
public class UserTest {
	
	@Autowired
	User user;

	@Test
	public void upgradeLevel() {
		Level[] levels = Level.values();
		for(Level level : levels) {
			
			if(level.nextLevel() == null) continue;
			user.setLevel(level);
			user.upgradeLevel();
			assertThat(user.getLevel(), is(level.nextLevel()));
		}
	}
	
	@Test(expected = IllegalStateException.class)
	public void upgradeLevelExceotion() {
		Level[] levels = Level.values();
		for(Level level : levels) {
			user.setLevel(level);
			user.upgradeLevel();
			assertThat(user.getLevel(), is(level.nextLevel()));
		}
	}
}
