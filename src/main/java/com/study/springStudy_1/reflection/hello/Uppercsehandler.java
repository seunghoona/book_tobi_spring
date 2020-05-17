package com.study.springStudy_1.reflection.hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Uppercsehandler implements InvocationHandler {

	Object target;
	
	public Uppercsehandler(Object target) {
		super();
		this.target = target;
	}



	@Override
	public Object invoke(Object object, Method method, Object[] args) throws Throwable {
		Object ret = method.invoke(target,args);
		if(ret instanceof String) {
			return ((String)ret).toUpperCase();
		}
		
		return ret;
	}

}
