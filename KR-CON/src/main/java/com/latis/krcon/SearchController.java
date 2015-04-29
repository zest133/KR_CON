package com.latis.krcon;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.latis.krcon.html.search.dao.HtmlSearchDAO;
import com.latis.krcon.html.search.dto.SearchDTO;
import com.latis.krcon.html.search.dto.SearchResultDTO;
import com.latis.krcon.synonym.dao.SynonymSearchDAO;

@Controller
public class SearchController {
	
	@Autowired
	public HtmlSearchDAO htmlSearchDAO;
	
	@Autowired
	public SynonymSearchDAO synonumSearchDAO;
	
//	@RequestMapping(value = "/search.do", method = RequestMethod.POST)
//	public String search(Model model, @RequestParam String keyword){
//		
//		System.out.println(keyword);
//		
//		htmlSearchDAO.search(keyword);
//		
//		List<SearchResultDTO> searchResult = htmlSearchDAO.search(keyword);
//		
//		if(searchResult != null){
//			model.addAttribute("searchKeyword", keyword);
//			model.addAttribute("searchResult", searchResult);
//			model.addAttribute("resultSize", searchResult.size());
//			model.addAttribute("stopWord", htmlSearchDAO.getStopWordList());
//			model.addAttribute("synonym", synonumSearchDAO.checkSynonymWord(keyword));
//		}
//		
//		return "searchResult";
//	}
	
	@RequestMapping(value = "/search.do", method = RequestMethod.POST)
	public String search(Model model,
			@RequestParam String searchAND,
			@RequestParam String searchOR, 
			@RequestParam String searchExact, 
			@RequestParam String searchNON,
			@RequestParam String filterBreradcrumbsList,
			@RequestParam String filterTitleList,
			@RequestParam String filterLocaleList,
			@RequestParam String pageNum,
			@RequestParam String totalCount
			){
//		System.out.println(searchAND);
//		System.out.println(searchOR);
//		System.out.println(searchExact);
//		System.out.println(searchNON);
		
		SearchDTO dto = new SearchDTO();
		dto.setAndWordSearch(searchAND);
		dto.setOrWordSearch(searchOR);
		
		if(!searchExact.equals("")){
			dto.setExactWordSearch("\"" + searchExact + "\"");
		}
		dto.setNotWordSearch(searchNON);
		
		dto.setBreadcrumb(filterBreradcrumbsList);
		dto.setCategoryTitle(filterTitleList);
		dto.setLocale(filterLocaleList);
		
		dto.setPageNum(Integer.parseInt(pageNum));
		
		dto.setTotalCount(Integer.parseInt(totalCount));
		
		ArrayList<SearchResultDTO> searchResult = htmlSearchDAO.advSearch(dto);
		
		if(searchResult != null){
			
			StringBuffer buffer = new StringBuffer();
			buffer.append(searchAND).append(" ").append(searchOR).append(" ").append(searchExact).append(" ").append(searchNON);
			
			model.addAttribute("searchKeyword", searchAND);
			if(Integer.parseInt(pageNum) == 0){
				model.addAttribute("resultSize", htmlSearchDAO.getSearchResultTotalCount());
			}else{
				model.addAttribute("resultSize", searchResult.size());
			}
			model.addAttribute("searchResult", searchResult);
			model.addAttribute("stopWord", htmlSearchDAO.getStopWordList());
			model.addAttribute("synonym", synonumSearchDAO.checkSynonymWord(searchAND));
			model.addAttribute("pageNum", pageNum);
			model.addAttribute("highlightQuery", buffer.toString());
		}
		
		return "searchResult";
		
	}
	
	
}
