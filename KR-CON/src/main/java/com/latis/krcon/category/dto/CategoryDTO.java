package com.latis.krcon.category.dto;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;

public class CategoryDTO {

	
	private String title;
	private String fileName;
	private String category;
	private String fullPath;
	private int sequence;
	private int index;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	public Document convetDocument() {
		// TODO Auto-generated method stub
		
		Document doc = new Document();
		NumericField index = new NumericField("index",Field.Store.YES,true);
		index.setIntValue(this.getIndex());		
		doc.add(index);		
		
		
		doc.add(new Field("title",this.getTitle(),Field.Store.YES,Field.Index.ANALYZED));
		doc.add(new Field("filename", this.getFileName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("category",this.getCategory(),Field.Store.YES,Field.Index.NOT_ANALYZED));
		doc.add(new Field("fullPath",this.getFullPath(),Field.Store.YES,Field.Index.NOT_ANALYZED));
		
		NumericField sequence = new NumericField("sequence",Field.Store.YES,true);
		sequence.setIntValue(this.getSequence());		
		doc.add(sequence);
		return doc;
		
		
	}
	
}
