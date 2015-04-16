package com.latis.krcon;

import java.awt.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
	
	@RequestMapping(value = "/search.do", method = RequestMethod.POST)
	public String search(@RequestParam String keyword, Model model){
		String searchKeyword = keyword;
		
		
		
		/*
		 * Search
		 */
		
		
		
		model.addAttribute("searchKeyword", searchKeyword);
//		model.addAttribute("searchResult", );
		
		
		return "searchResult";
	}
}
