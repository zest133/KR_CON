package com.latis.krcon.html.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;


public class HtmlWithTikaParser {

	
	private String path;
	
	
	public HtmlWithTikaParser(){
		
	}
	
	
	public File[] getFileList(){
		URL url = this.getClass().getClassLoader().getResource(path); // 이부분 수정. 
		String path = url.getPath();
		File file = new File(path);
		return file.listFiles();
	}
	
	public ArrayList<String> htmlParser(String fileName) throws IOException, SAXException,
			TikaException {
//		InputStream input = this.getClass().getClassLoader()
//				.getResourceAsStream("html/BABBADDG.htm");
//		InputStream input = this.getClass().getClassLoader()
//				.getResourceAsStream(fileName);
		FileInputStream input = new FileInputStream(new File(fileName));
		
		HtmlParser parser = new HtmlParser();
		
		LinkContentHandler linkHandler = new LinkContentHandler();
		ContentHandler textHandler = new BodyContentHandler(-1);
		ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
		
		TeeContentHandler teeHandler = new TeeContentHandler(linkHandler,
				textHandler, toHTMLHandler);
		
		Metadata metadata = new Metadata();
		ParseContext parseContext = new ParseContext();
		
		parser.parse(input, teeHandler, metadata, parseContext);

		ArrayList<String> returnList = new ArrayList<String>();
//		System.out.println(textHandler.toString());
		String temp = textHandler.toString();
		temp = temp.replaceAll("\u00a0"," ");
		
		returnList.add(temp);
		returnList.add(toHTMLHandler.toString());
		
		return returnList;
	}
	
	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}

	
}
