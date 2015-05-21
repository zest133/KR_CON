package com.latis.krcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

//	@RequestMapping(value = "{categoryTree}/{highlightQuery}/current_html.do")
//	public String getCurrentCategoryHTML(
//			@PathVariable String categoryTree,
//			@PathVariable String highlightQuery,
//			Model model) {
//		
//		
//		String html = categorySearchDAO.getCurrentCategoryHTML(
//				categoryTree, highlightQuery).toString();
//		
//		html = html.trim();
//		
//		categoryTree = categorySearchDAO.buildCategoryPath(categoryTree);
//		
//		model.addAttribute("html", html);
//		model.addAttribute("categoryTree", categoryTree);
//		return "frameContent";
//	}
	
	@RequestMapping(value = "current_html.do")
	public String getCurrentCategoryHTML(
			@RequestParam String categoryTree,
			@RequestParam String highlightQuery,
			Model model) {
		
		
		String html = categorySearchDAO.getCurrentCategoryHTML(
				categoryTree, highlightQuery).toString();
		
		html = html.trim();
		
		categoryTree = categorySearchDAO.buildCategoryPath(categoryTree);
		
		model.addAttribute("html", html);
		model.addAttribute("categoryTree", categoryTree);
		return "frameContent";
	}
}
