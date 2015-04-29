package com.latis.krcon.html.category.search.dao;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.latis.krcon.html.category.search.CategorySearch;
import com.latis.krcon.html.search.EnglishHtmlSearch;
import com.latis.krcon.html.search.highlight.HtmlHighlight;

public class CategorySearchDAOImpl implements CategorySearchDAO {

	@Autowired
	private CategorySearch categorySearch;
	
	@Autowired
	private EnglishAnalyzer englishAnalyzer;
	
	
	@Autowired
	private HtmlHighlight htmlHighlight;
	
	@Value("${rootCategoryTreeName}")
	private String rootCategoryTreeName;
	
	
	@Value("${htmlField}")
	private String htmlField;
	
	
	public CategorySearchDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public JSONArray getRootCategory() {
		// TODO Auto-generated method stub
		JSONArray returnArray = null;
		try {
			categorySearch.init();
			categorySearch.setSearchWord(rootCategoryTreeName);
			ArrayList<Document> list = categorySearch.search();

			returnArray = convertJsonArray(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} finally {
			categorySearch.close();
		}

		return returnArray;
	}

	@Override
	public JSONArray getSubCategory(String selectedCategoryTree) {
		// TODO Auto-generated method stub
		JSONArray returnArray = null;
		try {
			categorySearch.init();
			categorySearch.setSearchWord(selectedCategoryTree);
			ArrayList<Document> list = categorySearch
					.categorySubTreeSearchData();
			returnArray = convertJsonArray(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} finally {
			categorySearch.close();
		}

		return returnArray;
	}

	@Override
	public String getCurrentCategoryHTML(String selectedCategoryTree, String highlightQuery) {
		// TODO Auto-generated method stub
		String returnVal = "";
		try {
			categorySearch.init();
			ArrayList<Document> list = categorySearch
					.currentSearch(selectedCategoryTree);
			returnVal = convertHtmlText(list, highlightQuery);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			categorySearch.close();
		}
		
		return returnVal;
	}

	public JSONArray convertJsonArray(ArrayList<Document> list){
		JSONArray array = new JSONArray();
		
		try {
			categorySearch.init();
			for (Document document : list) {
				JSONObject jsonObject = new JSONObject();
				
				jsonObject.put("key", document.get("categoryTree"));
				jsonObject.put("categoryTree", document.get("categoryTree"));
				jsonObject.put("title", document.get("categoryTitle"));
				jsonObject.put("html", document.get("html"));
				
				categorySearch.checkSubCategory(document, jsonObject);
				
				// jsonObject.put("isFolder", "true");
				// jsonObject.put("isLazy", "true");
				
				array.add(jsonObject);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			categorySearch.close();
		}
		
		

		return array;
	}
	
	public String convertHtmlText(ArrayList<Document> list, String highlightQuery){
		
		String returnVal = "";
		
		String html = list.get(0).get(htmlField);
		
		try {
			
			returnVal = htmlHighlight.getHighlightHTML(englishAnalyzer, html, htmlField, highlightQuery);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnVal;
	}

}
