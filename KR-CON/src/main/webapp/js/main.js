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
		$(".advancedSearch").slideToggle("slow");
		search.advSearch();
	});
	
	
	$("#slideAdvSearchButton").click(function(){
		$(".advancedSearch").slideToggle("slow");
	});
	
	$(document).bind("click", function(event){
		var target = $(event.target);
		
		if(!target.is("#slideAdvSearchButton")){
			
			var flag = false;
			
			var parents = target.parents();
			
			for(var i = 0; i < parents.length; i ++){
				var parent = parents.eq(i);
				
				if(parent.is(".advancedSearch")){
					flag = true;
				}
			}
			
			if(target.is(".advancedSearch")){
				flag = true;
			}
			
			if(!flag){
				$( ".advancedSearch" ).slideUp();
			}
		}
		
	});
	
	
});