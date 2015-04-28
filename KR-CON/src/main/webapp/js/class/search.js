function Search(){
	this.pageNum;
};

Search.prototype.search = function(pageNum){
	this.pageNum = pageNum;
	
	var data = {
			searchAND: $("#searchinputbox").val(),
			searchOR: "",
			searchExact: "",
			searchNON: "",
			filterBreradcrumbsList: "",
			filterTitleList: "",
			filterLocaleList: ""
	};
	this.ajaxSearch(pageNum, data);
	
};

Search.prototype.advSearch = function(pageNum){
	this.pageNum = pageNum;
	var data = {
			searchAND: $("#searchAND").val(),
			searchOR: $("#searchOR").val(),
			searchExact: $("#searchExact").val(),
			searchNON: $("#searchNON").val(),
			filterBreradcrumbsList: $("#filterBreradcrumbsList").val(),
			filterTitleList: $("#filterTitleList").val(),
			filterLocaleList: $("#filterLocaleList").val()
	};
	this.ajaxSearch(pageNum, data);
	
};

Search.prototype.ajaxSearch = function(pageNum, data){
	$.ajax({
		url: pageNum+"/search.do",
		type: "post",
		data: data1,
		success: function(msg){
			$(".contents").html(msg);
		},
		error: function(msg){
			console.log(msg);
			alert("search error");
		}
	});
};

Search.prototype.getPageNum = function(){
	return this.pageNum;
};