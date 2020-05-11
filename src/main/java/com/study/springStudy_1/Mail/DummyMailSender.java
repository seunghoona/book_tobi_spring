package com.study.springStudy_1.Mail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DummyMailSender implements MailSender {

	@Override
	public void send(SimpleMailMessage simpleMessage) throws MailException {
		System.out.println("더미메일 발송");
	}

	@Override
	public void send(SimpleMailMessage... simpleMessages) throws MailException {
System.out.println("asdf");
	}

}
