package com.study.calculator;

public interface LineCallback<T> {
	T doSomeThingWithLine(String line , T value);
}
