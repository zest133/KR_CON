<%@ page language="java" contentType="text/html;charset=utf-8"%>
<div class="contents" style="padding-left: 270px;">
	<div>
		<h1>Advanced Search</h1>

		<div>
			<ul class="searchOptions">
				<li><label>all these words</label> <input type="text" id="searchAND"></li>

				<li><label>any of these words</label> <input type="text" id="searchOR"></li>

				<li><label>this exact word or phrase</label> <input type="text" id="searchExact"></li>

				<li><label>none of these words</label> <input type="text" id="searchNON"></li>
			</ul>
			
			<input type="button" value="Search" id="advabcedSearchButton">

		</div>
	</div>
</div>

<script>
$("#advabcedSearchButton").click(function(){
	var searchObj = new Search();
	searchObj.startSearch(true);
});
</script>