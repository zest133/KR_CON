<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input type="hidden" id="frameId" value="search">
<link rel="stylesheet" type="text/css" href="css/english.chrome.css">


<div class="result">
	<c:if test="${pageNum == '0'}">

		<div class="enterKeyword">
			<c:if test="${searchKeyword == ''}">
				<p>Advanced Search</p>
			</c:if>
			<c:if test="${searchKeyword != ''}">
				<p>
					Enter Keyword: <span>${searchKeyword}</span>
				</p>
			</c:if>
			<input type="hidden" id="highlightQuery" value="${highlightQuery }">
		</div>

		<c:if test="${stopWord != null}">
			<div class="stopWord">
				<p>
					Stop Word:
					<c:forEach items="${stopWord}" var="item">
						${item},
					</c:forEach>
				</p>
			</div>
		</c:if>

		<c:if test="${ synonym != ''}">
			<div class="synonymWord">
				<p>
					Did you mean: <a href="#"
						onclick="parent.Search.prototype.synonymSearch()">${synonym}</a> <input
						type="hidden" id="synonym" value="${synonym }">
				</p>
			</div>
		</c:if>

		<div class="spellCheck"></div>

		<div class="resultcount">
			<p>Search Results: (${resultSize})</p>
			<input type="hidden" value="${resultSize}" id="resultCount">
		</div>
	</c:if>

	<div class="result_list">
		<c:forEach items="${searchResult}" var="item">
			<div>
				<div class="otherInfo">
					<label>Rank = </label> <span class="rank"> ${item.rank} </span> <label>View
						= </label> <span class="view"> ${item.view} </span>
				</div>
				<div class="filename">
					<p>
						<a href="#"
							onclick="parent.CategoryTree.prototype.openCurrentTree('${item.categoryTree}'); return false;">
							${item.title} </a>
					</p>
					<div class="resultBreadCrumbs">
						<p>${item.breadcrumbs}</p>
					</div>
				</div>

				<div class="resulttext">
					<p>${item.htmlText}</p>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
