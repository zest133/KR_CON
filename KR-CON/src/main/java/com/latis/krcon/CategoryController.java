package com.latis.krcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.latis.krcon.html.category.search.dao.CategorySearchDAO;

@Controller
public class CategoryController {

	/*
	 * dynatree JSON 구조
	 * 
	 * [ { "title": "Folder 2", "isFolder": true, "key": "folder2", "children":
	 * [ {"title": "Sub-item 2.1"} ] }, {"title": "Item 5"} ]
	 */

	@Autowired
	public CategorySearchDAO categorySearchDAO;

	
	@RequestMapping(value = "/root_category.do")
	public @ResponseBody String getRootCategory() {
		// ArrayList<JSONObject> returnList = new ArrayList<JSONObject>();

		String returnVal = categorySearchDAO.getRootCategory().toString();
 
		return returnVal;
	}

	@RequestMapping(value = "/sub_category.do")
	public @ResponseBody String getSubCategory(@RequestParam String categoryTree) {
		// System.out.println(key);

		String returnVal = categorySearchDAO.getSubCategory(categoryTree)
				.toString();


		return returnVal;
	}

	@RequestMapping(value = "/current_html.do")
	public @ResponseBody String getCurrentCategoryHTML(
			@RequestParam String categoryTree,
			@RequestParam String highlightQuery) {
		// System.out.println(key);
		String returnVal = categorySearchDAO.getCurrentCategoryHTML(
				categoryTree, highlightQuery).toString();


		return returnVal;

	}
}
