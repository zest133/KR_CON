function Search(){
	this.tempTotalcount = 0;
	this.searchData = {
			searchAND: "",
			searchOR: "",
			searchExact: "",
			searchNON: "",
			filterBreradcrumbsList: "",
			filterTitleList: "",
			filterLocaleList: "",
			pageNum : 0,
			totalCount : -1
	};
};

Search.prototype.search = function(){
	this.dataReset();
	this.searchData.searchAND = $("#searchinputbox").val();
	this.ajaxSearch(this);
	
};

Search.prototype.advSearch = function(){
	this.dataReset("advance");
	
	this.searchData.searchAND = $("#searchAND").val();
	this.searchData.searchOR = $("#searchOR").val();
	this.searchData.searchExact = $("#searchExact").val();
	this.searchData.searchNON = $("#searchNON").val();
	this.searchData.filterBreradcrumbsList = $("#filterBreradcrumbsList").val();
	this.searchData.filterTitleList = $("#filterTitleList").val();
	this.searchData.filterLocaleList = $("#filterLocaleList").val();
	
	$(".contents").html("");
	this.ajaxSearch(this);
	
};

Search.prototype.synonymSearch = function(){
	this.dataReset("advance");
	this.searchData.searchAND = $("#synonym").val();
	
	$(".contents").html("");
	this.ajaxSearch(this);
};

Search.prototype.ajaxSearch = function(search){
	$.ajax({
		url: "search.do",
		type: "post",
		data: search.searchData,
		beforeSend: function(){
			
		},
		success: function(msg){
//			alert(msg);
			$(".contents").append(msg).append(" ");
			if(search.searchData.pageNum == 0){
				search.checkSearchResultScroll(search);
			}
		},
		error: function(msg){
			console.log(msg);
			alert("search error");
		}
	});
};


Search.prototype.callAdvanceSearch = function(){
	$.ajax({
		url:"advanced_search.do",
		type: "post",
		//data: "",
		success: function(msg){
			//alert(msg);
			$(".contents").html(msg);
			
			$("#advabcedSearchButton").click(function(){
				Search.prototype.advSearch(0);
			});
			
			
		},
		error: function(msg){
			console.log(msg);
			alert("search error");
		}
	});
}

Search.prototype.getPageNum = function(){
	return this.searchData.pageNum;
};

Search.prototype.checkSearchResultScroll = function(search){
	/*
	 * $(document).scroll(function(){
		var totalScroll = $("body").prop("scrollHeight") - 700;
		var currentScroll = $("body").scrollTop();
		
		console.log(totalScroll + ", " +currentScroll);
		
		if(currentScroll > totalScroll - 170){
			grid.loadGrid(grid, "gridContent", Number(grid.currentPage) + 1);
			
		}
	});
	 */
	$(document).scroll(function(){
		var totalScroll = $("body").prop("scrollHeight") - 525;
		var currentScroll = $("body").scrollTop();
		
		console.log(totalScroll + ", " +currentScroll);
		
		if(currentScroll > totalScroll - 525){
			search.searchData.pageNum = search.searchData.pageNum+1;
			search.searchData.totalCount = $("#resultCount").val();
			
			if(search.searchData.totalCount > 25){
				var requestCount = 25*(search.searchData.pageNum+1);
				var temp = search.searchData.totalCount / requestCount;
				if(temp >= 1){
					search.ajaxSearch(search);
				}else{
					if((requestCount - search.searchData.totalCount) < 25){
						search.ajaxSearch(search);
					}
				}
			}
			
			
		}
	});
};

Search.prototype.dataReset= function(flag){
	if(flag != "advance"){
		$(".contents").html("");
	}
	
	this.searchData = {
			searchAND: "",
			searchOR: "",
			searchExact: "",
			searchNON: "",
			filterBreradcrumbsList: "",
			filterTitleList: "",
			filterLocaleList: "",
			pageNum : 0,
			totalCount : -1
	};
};
