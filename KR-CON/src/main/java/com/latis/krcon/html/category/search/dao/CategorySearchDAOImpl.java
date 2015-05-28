package com.latis.krcon.html.category.search.dao;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.latis.krcon.html.category.search.CategorySearch;
import com.latis.krcon.html.search.EnglishHtmlSearch;
import com.latis.krcon.html.search.highlight.HtmlHighlight;

public class CategorySearchDAOImpl implements CategorySearchDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(CategorySearchDAOImpl.class);

	@Autowired
	private CategorySearch categorySearch;

	@Autowired
	private EnglishAnalyzer englishAnalyzer;

	@Autowired
	private HtmlHighlight htmlHighlight;

	@Autowired
	private EnglishHtmlSearch englishHtmlSearch;

	@Value("${rootCategoryTreeName}")
	private String rootCategoryTreeName;

	@Value("${htmlField}")
	private String htmlField;

	@Value("${fileindex}")
	private String dirPath;

	public CategorySearchDAOImpl() {
	}

	@Override
	public JSONArray getRootCategory() {
		JSONArray returnArray = null;
		try {
			categorySearch.init();
			categorySearch.setSearchWord(rootCategoryTreeName);
			ArrayList<Document> list = categorySearch.search();

			returnArray = convertJsonArray(list);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return returnArray;
	}

	@Override
	public JSONArray getSubCategory(String selectedCategoryTree) {
		JSONArray returnArray = null;
		try {
			categorySearch.init();
			categorySearch.setSearchWord(selectedCategoryTree);
			ArrayList<Document> list = categorySearch
					.categorySubTreeSearchData();
			returnArray = convertJsonArray(list);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return returnArray;
	}

	@Override
	public String getCurrentCategoryHTML(String selectedCategoryTree,
			String highlightQuery) {
		String returnVal = "";
		try {
			categorySearch.init();
			ArrayList<Document> list = categorySearch
					.currentSearch(selectedCategoryTree);

			returnVal = convertHtmlText(list, highlightQuery);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return returnVal;
	}

	@SuppressWarnings("unchecked")
	public JSONArray convertJsonArray(ArrayList<Document> list) {
		JSONArray array = new JSONArray();

		try {
			for (Document document : list) {
				JSONObject jsonObject = new JSONObject();

				jsonObject.put("key", document.get("categoryTree"));
				jsonObject.put("categoryTree", document.get("categoryTree"));
				jsonObject.put("title", document.get("categoryTitle"));
				jsonObject.put("html", document.get("html"));

				categorySearch.checkSubCategory(document, jsonObject);

				array.add(jsonObject);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return array;
	}

	public String convertHtmlText(ArrayList<Document> list,
			String highlightQuery) {
		String html = list.get(0).get(htmlField).trim();
		return html;
//		if (highlightQuery.equals("")) {
//			return html;
//		} else {
//			return htmlHighlight.htmlHighlight(html,
//					htmlHighlight.buildPatternString(highlightQuery));
//		}
	}

	@Override
	public String buildCategoryPath(String categoryTree) {
		return englishHtmlSearch.buildCategoryTreePath(categoryTree);
	}
}
