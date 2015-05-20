<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="graph_area"></div>
<div class="contents">
	<iframe name="iframe" src="" frameborder=0 class="contentsFrame" src="" width=100% height=100% ></iframe>
</div>

<div style="display: hidden;">
	<form id="categoryFrameForm" action="current_html.do" target="iframe" method="post">
		<input type="text" id="frameFormCategoryTree" name="categoryTree">
		<input type="text" id="frameFormHighlightQuery" name="highlightQuery">
	</form>
	
	<form id="searchFrameForm" action="search.do" target="iframe" method="post">
		<input type="text" id="frameFormSearchAND" name="searchAND">
		<input type="text" id="frameFormSearchOR" name="searchOR">
		<input type="text" id="frameFormSearchExact" name="searchExact">
		<input type="text" id="frameFormSearchNON" name="searchNON">
		<input type="text" id="frameFormFilterBreradcrumbsList" name="filterBreradcrumbsList">
		<input type="text" id="frameFormFilterTitleList" name="filterTitleList">
		<input type="text" id="frameFormFilterLocaleList" name="filterLocaleList">
		<input type="text" id="frameFormPageNum" name="pageNum">
		<input type="text" id="frameFormTotalCount" name="totalCount">
	</form>
</div>

<div class="advancedSearch">
	<h1 class="advancedSearchTitle">Advanced Search</h1>

	<div>
		<div class="optionDiv">
			<label>Search Word</label>
			<ul class="searchOptionsField">
				<li><label>all these words</label> <input type="text"
					id="searchAND" name="searchAND"></li>

				<li><label>any of these words</label> <input type="text"
					id="searchOR" name="searchOR"></li>

				<li><label>this exact word or phrase</label> <input type="text"
					id="searchExact" name="searchExact"></li>

				<li><label>none of these words</label> <input type="text"
					id="searchNON" name="searchNON"></li>
			</ul>
		</div>

		<div class="optionDiv">
			<label>Search Filter</label>
			<ul class="searchOptionsCategory">
				<li><label>Breadcrumbs</label> <select
					id="filterBreradcrumbsList" name="filterBreradcrumbsList">

						<c:forEach items="${filters.breadcrumbsFilter}" var="item">
							<option value="${item}">${item}</option>
						</c:forEach>

				</select></li>

				<li><label>Title</label> <select id="filterTitleList"
					name="filterTitleList">

						<c:forEach items="${filters.titleFilter}" var="item">
							<option value="${item}">${item}</option>
						</c:forEach>

				</select></li>
				<li><label>Locale</label> <select id="filterLocaleList"
					name="filterLocaleList">
						<option value="en">en</option>
				</select></li>
			</ul>
		</div>

		<div class="buttonDiv">
			<input type="button" value="Search" id="advabcedSearchButton">
		</div>
	</div>
</div>