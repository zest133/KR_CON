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
	
	if($.trim($("#searchinputbox").val()) != ""){
		this.dataReset();
		this.searchData.searchAND = $("#searchinputbox").val();
		this.ajaxSearch(this);
	}
	
	
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
			var opts = {
				lines : 13, // The number of lines to draw
				length : 20, // The length of each line
				width : 10, // The line thickness
				radius : 30, // The radius of the inner circle
				corners : 1, // Corner roundness (0..1)
				rotate : 0, // The rotation offset
				direction : 1, // 1: clockwise, -1: counterclockwise
				color : '#000', // #rgb or #rrggbb
				speed : 1, // Rounds per second
				trail : 60, // Afterglow percentage
				shadow : false, // Whether to render a shadow
				hwaccel : false, // Whether to use hardware acceleration
				className : 'spinner', // The CSS class to assign to the spinner
				zIndex : 2e9, // The z-index (defaults to 2000000000)
				top : 'auto', // Top position relative to parent in px
				left : 'auto' // Left position relative to parent in px
			};
			var target = document.getElementById('graph_area');
			var spinner = new Spinner(opts).spin(target);
		},
		success: function(msg){
//			alert(msg);
			$(".contents").append(msg).append(" ");
			if(search.searchData.pageNum == 0){
				search.checkSearchResultScroll(search);
			}
			
			$("#graph_area").html("");
		},
		error: function(msg){
			//console.log(msg);
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
			//console.log(msg);
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
		
		//console.log(totalScroll + ", " +currentScroll);
		
		if(currentScroll > totalScroll * 0.85){
			search.searchData.pageNum = search.searchData.pageNum+1;
			search.searchData.totalCount = $("#resultCount").val();
			
			if(search.searchData.totalCount > 100){
				var requestCount = 100*(search.searchData.pageNum+1);
				var temp = search.searchData.totalCount / requestCount;
				console.log("requestCount="+requestCount+", totalCount="+search.searchData.totalCount);
				if(temp >= 1){
					search.ajaxSearch(search);
				}else{
					if((requestCount - search.searchData.totalCount) < 100){
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
