var app = angular.module("ptech",['ngRoute','ngCookies','ui.bootstrap','angular-gestures']);


app.config(['$routeProvider',
   function($routeProvider){
	$routeProvider
		.when('/',
			{
				
				templateUrl: 'fragment/base.html'
			})
		.when('/home',
			{
				
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
		.when('/tsApproval',
			{
				controller: "timesheetCtrl",
				templateUrl: "fragment/tsApproval.html"
			})
		.when('/gallery',
			{
				controller: "approvalCtrl",
				templateUrl: "fragment/gallery.html"
			})
		.when('/search',
			{
				
				templateUrl: "fragment/search.html"
			})
		.otherwise({ redirectTo: '/' });

}]);