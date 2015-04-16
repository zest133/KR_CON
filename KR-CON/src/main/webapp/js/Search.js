function Search(){
	
};

Search.prototype.startSearch = function(advancedFlag) {
	if (!advancedFlag) {
		this.normalSearch();
	} else {
		this.advancedSearch();
	}
};

Search.prototype.normalSearch = function() {
	var searchKeyword = $("#searchinputbox").val();

	if (searchKeyword != "") {
		
		var data = {
				"searchKeyword" : searchKeyword
		};
		
		
	} else {
		alert("감섹어 없음");
	}
};

Search.prototype.advancedSearch = function() {
	var searchAND = $("#searchAND").val();
	var searchOR = $("#searchOR").val();
	var searchExact = $("#searchExact").val();
	var searchNON = $("#searchNON").val();

	if (searchAND != "" && searchOR != "" && searchExact != ""
			&& searchNON != "") {
		var data = {
				"searchAND" : searchAND,
				"searchOR" : searchOR,
				"searchExact" : searchExact,
				"searchNON" : searchNON
		};

	} else {
		alert("감섹어 없음");
	}
};
