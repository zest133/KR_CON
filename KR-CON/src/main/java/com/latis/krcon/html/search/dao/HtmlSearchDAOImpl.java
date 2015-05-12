package com.latis.krcon.html.search.dao;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.latis.krcon.html.category.search.CategorySearch;
import com.latis.krcon.html.filter.dto.FilterDTO;
import com.latis.krcon.html.search.EnglishHtmlSearch;
import com.latis.krcon.html.search.dto.SearchDTO;
import com.latis.krcon.html.search.dto.SearchResultDTO;

public class HtmlSearchDAOImpl implements HtmlSearchDAO{

	private static final Logger logger = LoggerFactory.getLogger(HtmlSearchDAOImpl.class);
	
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
	

	@Override
	public ArrayList<SearchResultDTO> advSearch(SearchDTO searchDTO) {
		// TODO Auto-generated method stub
		ArrayList<SearchResultDTO> returnList = null;
		
		try {
			englishHtmlSearch.init();
			
			searchDTO.setSortFileName("categoryTree");
			
			englishHtmlSearch.setSearchDTO(searchDTO);
			
			returnList = englishHtmlSearch.getSearchData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
		} finally {
		}

		return dto;
	}
	
	@Override
	public ArrayList<String> getStopWordList(){
		return englishHtmlSearch.compareStopWord();
	}

	@Override
	public int getSearchResultTotalCount() {
		// TODO Auto-generated method stub
		return englishHtmlSearch.getTotalHits();
	}

	

}
