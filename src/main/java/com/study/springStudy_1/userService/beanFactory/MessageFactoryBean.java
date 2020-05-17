package com.study.springStudy_1.userService.beanFactory;

import org.springframework.beans.factory.FactoryBean;

public class MessageFactoryBean implements FactoryBean<Message> {

	String text;

	
	//오브젝트를 생성할 떄 필요한 정보를 팩토리 빈의 프로퍼티로 설정해서 
	//대신 DI를 받을 수있게 한다. 주입된 정보는 오브젝트 생성중에 사용된다.
	public void setText(String text) {
		this.text = text;
	}


	/* 실제 빈으로 사용될 오브젝트를 직정 생성한다. 
	 * 코드를 이용하기 때문에 복잡한 방식의 오브젝트 
	 * 생성과 초기화 작업도 가능하다
	 * */
	@Override
	public Message getObject() throws Exception {
		
		return Message.newMessage(this.text);
	}

	@Override
	public Class<? extends Message> getObjectType() {
		return Message.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
