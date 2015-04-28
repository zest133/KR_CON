<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="contents" style="padding-left: 270px;">
	<div>
		<h1>Advanced Search</h1>

		<div>
				<label>Search Word</label>
				<ul class="searchOptionsField">
					<li><label>all these words</label> <input type="text"
						id="searchAND" name="searchAND"></li>

					<li><label>any of these words</label> <input type="text"
						id="searchOR" name="searchOR"></li>

					<li><label>this exact word or phrase</label> <input
						type="text" id="searchExact" name="searchExact"></li>

					<li><label>none of these words</label> <input type="text"
						id="searchNON" name="searchNON"></li>
				</ul>
				
				<label>Search Filter</label>
				<ul class="searchOptionsCategory">
					<li>
						<label>Breadcrumbs</label>
						
						<select id="filterBreradcrumbsList" name="filterBreradcrumbsList">
							
							<c:forEach items="${filters.breadcrumbsFilter}" var="item">
								<option value="${item}">${item}</option>
							</c:forEach>
							
						</select>
					</li>
					
					<li>
						<label>Title</label>
						
						<select id="filterTitleList" name="filterTitleList">
							
							<c:forEach items="${filters.titleFilter}" var="item">
								<option value="${item}">${item}</option>
							</c:forEach>
							
						</select>
					</li>
					<li>
						<label>Locale</label>
						<select id="filterLocaleList" name="filterLocaleList">
							<option value="en">en</option>
						</select>
					</li>
				</ul>
				<input type="button" value="Search" id="advabcedSearchButton">
		</div>
	</div>
</div>

<style>
label {
	display: inline-block;
	width: 200px;
}

.contents ul li input, .contents ul li select {
	width: 400px;
}

</style>
