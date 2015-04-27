package com.latis.krcon.html.search.dao;

import java.util.ArrayList;

import org.apache.lucene.document.Document;

import com.latis.krcon.html.dto.FilterDTO;
import com.latis.krcon.html.dto.SearchDTO;
import com.latis.krcon.html.dto.SearchResultDTO;

public interface HtmlSearchDAO {
	public ArrayList<SearchResultDTO> search(String keyword);
	public ArrayList<SearchResultDTO> advSearch(SearchDTO searchDTO);
	
	public FilterDTO getSearchFilterOption();
}
