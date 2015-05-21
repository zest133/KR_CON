$(document).ready(function() {
	/*
	 * Category
	 */
	var categoryTree = new CategoryTree();
	var search = new Search();
	var eventHandler = new EventHandler();

	/*
	 * categoryTreeSelector = ""; var categoryDivSelector = ""; var
	 * contentDivSelector = "";
	 */

	categoryTree.categoryTreeSelector = "tocContent";
	categoryTree.categoryDivSelector = "tocDiv";
	categoryTree.contentDivSelector = "contents";

	// categoryTree.buildTree("tocContent", "contents");
	// categoryTree.setLayoutResizable("tocDiv", "contents");

	categoryTree.buildTree(categoryTree);
	categoryTree.setLayoutResizable(categoryTree);

	/*
	 * Search
	 */

	eventHandler.setKeyDownEvent(search);

	eventHandler.setClickEvent(search);
	
	eventHandler.setCategoryOnLoadEvent();
	eventHandler.setFrameOnLoadEvent();
	// $("#searchinputbox").keydown(function(e){
	// if(e.keyCode == "13"){
	// $(document).unbind('scroll');
	// search.search();
	// }
	// });

});