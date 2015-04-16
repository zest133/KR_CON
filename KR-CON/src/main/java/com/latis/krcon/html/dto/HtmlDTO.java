package com.latis.krcon.html.dto;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.TermVector;

public class HtmlDTO {

	private String title;
	private String text;
	private String filename;
	private String path;
	private String html;
	
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public Document convetDocument() {
		// TODO Auto-generated method stub
		
		Document doc = new Document();
		
		
		doc.add(new Field("title",this.getTitle(),Field.Store.YES,Field.Index.ANALYZED, TermVector.WITH_POSITIONS_OFFSETS ));
		doc.add(new Field("text", this.getText(), Field.Store.YES, Field.Index.ANALYZED, TermVector.WITH_POSITIONS_OFFSETS));
		doc.add(new Field("html", this.getText(), Field.Store.YES, Field.Index.ANALYZED, TermVector.WITH_POSITIONS_OFFSETS));
		doc.add(new Field("filename",this.filename,Field.Store.YES,Field.Index.NOT_ANALYZED));
		
		return doc;
		
		
	}
	
	
}
