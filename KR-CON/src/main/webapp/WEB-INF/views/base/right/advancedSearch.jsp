<%@ page language="java" contentType="text/html;charset=utf-8"%>
<div class="contents" style="padding-left: 270px;">
	<div>
		<h1>Advanced Search</h1>

		<div>
			<form action="adv_search.do" method="post">
			<ul class="searchOptions">
				<li><label>all these words</label> <input type="text" id="searchAND" name="searchAND"></li>

				<li><label>any of these words</label> <input type="text" id="searchOR" name="searchOR"></li>

				<li><label>this exact word or phrase</label> <input type="text" id="searchExact" name="searchExact"></li>

				<li><label>none of these words</label> <input type="text" id="searchNON" name="searchNON"></li>
			</ul>
			
			<input type="submit" value="Search" id="advabcedSearchButton">
			</form>

		</div>
	</div>
</div>
