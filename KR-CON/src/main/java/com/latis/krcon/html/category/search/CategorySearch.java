package com.latis.krcon.html.category.search;


import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javassist.compiler.Parser;

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
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.latis.krcon.html.sort.HtmlSort;


public class CategorySearch {
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
	
//	private String preFixQueryData;
	private String searchWord;
	
	@Autowired
	private HtmlSort htmlSort;
	
	public CategorySearch(){
		
	}
	
	public void init() throws IOException{
		dir = FSDirectory.open(new File(dirPath));
		reader = IndexReader.open(dir);
		searcher = new IndexSearcher(reader);
	}
	
	
	public void close()  {
		try {
			if(searcher != null)
				searcher.close();
			if(reader != null)
				reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Document>  search(){
		return categorySearchData(categoryTreeField, searchWord);
	}
	
	public ArrayList<Document>  currentSearch(String currentCategoryTree){
		return categorySearchData(categoryTreeField, currentCategoryTree);
	}
	
	public ArrayList<Document> categoryAllSearchData(){
		Query allCategoryQuery = new MatchAllDocsQuery();
		htmlSort.addSortList(new SortField(categoryTreeField,SortField.STRING));
		
		ArrayList<Document> list = null;
		try {
			TopDocs hits = searcher.search(allCategoryQuery, searcher.maxDoc(), htmlSort.getSort());
			list = dumpHits(searcher, hits, categoryTreeField);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<Document>  categorySubTreeSearchData(){
		String searchWord = this.searchWord + anonymousData;
		return categorySearchData(categoryTreeField, searchWord);
	}
	
	
	public ArrayList<Document> categorySearchData(String field, String searchWord){
		ArrayList<Document> returnList =null;
		try {
			String queryStr = andAnalyze(searchWord, field, analyzer);
			htmlSort.addSortList(new SortField(categoryTreeField,SortField.STRING));
			Query query = new QueryParser(Version.LUCENE_36, field, analyzer).parse(queryStr);
			TopDocs hits = searcher.search(query, searcher.maxDoc(), htmlSort.getSort());
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
	
	public void checkSubCategory(Document document, JSONObject jsonObject)
			throws IOException, ParseException {
		String queryStr = andAnalyze(document.get("categoryTree")
				+ anonymousData, categoryTreeField, analyzer);

		Query query = new QueryParser(Version.LUCENE_36, categoryTreeField,
				analyzer).parse(queryStr);
		TopDocs hits = searcher.search(query, searcher.maxDoc());

		if (hits.totalHits == 0) {
		} else {
			jsonObject.put("isFolder", "true");
			jsonObject.put("isLazy", "true");
		}

	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	
}
