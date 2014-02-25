app.controller("MainController", function($scope, employeeFactory){
	$scope.selectedPerson = 0;
	$scope.selectedProject = null;
	$scope.employee = employeeFactory.getEmployees();

	$scope.addProject = function(pID,project){
		console.log(project);
		$scope.employee[pID].project.push(project);
		
	};
	$scope.removeProject = function(pID,project){
		console.log(project);
		$scope.employee[pID].project.pop(project);
	};
	
	$scope.addEmp = function(newName,newRole){
		console.log($scope.newName + " " + $scope.newRole);
		$scope.employee.push({
			name: newName,
			role: newRole,
			project: []
		});
		console.log($scope.employee);
	};
});