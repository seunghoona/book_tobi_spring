package com.study.springStudy_1.userService.beanFactory;

public class Message {
	
	private String text;

	private Message(String text) {
		super();
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public static Message newMessage(String text) {
		return new Message(text);
	}
	
}
