package com.latis.krcon.html.filter;


import org.apache.lucene.index.Term;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.ChainedFilter;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;

public class HtmlFilter {

	private Filter fileNameFilter = null;
	private Filter categoryFilter = null;

	private String fileName = null;
	private String category = null;

	public HtmlFilter(String fileName, String category) {
		// TODO Auto-generated constructor stub
		this.fileName = fileName;
		this.category = category;
		
		if (fileName != null && fileName != "") {
			fileNameFilter = new CachingWrapperFilter(new QueryWrapperFilter(
					new TermQuery(new Term("filename", fileName))));
		}
		if (category != null && category != "") {
			categoryFilter = new CachingWrapperFilter(new QueryWrapperFilter(
					new TermQuery(new Term("category", fileName))));
		}
		
		
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

//	public void init() {
//		
//	}

	public ChainedFilter getFilter() throws Exception {

		ChainedFilter chain = null;
		if(fileNameFilter != null && categoryFilter != null){
			chain = new ChainedFilter(new Filter[] { fileNameFilter,
					categoryFilter }, ChainedFilter.AND);
		}else{
			if(fileNameFilter != null){
				chain = new ChainedFilter(new Filter[] { fileNameFilter});
			}else if(categoryFilter != null){
				chain = new ChainedFilter(new Filter[] { categoryFilter});
			}
		}
		return chain;
		
	}
}
