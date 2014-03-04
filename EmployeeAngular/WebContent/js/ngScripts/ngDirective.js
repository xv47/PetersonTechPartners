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

app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);