package com.latis.krcon.html.parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.junit.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

public class TestHtmlParser {

	@Test
	public void testHtmlParser() throws IOException, SAXException,
			TikaException {
		// URL url = new URL("http://www.naver.com/");
		// InputStream input = url.openStream();

		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream("html/BABBADDG.htm");

//		URL url = this.getClass().getClassLoader().getResource("html/");
//		String path = url.getPath();
//		File file = new File(path);
//		File[] fileList = file.listFiles();
		
		// BufferedInputStream bis = new BufferedInputStream(new
		// FileInputStream(new File()) )
		LinkContentHandler linkHandler = new LinkContentHandler();
		ContentHandler textHandler = new BodyContentHandler(-1);
		ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
		TeeContentHandler teeHandler = new TeeContentHandler(linkHandler,
				textHandler, toHTMLHandler);
		Metadata metadata = new Metadata();
		ParseContext parseContext = new ParseContext();
		HtmlParser parser = new HtmlParser();
		parser.parse(input, teeHandler, metadata, parseContext);
		System.out.println("title:\n" + metadata.get("title"));
		System.out.println("links:\n" + linkHandler.getLinks());
		System.out.println("text:\n" + textHandler.toString());

		System.out.println("html:\n" + toHTMLHandler.toString());
	}

	@Test
	public void testHtmlParser2() throws IOException, SAXException, TikaException {
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream("html/BABBADDG.htm");
		ParseContext pcontext = new ParseContext();

		// Html parser
		HtmlParser htmlparser = new HtmlParser();
		htmlparser.parse(input, handler, metadata, pcontext);
		System.out.println("Contents of the document:" + handler.toString());
		System.out.println("Metadata of the document:");
		String[] metadataNames = metadata.names();

		for (String name : metadataNames) {
			System.out.println(name + ":   " + metadata.get(name));
		}
	}
}
