<div id="tocDiv" style="overflow-x: hidden;">
	<div class="empty"></div>
	<div style="overflow-y: auto; overflow-x: hidden; height: 90%;">
		<button id="btnLoadKeyPath">Load node by path</button>
		<button id="btnCollapseAll">Collapse All</button>
		<div id="tocContent"></div>
	</div>
</div>
<div class="header">
	<div class="headerWrap">

		<form class="searchWrap" action="search.do" method="post">
			<div class="search_from_valign">
				<input id="searchinputbox" type="text" name="keyword">
			</div>
			<input id="searchLink" type="submit" title="Search" alt="Search">
		</form>
		<!-- 
		<form class="searchform" action="search.do">
			<input class="searchfield" type="text" 
				value="Search..."
				onfocus="if (this.value == 'Search...') {this.value = '';}"
				onblur="if (this.value == '') {this.value = 'Search...';}" >
			<input class="searchbutton" type="Submit" value="Search">
		</form>
		 -->
	</div>
</div>

<script type="text/javascript">
	$(function() {
		$("#tocContent").dynatree({
			initAjax : {
				url : "root_category.do"
			},
			onActivate : function(node) {
				alert(node.data.title);
			},
			onLazyRead : function(node) {
				node.appendAjax({
					url : "sub_category.do",
					data : {
						key : node.data.key
					}
				});
			}
		});

		$("#btnLoadKeyPath").click(
				function() {
					var tree = $("#tocContent").dynatree("getTree");

					tree.loadKeyPath("/folder1/folder1/folder1/folder1",
							function(node, status) {
								if (status == "loaded") {
									node.expand();
								} else if (status == "ok") {
									node.activate();
								}
							});
				});

		$("#btnCollapseAll").click(function() {
			$("#tocContent").dynatree("getRoot").visit(function(node) {
				node.expand(false);
			});
			return false;
		});

	});
</script>

<style>
ul.dynatree-container {
	border: none;
}
</style>