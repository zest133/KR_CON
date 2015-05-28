<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%> 
<div id="graph_area"></div>
<div class="contents">
	<iframe name="iframe" src="" frameborder=0 class="contentsFrame" src="" width=100% height=100% ></iframe>
</div>

<div style="display: none;">
	<form id="categoryFrameForm" action="current_html.do" target="iframe" method="post">
		<input type="hidden" id="frameFormCategoryTree" name="categoryTree">
		<input type="hidden" id="frameFormHighlightQuery" name="highlightQuery">
	</form>
	
	<form id="searchFrameForm" action="search.do" target="iframe" method="post">
		<input type="hidden" id="frameFormSearchAND" name="searchAND">
		<input type="hidden" id="frameFormSearchOR" name="searchOR">
		<input type="hidden" id="frameFormSearchExact" name="searchExact">
		<input type="hidden" id="frameFormSearchNON" name="searchNON">
		<input type="hidden" id="frameFormFilterBreradcrumbsList" name="filterBreradcrumbsList">
		<input type="hidden" id="frameFormFilterTitleList" name="filterTitleList">
		<input type="hidden" id="frameFormFilterLocaleList" name="filterLocaleList">
		<input type="hidden" id="frameFormPageNum" name="pageNum">
		<input type="hidden" id="frameFormTotalCount" name="totalCount">
	</form>
</div>

<div class="advancedSearch">
	<h1 class="advancedSearchTitle">Advanced Search</h1>

	<div>
		<div class="optionDiv">
			<label>Search Word</label>
			<ul class="searchOptionsField">
				<li><label>All these words</label> <input type="text"
					id="searchAND" name="searchAND"></li>

				<li><label>Any of these words</label> <input type="text"
					id="searchOR" name="searchOR"></li>

				<li><label>Within word or phrase</label> <input type="text"
					id="searchExact" name="searchExact"></li>

				<li><label>None of these words</label> <input type="text"
					id="searchNON" name="searchNON"></li>
			</ul>
		</div>

		<div class="optionDiv">
			<label>Search Filter</label>
			<ul class="searchOptionsCategory">
				<li><label>Category</label> <select
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
						<option value="en">EN</option>
				</select></li>
			</ul>
		</div>

		<div class="buttonDiv">
			<input type="button" value="Search" id="advabcedSearchButton">
		</div>
	</div>
</div>