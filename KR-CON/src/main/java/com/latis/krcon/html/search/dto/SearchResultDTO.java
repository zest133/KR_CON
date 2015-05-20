package com.latis.krcon.html.search.dto;

public class SearchResultDTO {
	private String title;
	private String htmlText;
	private String breadcrumbs;
	private String categoryTree;
	private int rank;
	private int view;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHtmlText() {
		return htmlText;
	}
	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}
	public String getBreadcrumbs() {
		return breadcrumbs;
	}
	public void setBreadcrumbs(String breadcrumbs) {
		this.breadcrumbs = breadcrumbs;
	}
	public String getCategoryTree() {
		return categoryTree;
	}
	public void setCategoryTree(String categoryTree) {
		this.categoryTree = categoryTree;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getView() {
		return view;
	}
	public void setView(int view) {
		this.view = view;
	}
	
	
	
	
}
