<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Angular Tree View</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.min.css" />
<!--[if IE 7]>
		  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome-ie7.min.css" />
		<![endif]-->

<link rel="stylesheet" href="css/style.css" />
<!--[if !IE]> -->
<script src="js/jquery-2.1.3.js"></script>
<!-- <![endif]-->
<!--[if IE]>
		<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
		<![endif]-->

<script src="js/angular.js"></script>
<script src="js/treeView.js"></script>

<script>
	(function() {
		var app = angular.module('Demo', [ 'AxelSoft' ]);

		app.controller('DemoController', function($scope, $http) {
			
		});
		app
				.directive(
						'whoiam',
						function($http) {
							return {
								restrict : 'E',

								template : '<div tree-view="structure" tree-view-options="options"></div>',

								link : function(scope, element, attrs) {
									$http.get('root_category2.do').success(
											function(data) {
												scope.structure = data;
											});
								}

							};
						});
	})();
</script>


</head>

<body ng-app="Demo">
	<div class="container" ng-controller="DemoController">
		<div>Root</div>
		<whoiam>aaaaaaaaa</whoiam>
		
	</div>


</body>
</html>
