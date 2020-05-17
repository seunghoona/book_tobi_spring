package com.study.springStudy_1.reflection.hello;

public class HelloUppercase implements Hello {

	
	Hello hello ; 
	
	
	public HelloUppercase(Hello hello) {
		super();
		this.hello = hello;
	}

	//위임과 부가기능 
	@Override
	public String sayHello(String name) {
		return hello.sayHello(name).toUpperCase();
	}

	@Override
	public String sayHi(String name) {
		// TODO Auto-generated method stub
		return hello.sayHi(name).toUpperCase();
	}

	@Override
	public String sayThankYou(String name) {
		// TODO Auto-generated method stub
		return hello.sayThankYou(name).toUpperCase();
	}

}
