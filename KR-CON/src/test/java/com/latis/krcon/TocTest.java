package com.latis.krcon;

import org.junit.Test;

public class TocTest {
	@Test
	public void newTest(){
		char[] arr = "helloworld dsagasgasdgsdagasdgdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd".toCharArray(); // 해당 문자열로부터 캐릭터 배열을 선언한다
		StringBuffer sb = new StringBuffer();
		int size = 0;
		for(char c : arr) {
		    size += (c > 255) ? 2 : 1; // 조건에 따라 2 또는 1을 증가시킨다
		    sb.append(c);
		    if(size >= 80) {
		        break;
		    }
		}
		System.out.println(sb.toString());
//		return sb.toString();
	}
}
