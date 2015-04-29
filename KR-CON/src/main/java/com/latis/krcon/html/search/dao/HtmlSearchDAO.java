package com.latis.krcon.html.search.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.document.Document;

import com.latis.krcon.html.filter.dto.FilterDTO;
import com.latis.krcon.html.search.dto.SearchDTO;
import com.latis.krcon.html.search.dto.SearchResultDTO;

public interface HtmlSearchDAO {
//	public ArrayList<SearchResultDTO> search(String keyword);
	public HashMap<String, Object> advSearch(SearchDTO searchDTO);
	
	public FilterDTO getSearchFilterOption();
	
	public ArrayList<String> getStopWordList();
	
//	public int getSearchResultTotalCount(SearchDTO searchDTO);
}
