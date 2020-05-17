package com.study.springStudy_1;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.study.springStudy_1.reflection.hello.Hello;
import com.study.springStudy_1.reflection.hello.HelloTarget;
import com.study.springStudy_1.reflection.hello.HelloUppercase;


public class ReflectionHelloTest {

	@Test
	public void test() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		String name = "Spring";
		
		//length()
		assertThat(name.length(), is(6));
		
		Method lengthMethod = String.class.getMethod("length");
		assertThat((Integer)lengthMethod.invoke(name), is(6));
		
		//charAt()
		assertThat(name.charAt(0), is('S'));
		Method charAtMethod = String.class.getMethod("charAt", int.class);
		assertThat(charAtMethod.invoke(name, 0), is('S'));
	}
	
	
	@Test
	public void helloProxyTest() {
		Hello hello = new HelloTarget();
		/*assertThat(hello.sayHello("ama"), is("Helloama"));*/
		
		//프록시를 통해 타깃 오브젝트에 접근하도록 구성 
		//프록시에는 2가지 문제점이 있다. 
		//인터페이스의 모든 메소드를 구현해 위임하도록 만들어야하며, 부가 기능인 리턴 값을 대문자로 바꾸는 기능이 모든 메소드에 중복돼서 나타난다.
		
		Hello proxiedHello = new HelloUppercase(new HelloTarget());
		
		assertThat(proxiedHello.sayHello("ama"), is("HELLOAMA"));
	}

}
