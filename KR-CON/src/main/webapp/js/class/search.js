function Search() {
	this.tempTotalcount = 0;
	this.searchData = {
		searchAND : "",
		searchOR : "",
		searchExact : "",
		searchNON : "",
		filterBreradcrumbsList : "",
		filterTitleList : "",
		filterLocaleList : "",
		pageNum : 0,
		totalCount : -1
	};
};

Search.prototype.search = function() {

	if ($.trim($("#searchinputbox").val()) != "") {
		this.dataReset();
		this.searchData.searchAND = $("#searchinputbox").val();
		$("#searchinputbox").val("");
		this.ajaxSearch(this);
	}

};

Search.prototype.advSearch = function() {

	this.dataReset("advance");
	this.searchData.searchAND = $("#searchAND").val();
	this.searchData.searchOR = $("#searchOR").val();
	this.searchData.searchExact = $("#searchExact").val();
	this.searchData.searchNON = $("#searchNON").val();
	this.searchData.filterBreradcrumbsList = $("#filterBreradcrumbsList").val();
	this.searchData.filterTitleList = $("#filterTitleList").val();
	this.searchData.filterLocaleList = $("#filterLocaleList").val();

	$("#searchinputbox").val("");
	this.ajaxSearch(this);

};

Search.prototype.synonymSearch = function() {
	this.dataReset("advance");
	this.searchData.searchAND = $("iframe").contents().find("#synonym").val();
	this.ajaxSearch(this);
};

Search.prototype.ajaxSearch = function(search) {
	$("#searchinputbox").val("");

	$("#frameFormSearchAND").val(search.searchData.searchAND);
	$("#frameFormSearchOR").val(search.searchData.searchOR);
	$("#frameFormSearchExact").val(search.searchData.searchExact);
	$("#frameFormSearchNON").val(search.searchData.searchNON);
	$("#frameFormFilterBreradcrumbsList").val(
			search.searchData.filterBreradcrumbsList);
	$("#frameFormFilterTitleList").val(search.searchData.filterTitleList);
	$("#frameFormFilterLocaleList").val(search.searchData.filterLocaleList);
	$("#frameFormPageNum").val(search.searchData.pageNum);
	$("#frameFormTotalCount").val(search.searchData.totalCount);

	$("#searchFrameForm").submit();

};

/*
 * Search.prototype.callAdvanceSearch = function(){ $.ajax({
 * url:"advanced_search.do", type: "post", //data: "", success: function(msg){
 * //alert(msg); $(".contents").html(msg);
 * 
 * $("#advabcedSearchButton").click(function(){ Search.prototype.advSearch(0);
 * });
 * 
 *  }, error: function(msg){ //console.log(msg); alert("search error"); } }); }
 */

Search.prototype.getPageNum = function() {
	return this.searchData.pageNum;
};

Search.prototype.checkSearchResultScroll = function(search) {
	/*
	 * $(document).scroll(function(){ var totalScroll =
	 * $("body").prop("scrollHeight") - 700; var currentScroll =
	 * $("body").scrollTop();
	 * 
	 * console.log(totalScroll + ", " +currentScroll);
	 * 
	 * if(currentScroll > totalScroll - 170){ grid.loadGrid(grid, "gridContent",
	 * Number(grid.currentPage) + 1);
	 *  } });
	 */
	$(document)
			.scroll(
					function() {
						var totalScroll = $("body").prop("scrollHeight") - 525;
						var currentScroll = $("body").scrollTop();

						// console.log(totalScroll + ", " +currentScroll);

						if (currentScroll > totalScroll * 0.85) {
							search.searchData.pageNum = search.searchData.pageNum + 1;
							search.searchData.totalCount = $("#resultCount")
									.val();

							if (search.searchData.totalCount > 100) {
								var requestCount = 100 * (search.searchData.pageNum + 1);
								var temp = search.searchData.totalCount
										/ requestCount;
								console.log("requestCount=" + requestCount
										+ ", totalCount="
										+ search.searchData.totalCount);
								if (temp >= 1) {
									search.ajaxSearch(search);
								} else {
									if ((requestCount - search.searchData.totalCount) < 100) {
										search.ajaxSearch(search);
									}
								}
							}

						}
					});
};

