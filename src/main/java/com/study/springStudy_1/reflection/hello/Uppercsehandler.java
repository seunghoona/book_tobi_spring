package com.study.springStudy_1.reflection.hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Uppercsehandler implements InvocationHandler {

	Hello target;
	
	public Uppercsehandler(Hello target) {
		super();
		this.target = target;
	}



	@Override
	public Object invoke(Object object, Method method, Object[] args) throws Throwable {
		String ret = (String)method.invoke(target,args);
		return ret.toUpperCase();
	}

}
