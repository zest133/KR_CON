package com.latis.krcon.html.filter;


import java.util.ArrayList;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.ChainedFilter;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.springframework.beans.factory.annotation.Value;

public class HtmlFilter {

	private Filter breadcrumbFilter = null;
	private Filter categoryTitleFilter = null;
	private Filter localeTitleFilter = null;
//
	@Value("${breadcrumbField}")
	private String breadcrumbField;
	@Value("${categoryTitleField}")
	private String categoryTitleField;
	@Value("${localeField}")
	private String localeField;
	
	

	private ArrayList<Filter> filterList;
	
//	public HtmlFilter(String breadcrumb, String categoryTitle, String locale) {
//		// TODO Auto-generated constructor stub
//		
//		filterList = new ArrayList<Filter>();
//		
//		if (breadcrumb != null && !breadcrumb.equals("") && !breadcrumb.equals("All")) {
//			breadcrumbFilter = new CachingWrapperFilter(new QueryWrapperFilter(
//					new TermQuery(new Term(breadcrumbField, breadcrumb))));
//			filterList.add(breadcrumbFilter);
//		}
//		if (categoryTitle != null && !categoryTitle.equals("") && !categoryTitle.equals("All")) {
//			categoryTitleFilter = new CachingWrapperFilter(new QueryWrapperFilter(
//					new TermQuery(new Term(categoryTitleField, categoryTitle))));
//			filterList.add(categoryTitleFilter);
//		}
//		
//		if (locale != null && !locale.equals("")) {
//			localeTitleFilter = new CachingWrapperFilter(
//				new QueryWrapperFilter(
//					new TermQuery(
//						new Term(localeField, locale)
//					)
//				)
//			);
//			filterList.add(localeTitleFilter);
//		}
//		
//		
//	}
	
	public void setFilter(String breadcrumb, String categoryTitle, String locale) {
		filterList = new ArrayList<Filter>();
		
		if (breadcrumb != null && !breadcrumb.equals("") && !breadcrumb.equals("All")) {
			breadcrumbFilter = new CachingWrapperFilter(new QueryWrapperFilter(
					new TermQuery(new Term(breadcrumbField, breadcrumb))));
			filterList.add(breadcrumbFilter);
		}
		if (categoryTitle != null && !categoryTitle.equals("") && !categoryTitle.equals("All")) {
			categoryTitleFilter = new CachingWrapperFilter(new QueryWrapperFilter(
					new TermQuery(new Term(categoryTitleField, categoryTitle))));
			filterList.add(categoryTitleFilter);
		}
		
		if (locale != null && !locale.equals("")) {
			localeTitleFilter = new CachingWrapperFilter(
				new QueryWrapperFilter(
					new TermQuery(
						new Term(localeField, locale)
					)
				)
			);
			filterList.add(localeTitleFilter);
		}
	}

	

	public ChainedFilter getFilter() throws Exception {

		ChainedFilter chain = null;
		if(filterList.size() >0 ){
//			String[] arr = list.toArray(new String[list.size()]);
			Filter[] filter = filterList.toArray(new Filter[filterList.size()]);
			if(filterList.size() == 1){
				chain = new ChainedFilter(filter);
			}else{
				chain = new ChainedFilter(filter, ChainedFilter.AND);
			}
		}
		return chain;
		
	}
}
