package com.study.springStudy_1;

public class TestUserService extends UserService {

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
		System.out.println(user.getId());
		if(user.getId().equals(this.id)) throw new TestUserServiceServiceException();
		super.upgradeLevel(user);
	}

	
}
