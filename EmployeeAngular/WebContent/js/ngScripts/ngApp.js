var app = angular.module("ptech",['ngRoute','ngCookies','ui.bootstrap']);


app.config(['$routeProvider',
   function($routeProvider){
	$routeProvider
		.when('/',
			{
				controller: "consoleCtrl",
				templateUrl: 'fragment/base.html'
			})
		.when('/home',
			{
				controller: "consoleCtrl",
				templateUrl: 'fragment/base.html'
			
			})
		.when('/group',
			{
				controller: "groupCtrl",
				templateUrl: "fragment/groups.html"
			
			})
		.when('/timesheet',
			{
				controller: "timesheetCtrl",
				templateUrl: "fragment/timesheet.html"
			})
		.when('/approval',
			{
				controller: "approvalCtrl",
				templateUrl: "fragment/approval.html"
			})
		.otherwise({ redirectTo: '/' });

}]);