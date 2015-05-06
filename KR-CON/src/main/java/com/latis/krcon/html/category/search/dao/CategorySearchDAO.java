package com.latis.krcon.html.category.search.dao;

import org.json.simple.JSONArray;

public interface CategorySearchDAO {
	public JSONArray getRootCategory();
	public JSONArray getSubCategory(String selectedCategoryTree);
	public String getCurrentCategoryHTML(String selectedCategoryTree, String highlightQuery);

}
