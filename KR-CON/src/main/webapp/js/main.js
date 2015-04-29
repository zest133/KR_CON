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
			$(document).unbind('scroll');
			search.search();
		}
	});
	
	
	$("#searchLink").click(function(){
		$(document).unbind('scroll');
		search.search();
	});
	
	$("#advabcedSearchButton").click(function(){
		$(document).unbind('scroll');
		search.advSearch();
	});
	
	
	$("#advSearchButton").click(function(){
		$(document).unbind('scroll');
		search.callAdvanceSearch();
	});
	
});