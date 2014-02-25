var app = angular.module("EmployeeAngular",['ngResource']);

app.config(['$routeProvider',function($routeProvider){
	$routeProvider
		.when('/',
			{
				controller: 'MainController',
				templateUrl: 'fragment/home.html'
			})
		.when('/test',
			{
				controller: 'MainController',
				templateUrl: 'fragment/index.html'
			})
		.otherwise({ redirectTo: '/' });
			
}]);