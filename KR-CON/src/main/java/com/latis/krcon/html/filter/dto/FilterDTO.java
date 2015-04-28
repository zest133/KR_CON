package com.latis.krcon.html.filter.dto;

import java.util.ArrayList;

public class FilterDTO {
	public ArrayList<String> breadcrumbsFilter;
	public ArrayList<String> titleFilter;
	public ArrayList<String> localeFilter;
	
	public ArrayList<String> getBreadcrumbsFilter() {
		return breadcrumbsFilter;
	}
	public void setBreadcrumbsFilter(ArrayList<String> breadcrumbsFilter) {
		this.breadcrumbsFilter = breadcrumbsFilter;
	}
	public ArrayList<String> getTitleFilter() {
		return titleFilter;
	}
	public void setTitleFilter(ArrayList<String> titleFilter) {
		this.titleFilter = titleFilter;
	}
	public ArrayList<String> getLocaleFilter() {
		return localeFilter;
	}
	public void setLocaleFilter(ArrayList<String> localeFilter) {
		this.localeFilter = localeFilter;
	}
	
	
}
