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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;


@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestCategorySearch {

	
	@Value("${fileindex}")
	private String dirPath;
	
	private IndexSearcher searcher;
	private Directory dir;
	private IndexReader reader;
	
	@Value("${categoryTreeField}")
	private String categoryTreeField;
	
	
	@Value("${anonymousData}")
	private String anonymousData;
	
	@Autowired
	private KeywordAnalyzer analyzer;
	
	
	private String preFixQueryData;
	
	
	

	@Before
	public void setup() throws IOException{
		dir = FSDirectory.open(new File(dirPath));
		reader = IndexReader.open(dir);
		searcher = new IndexSearcher(reader);
		preFixQueryData = "0000.00e0.1530";
	}
	
	@After
	public void tearDown() throws IOException{
		searcher.close();
		reader.close();
	}
	
	public String getDirPath() {
		return dirPath;
	}
	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}
	
	@Test
	public void testSearch(){
		String searchWord = "0000.00e0.1530";
		
		ArrayList<Document> list =  categorySearchData(categoryTreeField, searchWord);
		
		assertEquals(1, list.size());
		//fail();
	}
	
	@Test
	public void testCategoryAllSearchData(){
		Query allCategoryQuery = new MatchAllDocsQuery();
		try {
			TopDocs hits = searcher.search(allCategoryQuery, searcher.maxDoc());
			dumpHits(searcher, hits, categoryTreeField);
			
			assertNotSame(0, hits.scoreDocs.length);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCategorySubTreeSearchData(){
		String searchWord = this.preFixQueryData+anonymousData;
		
		
		
		ArrayList<Document> list =  categorySearchData(categoryTreeField, searchWord);
		
		assertEquals(1, list.size());
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
//			System.out.println(match.score + ":" + doc.get(fieldName));
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
	
	public String getPreFixQueryData() {
		return preFixQueryData;
	}

	public void setPreFixQueryData(String preFixQueryData) {
		this.preFixQueryData = preFixQueryData;
	}
	
	public void buildRootCategory() throws IOException, ParseException{
		String searchWord = "0000.00e0.1530";
		
		ArrayList<Document> list =  categorySearchData(categoryTreeField, searchWord);
		
		
		JSONArray array = new JSONArray();
		for(Document document : list){
			JSONObject jsonObject = new JSONObject();
			
			jsonObject.put("categoryTree", document.get("categoryTree"));
			jsonObject.put("categoryTitle", document.get("categoryTitle"));
			
			
			checkSubCategory(document, jsonObject);
			
			jsonObject.put("isFolder", "true");
			jsonObject.put("isLazy", "true");
			
			array.add(jsonObject);
		}
		
		System.out.println(array.toString());
		/*
		 * private String categoryTree; // category tree struct
	private int categoryTextId;	
	private int categoryId;	
	private String localeKey;	//locale info
	private String categoryTitle;	//breadcrumbs
	private String categoryDesc;	//desc
	
	private String html;			//html
	private String text;			//html text
	private String breadcrumb;
		 */
		
	}

	private void checkSubCategory(Document document, JSONObject jsonObject) throws IOException,
			ParseException {
		String queryStr = andAnalyze(document.get("categoryTree")+anonymousData, categoryTreeField, analyzer);
		
		Query query = new QueryParser(Version.LUCENE_36, categoryTreeField, analyzer).parse(queryStr);
		TopDocs hits = searcher.search(query, searcher.maxDoc());
		
		
		if (hits.totalHits == 0) {
		}else{
			jsonObject.put("isFolder", "true");
			jsonObject.put("isLazy", "true");
		}
	}

}
