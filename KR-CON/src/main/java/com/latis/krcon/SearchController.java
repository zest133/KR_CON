package com.latis.krcon;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
	
	@RequestMapping(value = "/search.do", method = RequestMethod.POST)
	public String search(Model model, @RequestParam String keyword){
		String searchKeyword = keyword;
		
		System.out.println(searchKeyword);
		
		/*
		 * Search
		 */
		
		List<TestSearchDTO> searchResult = new ArrayList<TestSearchDTO>();
		
		TestSearchDTO testDTO1 = new TestSearchDTO();
		TestSearchDTO testDTO2 = new TestSearchDTO();
		
		testDTO1.setFileName("name1");
		testDTO1.setResulttext("text1");
		
		testDTO2.setFileName("name2");
		testDTO2.setResulttext("text2");
		
		searchResult.add(testDTO1);
		searchResult.add(testDTO2);
		
		
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("searchResult", searchResult);
		model.addAttribute("resultSize", searchResult.size());
		
		
		return "searchResult";
	}
	
	@RequestMapping(value = "/adv_search.do", method = RequestMethod.POST)
	public String advancedSearch(Model model, @RequestParam String searchAND,
			@RequestParam String searchOR, @RequestParam String searchExact, @RequestParam String searchNON){
//		String searchKeyword = keyword;
		
		System.out.println(searchAND);
		System.out.println(searchOR);
		System.out.println(searchExact);
		System.out.println(searchNON);
		
		/*
		 * Search
		 */
		
		List<TestSearchDTO> searchResult = new ArrayList<TestSearchDTO>();
		
		TestSearchDTO testDTO1 = new TestSearchDTO();
		TestSearchDTO testDTO2 = new TestSearchDTO();
		
		testDTO1.setFileName("name1");
		testDTO1.setResulttext("text1");
		
		testDTO2.setFileName("name2");
		testDTO2.setResulttext("text2");
		
		searchResult.add(testDTO1);
		searchResult.add(testDTO2);
		
		
		model.addAttribute("searchKeyword", "Advanced Search");
		model.addAttribute("searchResult", searchResult);
		model.addAttribute("resultSize", searchResult.size());
		
		
		return "searchResult";
	}
	
	public class TestSearchDTO{
		public String fileName;
		public String resulttext;
		
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getResulttext() {
			return resulttext;
		}
		public void setResulttext(String resulttext) {
			this.resulttext = resulttext;
		}
	}
}
