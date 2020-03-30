package com.study.calculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

	public Integer calcSum(String filePath) throws IOException {
		BufferedReaderCallBack sumCallBack = new BufferedReaderCallBack() {
			@Override
			public Integer doSomethingWithReader(BufferedReader br) throws IOException {
				Integer sum= 0 ; 
				String line =null;
				while((line = br.readLine()) != null) {
					sum+= Integer.valueOf(line);
				}
				return sum;
			}
		};
		return fileReadTemplate(filePath,sumCallBack);
	}
	
	public Integer calMultiply(String filePath) throws IOException {
		BufferedReaderCallBack sumCallBack = new BufferedReaderCallBack() {
			@Override
			public Integer doSomethingWithReader(BufferedReader br) throws IOException {
				Integer sum= 1 ; 
				String line =null;
				while((line = br.readLine()) != null) {
					sum*= Integer.valueOf(line);
				}
				return sum;
			}
		};
		return fileReadTemplate(filePath,sumCallBack);
	}
	
	
	/**
	 * @param filePath
	 * @param callBack
	 * @return
	 * @throws IOException
	 * 설	명 : 파일을 읽어 들이는 메소드 
	 */
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
	}
}
