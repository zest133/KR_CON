package com.latis.krcon.html.category.search;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;


public class CategorySearch {

	
	private String dirPath = null;
	private IndexSearcher searcher;
	private Directory dir;
	private IndexReader reader;
	
	@Autowired
	private KeywordAnalyzer analyzer;
	
	public CategorySearch(){
		
	}
	public void init() throws IOException{
		dir = FSDirectory.open(new File(dirPath));
		reader = IndexReader.open(dir);
		searcher = new IndexSearcher(reader);
	}
	
	public String getDirPath() {
		return dirPath;
	}
	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}
	
	
	public ArrayList<Document> categorySearchData(String field, String searchWord){
		ArrayList<Document> returnList =null;
		try {
			String queryStr = andAnalyze(searchWord, field, analyzer);
			
			Query query = new QueryParser(Version.LUCENE_36, field, analyzer).parse(queryStr);
			TopDocs hits = searcher.search(query, searcher.maxDoc());
			returnList = dumpHits(searcher, hits, field);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	public ArrayList<Document> categoryAllSearchData(){
		Query allCategoryQuery = new MatchAllDocsQuery();
		ArrayList<Document> returnList = null;
		try {
			TopDocs hits = searcher.search(allCategoryQuery, searcher.maxDoc());
			returnList = dumpHits(searcher, hits, "filename");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	private ArrayList<Document> dumpHits(IndexSearcher searcher, TopDocs hits, String fieldName)
			throws IOException {
		ArrayList< Document> docList = null; 
		if (hits.totalHits == 0) {
			System.out.println("No hits");
			return null;
		}
		docList = new ArrayList<Document>();
		for (ScoreDoc match : hits.scoreDocs) {
			Document doc = searcher.doc(match.doc);
			System.out.println(match.score + ":" + doc.get(fieldName));
			docList.add(doc);
		}
		return docList;
	}
	
	
	
	
	private String andAnalyze(String searchWord, String field, Analyzer analyzer)
			throws IOException {
		StringBuffer buffer = new StringBuffer();

		TokenStream stream = analyzer.tokenStream(field, new StringReader(
				searchWord));
		CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
		// PositionIncrementAttribute posIncr = stream
		// .addAttribute(PositionIncrementAttribute.class);
		buffer.append("(");
		while (stream.incrementToken()) { // C
			buffer.append("+");
			buffer.append(field);
			buffer.append(":");
			buffer.append(term.toString());
		}
		buffer.append(")");

		String output = buffer.toString();

		System.out.println(output);

		return buffer.toString();
	}

}
