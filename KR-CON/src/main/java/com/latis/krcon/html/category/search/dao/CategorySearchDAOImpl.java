package com.latis.krcon.html.category.search.dao;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.latis.krcon.html.category.search.CategorySearch;

public class CategorySearchDAOImpl implements CategorySearchDAO {

	@Autowired
	private CategorySearch categorySearch;
	
	public CategorySearchDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public JSONArray getRootCategory() {
		// TODO Auto-generated method stub
		JSONArray returnArray = null;
		try {
			categorySearch.init();
			categorySearch.setSearchWord("0000.00e0.1530");
			ArrayList<Document> list = categorySearch.search();

			returnArray = convertJsonArray(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (ParseException e) {
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
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			categorySearch.close();
		}

		return returnArray;
	}

	@Override
	public String getCurrentCategoryHTML(String selectedCategoryTree) {
		// TODO Auto-generated method stub
		String returnVal = "";
		try {
			categorySearch.init();
			ArrayList<Document> list = categorySearch
					.currentSearch(selectedCategoryTree);
			returnVal = cenvertHtmlText(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			categorySearch.close();
		}
		
		return returnVal;
	}

	public JSONArray convertJsonArray(ArrayList<Document> list)
			throws IOException, ParseException {
		JSONArray array = new JSONArray();
		
		
		for (Document document : list) {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put("categoryTree", document.get("categoryTree"));
			jsonObject.put("title", document.get("categoryTitle"));
			jsonObject.put("html", document.get("html"));

			categorySearch.checkSubCategory(document, jsonObject);

			// jsonObject.put("isFolder", "true");
			// jsonObject.put("isLazy", "true");

			array.add(jsonObject);
		}

		return array;
	}
	
	public String cenvertHtmlText(ArrayList<Document> list){
		
		return list.get(0).get("html");
	}

}
