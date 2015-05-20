function CategoryTree() {
	var categoryTreeSelector = "";
	var categoryDivSelector = "";
	var contentDivSelector = "";
};

CategoryTree.prototype.buildTree = function(categoryTree) {
	$("#" + categoryTree.categoryTreeSelector).dynatree(
			{
				initAjax : {
					url : "root_category.do"
				},
				onClick : function(node) {
					node.deactivate();
					$("iframe").contents().find("#highlightQuery").val("");
				},
				
				onActivate : function(node) {
					//
					node.focus();
					$(document).unbind('scroll');
					$( ".advancedSearch" ).slideUp();
					categoryTree.getCurrentHtmlContent(categoryTree,
							node.data.categoryTree);
				},
				onLazyRead : function(node) {
					node.appendAjax({
						url : "sub_category.do",
						data : {
							categoryTree : node.data.categoryTree
						},
						debugLazyDelay : 75
					});
				}
			});
};

CategoryTree.prototype.openCurrentTree = function(keyPath) {
	var tree = $("#tocContent").dynatree("getTree");

	tree.loadKeyPath(keyPath, function(node,
			status) {
		node.deactivate();
		if (status == "loaded") {
			node.expand();
		} else if (status == "ok") {			
			node.activate();
		}
	});
};


$("#btnCollapseAll").click(function() {
	$("#tocContent").dynatree("getRoot").visit(function(node) {
		node.expand(false);
	});
	return false;
});

CategoryTree.prototype.getCurrentHtmlContent = function(categoryTree,
		currentCategoryTree) {
	
	var highlightQuery = "";
	if($("iframe").contents().find("#highlightQuery").val() != null){
		highlightQuery = $("iframe").contents().find("#highlightQuery").val();
	}
	
	
	if(highlightQuery == ""){
		highlightQuery = "@";
	}
	
	$("#frameFormCategoryTree").val(currentCategoryTree);
	$("#frameFormHighlightQuery").val(highlightQuery);
	
	$("#categoryFrameForm").submit();
	
};

CategoryTree.prototype.setLayoutResizable = function(categoryTree) {
	$("#" + categoryTree.categoryDivSelector).resizable({
		handles : 'e, w'
	});

	$("#" + categoryTree.categoryDivSelector).resize(
			function() {
				$("." + categoryTree.contentDivSelector).css("left",
						$("#" + categoryTree.categoryDivSelector).width());
			});
};
