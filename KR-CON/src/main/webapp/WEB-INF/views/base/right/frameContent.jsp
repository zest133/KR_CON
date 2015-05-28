<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Search Result</title>
<link rel="stylesheet" type="text/css" href="css/english.chrome.css">
</head>
<body>
	<input type="hidden" id="frameId" value="content">
	<input type="hidden" id="frameCurrentTree" value="${categoryTree }">
	<input type="hidden" id="frameHighlightQuery" value="${highlightQuery }">
	
	<div id="frameDiv" style="height: 100%; overflow-y: auto;">
		${html}</div>
</body>
</html>

