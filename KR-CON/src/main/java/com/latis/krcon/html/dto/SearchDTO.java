package com.latis.krcon.html.dto;

public class SearchDTO {

	// search word data
	private String andWordSearch;
	private String orWordSearch;
	private String exactWordSearch;
	private String notWordSearch;

	// filter word data
	private String breadcrumb;
	private String categoryTitle;
	private String locale;

	// sort fieldcolumn name
	private String sortFileName;

	public String getAndWordSearch() {
		return andWordSearch;
	}

	public void setAndWordSearch(String andWordSearch) {
		this.andWordSearch = andWordSearch;
	}

	public String getOrWordSearch() {
		return orWordSearch;
	}

	public void setOrWordSearch(String orWordSearch) {
		this.orWordSearch = orWordSearch;
	}

	public String getExactWordSearch() {
		return exactWordSearch;
	}

	public void setExactWordSearch(String exactWordSearch) {
		this.exactWordSearch = exactWordSearch;
	}

	public String getNotWordSearch() {
		return notWordSearch;
	}

	public void setNotWordSearch(String notWordSearch) {
		this.notWordSearch = notWordSearch;
	}

	public String getBreadcrumb() {
		return breadcrumb;
	}

	public void setBreadcrumb(String breadcrumb) {
		this.breadcrumb = breadcrumb;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getSortFileName() {
		return sortFileName;
	}

	public void setSortFileName(String sortFileName) {
		this.sortFileName = sortFileName;
	}
	
	
}
