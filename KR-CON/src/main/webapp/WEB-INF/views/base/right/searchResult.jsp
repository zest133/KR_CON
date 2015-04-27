<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="contents" style="padding-left: 270px;">
	<div class="result">
		<div class="enterKeyword">
			<c:if test="${searchKeyword == 'Advanced Search'}">
				<p>${searchKeyword}</p>
			</c:if>
			<c:if test="${searchKeyword != 'Advanced Search'}">
				<p>
					Enter Keyword: <span>${searchKeyword}</span>
				</p>
			</c:if>
		</div>
		
		<div class="stopWord">
				<p> Stop Word: 
			<c:forEach items="${stopWord}" var="item">
				${item},
			</c:forEach>
				</p>
		
		</div>
		
		<div class="spellCheck">
		</div>
		
		<div class="resultcount">
			<p>Search Results: (${resultSize})</p>
		</div>
		
		<div class="result_list">
			<c:forEach items="${searchResult}" var="item">
				<div class="filename">
					<p>
						<a href="#">${item.title}</a>
					</p>
					<div class="resultBreadCrumbs">
						<p>${item.breadcrumbs}</p>
					</div>
				</div>
				<div class="resulttext">
					<p>${item.htmlText}</p>
				</div>
			</c:forEach>
		</div>
	</div>

	<!-- 
	<div class="result">
		<div class="enterKeyword">
			<p>Enter Keyword: <span>blah blah</span></p>
		</div>
		<div class="resultcount">
			<p>Search Results: (4)</p>
		</div>
		<div class="result_list">
			<div class="filename">
				<p>
					<a href="pt01.htm?keyword=machine%20overview">Basic</a>
				</p>
			</div>
			<div class="resulttext">
				<p>Machine overview ...</p>
			</div>
			<div class="filename">
				<p>
					<a href="introduction.htm?keyword=machine%20overview">Introduction</a>
				</p>
			</div>
			<div class="resulttext">
				<p>Machine overview ...</p>
			</div>
			<div class="filename">
				<p>
					<a href="CACHDGFD.htm?keyword=machine%20overview">Machine
						overview</a>
				</p>
			</div>
			<div class="resulttext">
				<p>Machine overview ...</p>
			</div>
			<div class="filename">
				<p>
					<a href="CEGCGFCB.htm?keyword=machine%20overview">Printing
						quality problems</a>
				</p>
			</div>
			<div class="resulttext">
				<p>Machine overview ...</p>
			</div>
		</div>
	</div>
 -->
</div>
