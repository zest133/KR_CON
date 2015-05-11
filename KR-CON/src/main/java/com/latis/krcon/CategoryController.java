package com.latis.krcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.latis.krcon.html.category.search.dao.CategorySearchDAO;

@Controller
public class CategoryController {

	@Autowired
	public CategorySearchDAO categorySearchDAO;
	
	@RequestMapping(value = "/root_category.do")
	public @ResponseBody String getRootCategory() {
		String returnVal = categorySearchDAO.getRootCategory().toString();
		return returnVal;
	}

	@RequestMapping(value = "/sub_category.do")
	public @ResponseBody String getSubCategory(@RequestParam String categoryTree) {
		String returnVal = categorySearchDAO.getSubCategory(categoryTree)
				.toString();
		return returnVal;
	}

	@RequestMapping(value = "/current_html.do")
	public @ResponseBody String getCurrentCategoryHTML(
			@RequestParam String categoryTree,
			@RequestParam String highlightQuery) {
		String returnVal = categorySearchDAO.getCurrentCategoryHTML(
				categoryTree, highlightQuery).toString();


		return returnVal;

	}
}