Search.prototype.dataReset = function(flag) {
	// if(flag != "advance"){
	// $(".contents").html("");
	// }

	this.searchData = {
		searchAND : "",
		searchOR : "",
		searchExact : "",
		searchNON : "",
		filterBreradcrumbsList : "",
		filterTitleList : "",
		filterLocaleList : "",
		pageNum : 0,
		totalCount : -1
	};
};

Search.prototype.appendSearch = function() {

	var data = {
		searchAND : $("#frameFormSearchAND").val(),
		searchOR : $("#frameFormSearchOR").val(),
		searchExact : $("#frameFormSearchExact").val(),
		searchNON : $("#frameFormSearchNON").val(),
		filterBreradcrumbsList : $("#frameFormFilterBreradcrumbsList").val(),
		filterTitleList : $("#frameFormFilterTitleList").val(),
		filterLocaleList : $("#frameFormFilterLocaleList").val(),
		pageNum : $("#frameFormPageNum").val(),
		totalCount : $("#frameFormTotalCount").val()
	};

	$.ajax({
		url : "search.do",
		type : "post",
		data : data,
		success : function(msg) {
			// alert(msg);

			$("iframe").contents().find("body").append(msg).append(" ");

			// $("iframe").load(msg);

		},
		error : function(msg) {
			console.log(msg);
		}
	});
}

Search.prototype.searchHighlight = function(query) {
	if(query.trim() == ""){
		return;
	}
	var keywordArray = query.trim().split(" ");
	
	
	for (var k = 0; k < keywordArray.length; k++) {
		var content = $('iframe').contents().find('body').html();
		var para = $('iframe').contents().find(
				"p, span, h1, h2, h3, h4, h5, h6, td");
		var currentKey = keywordArray[k];
		
		for (var i = 0; i < para.length; i++) {
			var tempHTML = para[i].outerHTML;
			var tagData = tempHTML.match(/<.*?>/gi);
			var resultData = "";
			var indexArray = new Array();
			var offset = 0;
			for (var j = 0; j < tagData.length; j++) {
				var index = 0;
				var lastIndex = 0;
				if (tagData.length - 1 == j) {
					resultData += tagData[j];
					break;
				} else {
					index = tempHTML.indexOf(tagData[j], offset)
							+ tagData[j].length;
					resultData += tagData[j];
					lastIndex = tempHTML.indexOf(tagData[j + 1], index);
					offset = lastIndex;
					var tempData = tempHTML.substring(index, lastIndex);
					tempData = this.replaceSearch(tempData, currentKey);
					resultData += tempData;
				}
			}
			content = content.replace(tempHTML, resultData);
		}
		$('iframe').contents().find('body').html(content);
	}
};

Search.prototype.replaceSearch = function(data, currentKey) {
	if (data.indexOf("&nbsp;") > -1) {
		data = data.split("&nbsp;").join(" ");
	}

	var regexp = new RegExp("\\W(" + currentKey + ".*?)\\W|^(" + currentKey
			+ ".*?)\\W|^(" + currentKey + ".*?)$|\\W(" + currentKey + ".*?)$",
			'gi');

	var check = regexp.test(data);

	if (check) {
		data = data.replace(regexp, " <span class='highlight'>" + "$1$2$3$4" + "</span> ");
	}

	// if (currentKey != "") {
	// var keyIgnoreCaseIndex =
	// data.toUpperCase().indexOf(currentKey.toUpperCase());
	// var tempData = "";
	// if (keyIgnoreCaseIndex > -1) {
	// var currentKeyword = data.substring(keyIgnoreCaseIndex, currentKey.length
	// + keyIgnoreCaseIndex);
	// var currentReplacedData = data.substring(0, currentKey.length +
	// keyIgnoreCaseIndex);
	// var nextData = data.substring(currentKey.length + keyIgnoreCaseIndex,
	// data.length);
	// currentReplacedData = this.replaceHighglight(currentReplacedData,
	// currentKeyword);
	// nextData = this.replaceSearch(nextData, currentKey);
	// tempData += currentReplacedData;
	// tempData += nextData;
	// return tempData;
	// } else {
	//
	// }
	// }
	return data;
};

Search.prototype.replaceHighglight = function(data, key) {
	var startTag = "<span class='highlight'>";
	var endTag = "</span>";
	var tempData = data.replace(key, startTag + key + endTag);
	return tempData;
};