package com.latis.krcon;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.latis.krcon.html.dto.SearchDTO;
import com.latis.krcon.html.dto.SearchResultDTO;
import com.latis.krcon.html.search.dao.HtmlSearchDAO;

@Controller
public class SearchController {
	
	@Autowired
	public HtmlSearchDAO htmlSearchDAO;
	
	@RequestMapping(value = "/search.do", method = RequestMethod.POST)
	public String search(Model model, @RequestParam String keyword){
		
		System.out.println(keyword);
		
		htmlSearchDAO.search(keyword);
		
		List<SearchResultDTO> searchResult = htmlSearchDAO.search(keyword);
		
		if(searchResult != null){
			
			model.addAttribute("searchKeyword", keyword);
			model.addAttribute("searchResult", searchResult);
			model.addAttribute("resultSize", searchResult.size());
			model.addAttribute("stopWord", htmlSearchDAO.getStopWordList());
		}
		
		return "searchResult";
	}
	
	@RequestMapping(value = "/adv_search.do", method = RequestMethod.POST)
	public String advancedSearch(Model model, 
			@RequestParam String searchAND,
			@RequestParam String searchOR, 
			@RequestParam String searchExact, 
			@RequestParam String searchNON,
			@RequestParam String filterBreradcrumbsList,
			@RequestParam String filterTitleList,
			@RequestParam String filterLocaleList
			){
		
//		System.out.println(searchAND);
//		System.out.println(searchOR);
//		System.out.println(searchExact);
//		System.out.println(searchNON);
		
		SearchDTO dto = new SearchDTO();
		dto.setAndWordSearch(searchAND);
		dto.setOrWordSearch(searchOR);
		dto.setExactWordSearch("\"" + searchExact + "\"");
		dto.setNotWordSearch(searchNON);
		
		List<SearchResultDTO> searchResult = htmlSearchDAO.advSearch(dto);
		
		
		
		if(searchResult != null){
			
			model.addAttribute("searchKeyword", "Advanced Search");
			model.addAttribute("searchResult", searchResult);
			model.addAttribute("resultSize", searchResult.size());
			model.addAttribute("stopWord", htmlSearchDAO.getStopWordList());
		}
		
		return "searchResult";
		
	}
	
	
}
