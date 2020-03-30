package com.study.calculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

	public Integer calcSum(String filePath) throws IOException {
		LineCallback subCallBack = new LineCallback() {
			
			@Override
			public Integer doSomeThingWithLine(String line, Integer value) {
				return value+Integer.valueOf(line);
			}
		};
		return lineReadTemplate(filePath,subCallBack,0);
	}
	
	public Integer calMultiply(String filePath) throws IOException {
		LineCallback sumCallBack = new LineCallback() {
			@Override
			public Integer doSomeThingWithLine(String line, Integer value) {
				return value+Integer.valueOf(line);
			}
		};
		return lineReadTemplate(filePath,sumCallBack,1);
	}
	
	
/*	*//**
	 * @param filePath
	 * @param callBack
	 * @return
	 * @throws IOException
	 * 설	명 : 파일을 읽어 들이는 메소드 
	 *//*
	public Integer fileReadTemplate(String filePath, BufferedReaderCallBack callBack) throws IOException {
		BufferedReader br = null;
		try {
			
		br = new BufferedReader(new FileReader(filePath));
		
		int ret =callBack.doSomethingWithReader(br);
		return ret;
		
		}catch(IOException e) {
			System.out.println(e.getMessage());
			throw e;
		}finally {
			if(br!=null) {
				try {br.close();}catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}*/
	
	public Integer lineReadTemplate(String filePath, LineCallback callback, int initVal) throws IOException{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
			Integer res = initVal;
			String line = null;
			while((line = br.readLine()) != null){
				//각 라인의 내용을 가지고 계산하는 작업만 콜백에게 맡긴다.
				res = callback.doSomeThingWithLine(line, res);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return initVal;
	}
}
