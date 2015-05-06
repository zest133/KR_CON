package com.latis.krcon;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.latis.krcon.html.filter.dto.FilterDTO;
import com.latis.krcon.html.search.dao.HtmlSearchDAO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	public HtmlSearchDAO htmlSearchDAO;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	// @RequestMapping(value = "/", method = RequestMethod.GET)
	// public String home(Locale locale, Model model) {
	// logger.info("Welcome home! The client locale is {}.", locale);
	//
	// Date date = new Date();
	// DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
	// DateFormat.LONG, locale);
	//
	// String formattedDate = dateFormat.format(date);
	//
	// model.addAttribute("serverTime", formattedDate );
	//
	// return "home";
	// }

	@RequestMapping(value = { "/", "/main.do" }, method = RequestMethod.GET)
	public String home(Model model) {

		return "content";
	}

	// @RequestMapping(value = "/searchResult.do", method = RequestMethod.GET)
	// public String searchResult() {
	// return "searchResult";
	// }

	@RequestMapping(value = "/advanced_search.do", method = RequestMethod.POST)
	public String advancedSearch(Model model) {

		model.addAttribute("filters", htmlSearchDAO.getSearchFilterOption());
		return "advancedSearch";
	}

}
