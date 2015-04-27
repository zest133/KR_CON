function CategoryTree() {
	var categoryTreeSelector = "";
	var categoryDivSelector = "";
	var contentDivSelector = "";
};
/*
 * $("#tocContent").dynatree({ initAjax : { url : "root_category.do" },
 * onActivate : function(node) { loadHtmlContent(node.data.html); }, onLazyRead :
 * function(node) { node.appendAjax({ url : "sub_category.do", data : {
 * categoryTree : node.data.categoryTree } }); } });
 */

CategoryTree.prototype.buildTree = function(categoryTree) {
	$("#" + categoryTree.categoryTreeSelector).dynatree(
			{
				initAjax : {
					url : "root_category.do"
				},
				onActivate : function(node) {
					// CategoryTree.loadHtmlContent(node.data.html,
					// contentDivClassName);
					categoryTree.getCurrentHtmlContent(categoryTree,
							node.data.categoryTree);
				},
				onLazyRead : function(node) {
					node.appendAjax({
						url : "sub_category.do",
						data : {
							categoryTree : node.data.categoryTree
						},
						debugLazyDelay : 100
					});
				}
			});
};

CategoryTree.prototype.openCurrentTree = function(keyPath) {
	var tree = $("#tocContent").dynatree("getTree");

	tree.loadKeyPath(keyPath, function(node,
			status) {
		if (status == "loaded") {
			node.expand();
		} else if (status == "ok") {
			node.activate();
		}
	});
};

//
//$("#btnLoadKeyPath").click(
//		function() {
//			var tree = $("#tocContent").dynatree("getTree");
//
//			tree.loadKeyPath("/folder1/folder1/folder1/folder1", function(node,
//					status) {
//				if (status == "loaded") {
//					node.expand();
//				} else if (status == "ok") {
//					node.activate();
//				}
//			});
//		});

$("#btnCollapseAll").click(function() {
	$("#tocContent").dynatree("getRoot").visit(function(node) {
		node.expand(false);
	});
	return false;
});

CategoryTree.prototype.getCurrentHtmlContent = function(categoryTree,
		currentCategoryTree) {

	var data1 = {
		categoryTree : currentCategoryTree
	};

	$.ajax({
		url : "current_html.do",
		data : data1,
		success : function(msg) {
			categoryTree.loadHtmlContent(categoryTree, msg);
		},
		error : function(msg) {

		}
	});

	// current_html.do
	/*
	 * request html
	 */

	// CategoryTree.loadHtmlContent(node.data.html, contentDivClassName);
};

CategoryTree.prototype.loadHtmlContent = function(categoryTree, htmlText) {
	$("." + categoryTree.contentDivSelector).html(htmlText);
};

CategoryTree.prototype.setLayoutResizable = function(categoryTree) {
	$("#" + categoryTree.categoryDivSelector).resizable({
		handles : 'e, w'
	});

	$("#" + categoryTree.categoryDivSelector).resize(
			function() {
				$("." + categoryTree.contentDivSelector).css("padding-left",
						$("#" + categoryTree.categoryDivSelector).width());
			});
};
