package com.latis.krcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.latis.krcon.html.search.dao.HtmlSearchDAO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	public HtmlSearchDAO htmlSearchDAO;

	

	@RequestMapping(value = { "/", "/main.do" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "content";
	}

	@RequestMapping(value = "/advanced_search.do", method = RequestMethod.POST)
	public String advancedSearch(Model model) {

		model.addAttribute("filters", htmlSearchDAO.getSearchFilterOption());
		return "advancedSearch";
	}

}
