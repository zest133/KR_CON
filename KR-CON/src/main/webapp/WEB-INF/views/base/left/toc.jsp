<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%> 
<div id="tocDiv" >
		<!-- 
		<button id="btnLoadKeyPath">Load node by path</button>
		<button id="btnCollapseAll">Collapse All</button>
	 -->
		<div id="tocContent"></div>
</div>
<div class="header">
	<div class="headerWrap">
		<input type="button" value="Advanced Search" id="slideAdvSearchButton">
		<!-- 
		<div class="searchWrap">
			<div class="search_from_valign">
				<input id="searchinputbox" type="text" name="keyword">
			</div>
			<input id="searchLink" type="button" >
		</div>
		 -->
		 
		<div id="search">
			<input type="search" name="search" id="searchinputbox"> <input
				id="searchLink" type="button" value="Search" class="button"
				title="Search" alt="Search">
		</div>

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
