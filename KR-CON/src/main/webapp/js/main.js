$(document).ready(function(){
	/*
	 * Category
	 */
	var categoryTree = new CategoryTree();
	var search = new Search();
	
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
	
	$("#searchinputbox").keydown(function(e){
		if(e.keyCode == "13"){
			search.search(0);
		}
	});
	
	
	$("#searchLink").click(function(){
		search.search(0);
	});
	
	$("#advabcedSearchButton").click(function(){
		search.advSearch(0);
	});
	
	
	$("#advSearchButton").click(function(){
		document.location.href="advanced_search.do";
	});
	
});