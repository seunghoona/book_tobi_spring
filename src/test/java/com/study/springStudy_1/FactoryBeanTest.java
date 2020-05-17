package com.study.springStudy_1;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.InstanceOf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.study.springStudy_1.config.DaoFactory;
import com.study.springStudy_1.userService.beanFactory.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
public class FactoryBeanTest {
	
	@Autowired
	ApplicationContext context;
	
	
	@Test
	public void getMessageFromFactoryBean() {
		Object message =context.getBean("messageFactoryBean");
		assertThat(message, is(instanceOf(Message.class)));
		assertThat(((Message)message).getText(), is("FACTORY BEAN"));
		
	}
}
