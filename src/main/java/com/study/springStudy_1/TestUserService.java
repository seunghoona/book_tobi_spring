package com.study.springStudy_1;

import com.study.springStudy_1.domain.User;
import com.study.springStudy_1.userService.UserServiceImpl;

public class TestUserService extends UserServiceImpl {

	private String id ;
	
	static class TestUserServiceServiceException extends RuntimeException{}
	
	public TestUserService(String id) {
		super();
		this.id = id;
	}

	public TestUserService() {
		super();
	}
	

	@Override
	protected void upgradeLevel(User user) {
		if(user.getId().equals(this.id)) throw new TestUserServiceServiceException();
		super.upgradeLevel(user);
	}

	
}
