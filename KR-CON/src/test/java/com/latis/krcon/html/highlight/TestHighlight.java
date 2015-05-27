package com.latis.krcon.html.highlight;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Source;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestHighlight {

	// @Test
	// public void highlightTest(){
	// String htmlText =
	// "<HTML><BODY><H3>Regulation <span>28</span></H3><H3>Regulation 29</H3></BODY></HTML>";
	//
	// Source source = new Source(htmlText);
	// source.fullSequentialParse();
	// OutputDocument document = new OutputDocument(source);
	//
	//
	// List<Element> allElem = source.getAllElements();
	//
	// for(Element elem : allElem){
	// //System.out.println(elem.getContent().getTextExtractor().toString());
	//
	// if(elem.getName().equals(HTMLElementName.H3)){
	//
	// String elemText = elem.getContent().getTextExtractor().toString();
	// elemText = elemText.replaceAll("a", "<span>a</span>");
	// document.replace(elem.getContent(), elemText);
	// }
	//
	// }
	//
	// System.out.println(document.toString());
	// // System.out.println("!");
	//
	// }

	@Test
	public void textHighlightTest() {
		String text = "The purpose of jQuery is to make it much easier to use JavaScript on your website";

		String input = "purpose a jQuery";

		String[] keywords = input.split(" ");

		StringBuffer buffer = new StringBuffer();
		for (String keyword : keywords) {
			buffer.append("("+keyword + ")|");
		}

		String patternString = buffer.toString();
		patternString = patternString.substring(0, patternString.length() - 1);
		
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);

		
		buffer = null;
		buffer = new StringBuffer();
		
		while (matcher.find()) {
			matcher.appendReplacement(buffer, "<span>" + matcher.group() + "</span>");
		}
		
		matcher.appendTail(buffer);
		text = buffer.toString();

		System.out.println("----------" + text);
	};

	public String highliter(String keyword) {
		return "<span>" + keyword + "</span>";
	}
}
