package com.latis.krcon.xml.write;

import org.junit.Before;
import org.junit.Test;

public class XmlPreProcessTest {

//	private String temp = "<doc>text sample text <x>test</x> words lipsum words words <x>text</x> some other text </doc><foobar>a</foobar> some more text flop</doc>";
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void test(){
		String temp = "<doc>text sample text<x>test</x>words lipsum\n\n words words<x>text</x> some other text </doc><foobar>a</foobar>&nbsp; some more text \n\nflop</doc>\n\n";
		String temp2 = temp.replaceAll("\\<", " <");
		temp2 = temp2.replaceAll("\\>", "> ");
	}
	
}
