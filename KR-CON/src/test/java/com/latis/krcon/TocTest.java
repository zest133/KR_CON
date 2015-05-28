package com.latis.krcon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TocTest {
	@Test
	public void newTest() throws SAXException, IOException,
			ParserConfigurationException {

		URL url = this.getClass().getClassLoader()
				.getResource("html/b.txt");
		// 이부분 수정.
		String path = url.getPath();
		File file = new File(path);

		// File file = new File("/html/10020.en.10020.html");
		System.out.println(file.isFile());
		//
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuffer htmlBuffer = new StringBuffer(1024 * 8);
		String line = null;
		while ((line = br.readLine()) != null) {
			htmlBuffer.append(line).append("\n");
		}
		br.close();
//		  String temp =
//		 "<html>\\n<body a=ddddddd c='aaa'><tag>(a)apple</tag><aaaa /><b>hello</b><tag>orange</tag><tag>pear\\n</tag></body></html>";
		 // String regex = "(<.*?>|</.*?>)(.*?)(</.*?>|<.*?>)";
		 String regex = "(<.*?>)(.*?)(<.*?>)";
		Pattern patternTag = null;
		Matcher matcherTag = null;
		patternTag = Pattern.compile(regex);
		matcherTag = patternTag.matcher(htmlBuffer.toString());
		StringBuffer buffer = new StringBuffer(1024*8);
		while (matcherTag.find()) {
			String all = matcherTag.group(0);
			String href = matcherTag.group(1); // href
			String linkText = matcherTag.group(2); // link text
			String linkText2 = matcherTag.group(3); // link text
			
			int[] inta = new int[1];
			
			
			
			
			
			int[] intb = {1,2, 1,3, 4};
			if(linkText.length() > 0){
				
						
				String input = "rESOLUTIONS comprehens convention sAFE HIGH Recommendation";

				String[] keywords = input.split(" ");

				StringBuffer patternBuffer = new StringBuffer();
				for (String keyword : keywords) {
//					buffer.append("[ ]("+ keyword + ".*?)[ ]|");
					patternBuffer.append("^((?i)"+ keyword + ".*?)\\W|\\W((?i)"+ keyword + ".*?)\\W|");
				}
				
				String patternString = patternBuffer.toString();
				patternString = patternString.substring(0, patternString.length() - 1);

				Pattern pattern = Pattern.compile(patternString);
				Matcher matcher = pattern.matcher(linkText);
				
				StringBuffer textBuffer = new StringBuffer();

				while (matcher.find()) {
					int length = matcher.groupCount();
					for(int i = 1; i <= length ; i++){
						if(matcher.group(i) != null){
//							System.out.println(matcher.group(i));
							matcher.appendReplacement(textBuffer, " <span class='hightlight'>" + matcher.group(i)+ "</span> ");
							break;
						}
						
					}
//					matcher.appendReplacement(buffer, "<span>" + matcher.group(1)+ "</span>");
							
				}
				matcher.appendTail(textBuffer);
				matcherTag.appendReplacement(buffer, textBuffer.toString());
						
//				linkText = buffer.toString();
				
				
				
			}
			
			// matcherTag.replaceFirst(replacement)
			// temp =
//			matcherTag.replaceFirst(href + "<span aa='ddd'>" + linkText
//					+ "</span>" + linkText2);
			//
			// }
		}
		matcherTag.appendTail(buffer);
		 System.out.println(buffer.toString());

		// String text =
		// "Johns writes about this, and John Doe writes about that,"
		// + " and John Wayne writes about everything.";
		//
		// String patternString1 = "((John*) (.+?)) ";
		//
		// Pattern pattern = Pattern.compile(patternString1);
		// Matcher matcher = pattern.matcher(text);
		//
		// // String replaceAll =
		// // matcher.replaceAll("<span>"+matcher.group()+"</span>");
		// String replaceAll = matcher.replaceAll("Joe Blocks ");
		// while (matcher.find()) {
		// System.out.println(matcher.group(1));
		//
		// }
		// System.out.println("replaceAll   = " + replaceAll);
		//
		// String replaceFirst = matcher.replaceFirst("Joe Blocks ");
		// System.out.println("replaceFirst = " + replaceFirst);

	}

//	@Test
//	public void textHighlightTest() throws IOException {
//		
//		URL url = this.getClass().getClassLoader()
//				.getResource("html/a.txt");
//		// 이부분 수정.
//		String path = url.getPath();
//		File file = new File(path);
//
//		// File file = new File("/html/10020.en.10020.html");
//		System.out.println(file.isFile());
//		//
//		BufferedReader br = new BufferedReader(new FileReader(file));
//		StringBuffer buffer2 = new StringBuffer(1024 * 8);
//		String line = null;
//		while ((line = br.readLine()) != null) {
//			buffer2.append(line).append("\n");
//		}
//		br.close();
////		System.out.println(buffer2.toString());
//		
//		String text = buffer2.toString();
//		
////		String text = "The  purposedding aThe of jQuery is to make it much easier to use JavaScript on your website";
//
//		String input = "rESOLUTIONS comprehens convention sAFE HIGH Recommendation";
//
//		String[] keywords = input.split(" ");
//
//		StringBuffer buffer = new StringBuffer();
//		for (String keyword : keywords) {
////			buffer.append("[ ]("+ keyword + ".*?)[ ]|");
//			buffer.append("^((?i)"+ keyword + ".*?)\\W|\\W((?i)"+ keyword + ".*?)\\W|");
//		}
//
//		String patternString = buffer.toString();
//		patternString = patternString.substring(0, patternString.length() - 1);
//
//		Pattern pattern = Pattern.compile(patternString);
//		Matcher matcher = pattern.matcher(text);
//
//		buffer = null;
//		buffer = new StringBuffer();
//
//		while (matcher.find()) {
//			int length = matcher.groupCount();
//			for(int i = 1; i <= length ; i++){
//				if(matcher.group(i) != null){
////					System.out.println(matcher.group(i));
//					matcher.appendReplacement(buffer, " <span>" + matcher.group(i)+ "</span> ");
//					break;
//				}
//				
//			}
////			matcher.appendReplacement(buffer, "<span>" + matcher.group(1)+ "</span>");
//					
//		}
//
//		matcher.appendTail(buffer);
//		text = buffer.toString();
//
//		System.out.println("----------" + text);
//	};
}
