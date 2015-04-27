package com.latis.krcon.synonym.write;

import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.document.Field.TermVector;

public class SynonymDTO {

	private ArrayList<String> synList = new ArrayList<String>();
	private String word;
	
	public ArrayList<String> getSynList() {
		return synList;
	}
	public void setSynList(ArrayList<String> synList) {
		this.synList = synList;
	}
	public void addSynList(String syn){
		synList.add(syn);
	}
	
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	public Document convetDocument() {
		// TODO Auto-generated method stub
		Document doc = new Document();
		for(String str : this.getSynList()){
			doc.add(new Field("syn",str,Field.Store.YES,Field.Index.NOT_ANALYZED ));
		}
		doc.add(new Field("word",this.getWord(),Field.Store.YES,Field.Index.NOT_ANALYZED ));
		return doc;
	}
	
}
