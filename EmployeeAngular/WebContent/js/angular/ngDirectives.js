app.directive('employeePanel', function(){
	return{
		restrict: 'E',
		scope: {
			person: '=',
			job: '='

		},
		templateUrl: 'template/employeePanel.html',
		controller: function($scope){
			console.log($scope.person);			
		}
	};
}); 