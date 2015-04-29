package com.latis.krcon.html.search.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.latis.krcon.html.category.search.CategorySearch;
import com.latis.krcon.html.filter.dto.FilterDTO;
import com.latis.krcon.html.search.EnglishHtmlSearch;
import com.latis.krcon.html.search.dto.SearchDTO;
import com.latis.krcon.html.search.dto.SearchResultDTO;

public class HtmlSearchDAOImpl implements HtmlSearchDAO{

	@Autowired
	public EnglishHtmlSearch englishHtmlSearch;
	
	@Autowired
	public CategorySearch categorySearch;
	
	@Value("${breadcrumbField}")
	private String breadcrumbField;
	@Value("${categoryTitleField}")
	private String categoryTitleField;
	@Value("${localeField}")
	private String localeField;
	
//	@Override
//	public ArrayList<SearchResultDTO> search(String keyword) {
//		// TODO Auto-generated method stub
//		
//		ArrayList<SearchResultDTO> returnList = null;
//		
//		try {
//			englishHtmlSearch.init();
//			
//			SearchDTO searchDTO = new SearchDTO();
//			
//			searchDTO.setAndWordSearch(keyword);
//			searchDTO.setSortFileName("categoryTree");
//			
//			englishHtmlSearch.setSearchDTO(searchDTO);
//			
//			returnList = englishHtmlSearch.getSearchData();
//			
//			
//			
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			englishHtmlSearch.close();
//		}
//		
//		return returnList;
//	}

	@Override
	public HashMap<String, Object> advSearch(SearchDTO searchDTO) {
		// TODO Auto-generated method stub
		HashMap<String, Object> returnList = null;
		
		try {
			englishHtmlSearch.init();
			
			searchDTO.setSortFileName("categoryTree");
			
			englishHtmlSearch.setSearchDTO(searchDTO);
			
			returnList = englishHtmlSearch.getSearchData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			englishHtmlSearch.close();
		}
		
		return returnList;
	}
	
	@Override
	public FilterDTO getSearchFilterOption() {
		// TODO Auto-generated method stub
		
		FilterDTO dto = new FilterDTO();
		
		try {
			categorySearch.init();
			ArrayList<String> breadcrumbsFilter = new ArrayList<String>(); 
			ArrayList<String> titleFilter = new ArrayList<String>(); 
			ArrayList<String> localeFilter = new ArrayList<String>(); 
			
			ArrayList<Document> allList = categorySearch.categoryAllSearchData();
			
			breadcrumbsFilter.add("All");
			titleFilter.add("All");
			localeFilter.add("All");
			
			for(Document doc : allList){
				breadcrumbsFilter.add(doc.get(breadcrumbField));
				titleFilter.add(doc.get(categoryTitleField));
				localeFilter.add(doc.get(localeField));
			}
			
			dto.setBreadcrumbsFilter(breadcrumbsFilter);
			dto.setTitleFilter(titleFilter);
			dto.setLocaleFilter(localeFilter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			categorySearch.close();
		}
		
		
		
		return dto;
	}
	
	@Override
	public ArrayList<String> getStopWordList(){
		return englishHtmlSearch.compareStopWord();
	}
//
//	@Override
//	public int getSearchResultTotalCount(SearchDTO searchDTO) {
//		// TODO Auto-generated method stub
//		
//		int returnVal = 0;
//		try {
//			categorySearch.init();
//			
//			returnVal = englishHtmlSearch.htmlSearchData().totalHits;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			categorySearch.close();
//		}
//		
//		return englishHtmlSearch.htmlSearchData().totalHits;
//	}
	

}
