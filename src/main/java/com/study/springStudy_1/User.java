package com.study.springStudy_1;

import java.util.Date;

public class User {
	
	String id ;
	String name ;
	String password;
	Level level;
	int Login;
	int Recommend;
	String email;
	
	// 가장 최근에 레벨을 변경한 일자를 User오브젝트에 남기기 
	Date lastUpgraded;
	
	
	enum Level{
		
		 GOLD  (3,null)
		,SILVER(2,GOLD)
		,BASIC (1,SILVER);
		
		private final int value ;
		private final Level next;
		
		private Level(int value, Level next) {
			this.value = value;
			this.next  = next;
		}
		
		public int getValue() {
			return value;
		}
		//값으로부터 LEVEL 타입 오브젝트를 가져오도록 만든 스태틱 메소드 
		public static  Level valueOf(int value){
			switch(value) {
			case 1: return BASIC;
			case 2: return SILVER;
			case 3: return GOLD;
			default : throw new AssertionError("열려지지 않는 값입니다. "+value);
			
			}
		}
		
		public Level nextLevel() {
			return this.next;
		}
		
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(String id, String name, String password, Level level, int login, int recommend,String email) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.level = level;
		this.Login = login;
		this.Recommend = recommend;
		this.email = email;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getLogin() {
		return Login;
	}

	public void setLogin(int login) {
		Login = login;
	}

	public int getRecommend() {
		return Recommend;
	}

	public void setRecommend(int recommend) {
		Recommend = recommend;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void upgradeLevel() {
		Level nextLevel = this.level.nextLevel();
		if(nextLevel == null) {
			throw new IllegalStateException(this.level+"은 업그레이드가 불가능합니다.");
		}else {
			this.level = nextLevel;
			this.lastUpgraded = new Date();
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", level=" + level + ", Login=" + Login
				+ ", Recommend=" + Recommend + "]";
	}
	
}

