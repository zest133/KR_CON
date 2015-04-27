$(document).ready(function(){
	/*
	 * Category
	 */
	var categoryTree = new CategoryTree();
	
	/*
	 * categoryTreeSelector = "";
	var categoryDivSelector = "";
	var contentDivSelector = "";
	 */
	
	categoryTree.categoryTreeSelector = "tocContent";
	categoryTree.categoryDivSelector = "tocDiv";
	categoryTree.contentDivSelector = "contents";
	
	
//	categoryTree.buildTree("tocContent", "contents");
//	categoryTree.setLayoutResizable("tocDiv", "contents");
	
	categoryTree.buildTree(categoryTree);
	categoryTree.setLayoutResizable(categoryTree);
	
	/*
	 * Search
	 */
	
	$("#advSearchButton").click(function(){
		document.location.href="advanced_search.do";
	});
	
});