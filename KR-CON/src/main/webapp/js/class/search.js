function Search(){
	this.pageNum;
};

Search.prototype.search = function(pageNum){
	this.pageNum = pageNum;
	
	var data1 = {
			searchAND: $("#searchinputbox").val()
	};
	
	$.ajax({
		url: pageNum+"/search.do",
		type: "post",
		data: data1,
		success: function(msg){
			$(".contents").html(msg);
		},
		error: function(msg){
			
		}
	});
};

Search.prototype.advSearch = function(pageNum){
	this.pageNum = pageNum;
	var data1 = {
			searchAND: $("#searchAND").val(),
			searchOR: $("#searchOR").val(),
			searchExact: $("#searchExact").val(),
			searchNON: $("#searchNON").val(),
			filterBreradcrumbsList: $("#filterBreradcrumbsList").val(),
			filterTitleList: $("#filterTitleList").val(),
			filterLocaleList: $("#filterLocaleList").val()
	};
	
	$.ajax({
		url: pageNum+"/search.do",
		type: "post",
		data: data1,
		success: function(msg){
			$(".contents").html(msg);
		},
		error: function(msg){
			
		}
	});
};

Search.prototype.getPageNum = function(){
	return this.pageNum;
};