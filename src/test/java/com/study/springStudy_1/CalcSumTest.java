package com.study.springStudy_1;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.study.calculator.Calculator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
public class CalcSumTest {

	Calculator calculator;
	String numFilepath;
	
	
	@Before
	public void setUp() {
		this.calculator = new Calculator();
		this.numFilepath = getClass().getResource("number.txt").getPath();
	}
	
	
	@Test
	public void test() throws IOException {
		assertThat(calculator.calcSum(numFilepath), is(15));
	}
	
	@Test
	public void multiplayOfNumber() throws IOException {
		assertThat(calculator.calMultiply(numFilepath), is(120));
	}
	
	@Test
	public void concatenateStrings() throws IOException {
		assertThat(calculator.concatenate(numFilepath), is("12345"));
	}

}
