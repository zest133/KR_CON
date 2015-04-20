<div id="tocDiv" style="overflow-x: hidden;">
	<div class="empty"></div>
	<div style="overflow-y: auto; overflow-x: hidden; height: 90%;">
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
			}
		});
	});
</script>

<style>
	ul.dynatree-container {
		border: none;
	}
</style>