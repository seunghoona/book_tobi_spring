package com.study.springStudy_1;

import java.sql.SQLException;
import java.util.List;

import com.study.springStudy_1.User.Level;

public class UserService {
	UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		for(User user: users) {
			Boolean changed = null;
			if(user.getLevel() == User.Level.BASIC && user.getLogin() >=50){
				user.setLevel(User.Level.SILVER);
				changed =true ;
			}else if (user.getLevel() == User.Level.SILVER && user.getLogin() >=30) {
				user.setLevel(Level.GOLD);
				changed =true ;
			}else if(user.getLevel() == User.Level.GOLD) {changed = false;}
			else {
				changed=false;
			}
			//일치하는 조건이 있으면 업데이트 
			if(changed) {userDao.update(user);}
			
		}
	}


	public void add(User user) throws SQLException {
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
}
