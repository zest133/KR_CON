<div id="tocDiv" style="overflow-x: hidden;">
	<div class="empty"></div>
	<div style="overflow-y: auto; overflow-x: hidden; height: 100%;">
	<!-- 
		<button id="btnLoadKeyPath">Load node by path</button>
		<button id="btnCollapseAll">Collapse All</button>
	 -->
		<div id="tocContent"></div>
	</div>
</div>
<div class="header">
	<div class="headerWrap">
		<div class="searchWrap">
			<div class="search_from_valign">
				<input id="searchinputbox" type="text" name="keyword">
			</div>
			<input id="searchLink" type="button" title="Search" alt="Search">
		</div>
		
		<input type="button" value="Advanced Search" id="advSearchButton">
		<!-- 
		<form class="searchWrap" action="search.do" method="post">
			<div class="search_from_valign">
				<input id="searchinputbox" type="text" name="keyword">
			</div>
			<input id="searchLink" type="submit" title="Search" alt="Search">
		</form>
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

<style>

#tocContent {
	height: 90%;
	
}

div#tocDiv{
	border-right: 1px solid grey;
}

ul.dynatree-container {
	border: none;
	height: 100%;
}
</style>