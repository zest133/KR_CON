package com.latis.krcon;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.latis.krcon.analyzer.CustomKeywordAnalyzer;

@ContextConfiguration(locations={
"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestAnalyzer {

	
	@Value("${synonymindex}")
	private String dirPath;
	
	private IndexSearcher searcher;
	private Directory dir;
	private IndexReader reader;
	
	/**
	 * synField=syn
wordField=word
	 */
	
	@Value("${synField}")
	private String synField;
	
	
	@Value("${wordField}")
	private String wordField;
	
	
	
	
	private CustomKeywordAnalyzer analyzer ;
	
	@Before
	public void setup() throws IOException{
		analyzer = new CustomKeywordAnalyzer(Version.LUCENE_36);
		dir = FSDirectory.open(new File(dirPath));
		reader = IndexReader.open(dir);
		searcher = new IndexSearcher(reader);
	}
	
	@After
	public void tearDown() throws IOException{
		searcher.close();
		reader.close();
	}
	
	@Test
	public void testSearch(){
		String searchWord = "life boats";
		
		ArrayList<Document> list =  synonymSearchData(synField, searchWord);
		
		assertEquals(1, list.size());
		//fail();
	}
	
	
	
	public ArrayList<Document> synonymSearchData(String field, String searchWord){
		ArrayList<Document> returnList =null;
		try {
			String queryStr = keywordAnalyzeQuery(searchWord, field);
			
			Query query = new QueryParser(Version.LUCENE_36, field, analyzer).parse(queryStr);
			TopDocs hits = searcher.search(query, searcher.maxDoc());
			returnList = dumpHits(searcher, hits, wordField);
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
			System.out.println(match.score + ":" + doc.get(fieldName));
			docList.add(doc);
		}
		return docList;
	}
	
	
	
	
	private String keywordAnalyzeQuery(String searchWord, String field)
			throws IOException {
		StringBuffer buffer = new StringBuffer();

//		TokenStream stream = analyzer.tokenStream(field, new StringReader(
//				searchWord));
//		CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
		// PositionIncrementAttribute posIncr = stream
		// .addAttribute(PositionIncrementAttribute.class);
		buffer.append("(");
//		while (stream.incrementToken()) { // C
			buffer.append("+");
			buffer.append(field);
			buffer.append(":");
			buffer.append("\"").append(searchWord).append("\"");
//		}
		buffer.append(")");

		String output = buffer.toString();

		System.out.println(output);

		return buffer.toString();
	}
	
}
