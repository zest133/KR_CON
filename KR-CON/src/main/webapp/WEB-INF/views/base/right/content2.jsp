<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="graph_area"></div>
<div class="contents" style="padding-left: 270px;"></div>

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