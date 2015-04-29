package com.latis.krcon.synonym.search;

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
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.latis.krcon.analyzer.CustomKeywordAnalyzer;
import com.latis.krcon.query.BuildQuery;

public class SynonymSearch {
	@Value("${synonymindex}")
	private String dirPath;
	
	private IndexSearcher searcher;
	private Directory dir;
	private IndexReader reader;
	
	@Autowired
	private CustomKeywordAnalyzer analyzer;
	
	@Autowired
	private BuildQuery buildQuery;
	
	@Value("${synField}")
	private String synField;
	
	
	@Value("${wordField}")
	private String wordField;
	
	
	public void init() throws IOException{
		dir = FSDirectory.open(new File(dirPath));
		reader = IndexReader.open(dir);
		searcher = new IndexSearcher(reader);
	}
	
	public void close(){
		try {
			if(reader != null){
				reader.close();
			}
			if(searcher != null){
				searcher.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String checkSynonymWord(String searchWord){
		ArrayList<Document> list = synonymSearchData(synField, searchWord);
		if(list != null){
			return list.get(0).get(wordField);
		}else{
			return "";
		}
		
	}
	
	
	public ArrayList<Document> synonymSearchData(String field, String searchWord){
		ArrayList<Document> returnList =null;
		try {
			String queryStr = buildQuery.keywordAnalyzeMakeQuery(searchWord, field);
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
	
	
}
