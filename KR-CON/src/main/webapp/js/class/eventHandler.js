function EventHandler() {

};

EventHandler.prototype.setKeyDownEvent = function(search) {
	$("#searchinputbox").keydown(function(e) {
		if (e.keyCode == "13") {
			$(document).unbind('scroll');
			search.search();
		}
	});
};

EventHandler.prototype.setClickEvent = function(search) {
	$("#searchLink").click(function() {
		$(document).unbind('scroll');
		search.search();
	});

	$("#advabcedSearchButton").click(function() {
		$(document).unbind('scroll');
		$(".advancedSearch").slideToggle("slow");
		search.advSearch();
	});

	$("#slideAdvSearchButton").click(function() {
		$(".advancedSearch").slideToggle("slow");
	});

	$(document).bind("click", function(event) {
		var target = $(event.target);

		if (!target.is("#slideAdvSearchButton")) {

			var flag = false;

			var parents = target.parents();

			for (var i = 0; i < parents.length; i++) {
				var parent = parents.eq(i);

				if (parent.is(".advancedSearch")) {
					flag = true;
				}
			}

			if (target.is(".advancedSearch")) {
				flag = true;
			}

			if (!flag) {
				$(".advancedSearch").slideUp();
			}
		}

	});
};

EventHandler.prototype.setCategoryOnLoadEvent = function() {
	
	$(window).bind('resize',function() {
		var bodyHeight = $("body").height();
		var headerHeight = $(".headerWrap").height();
		$("#tocContent").height(bodyHeight - headerHeight - 2);
		$(".dynatree-container").height(bodyHeight - headerHeight - 12);
	}).trigger('resize');
	
};

EventHandler.prototype.setFrameOnLoadEvent = function() {
	
	$('iframe').load(function() {
		
		var frameBody = this.contentWindow.document.body;
		
		if(frameBody.children.length > 0){
			var frameId = frameBody.children[0].value;
			if(frameId == "content"){
				var tree = frameBody.children[1].value;
//				var highlightQuery = frameBody.children[2].value;
//				Search.prototype.searchHighlight(highlightQuery);
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
//							console.log("requestCount="+requestCount+", totalCount=" + $("#frameFormTotalCount").val());
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
};
