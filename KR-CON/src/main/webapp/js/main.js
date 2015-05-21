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
	
	$('iframe').load(function() {
		this.focus();
		
		
		if(this.contentWindow.document.body.children.length > 0){
			var frameId = this.contentWindow.document.body.children[0].value;
			if(frameId == "content"){
				var tree = this.contentWindow.document.body.children[1].value;
			}else if(frameId == "search"){
				
				
				var result = $(this).contents().find("body");
				$(this).contents().scroll(function(){
					
					var totalScroll = result.prop("scrollHeight") - 525;
					var currentScroll = result.scrollTop();
					if(currentScroll > totalScroll * 0.85){
						
						$("#frameFormPageNum").val(Number($("#frameFormPageNum").val()) + 1);
						$("#frameFormTotalCount").val($(this).contents().find("#resultCount").val());
						
						if($("#frameFormTotalCount").val() > 100){
							var requestCount = 100*(Number($("#frameFormPageNum").val()) + 1);
							var temp = $("#frameFormTotalCount").val() / requestCount;
							console.log("requestCount="+requestCount+", totalCount=" + $("#frameFormTotalCount").val());
							if(temp >= 1){
								Search.prototype.appendSearch();
							}else{
								if((requestCount - $("#frameFormTotalCount").val()) < 100){
									Search.prototype.appendSearch();
								}
							}
						}
					}
				});
			}
		}
		
		
		
		
		
    });
});