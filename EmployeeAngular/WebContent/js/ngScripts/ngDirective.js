app.directive('timesheet', function(){
	return{
		restrict: 'E',
		scope: {
			header: '=',
			submitted: '=',
			approved: '='
			

		},
		templateUrl: 'template/timesheetModal.html',
		controller: function($scope){
			console.log($scope.person);			
		}
	};
}); 