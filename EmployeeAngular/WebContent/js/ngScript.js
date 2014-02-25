/**
 * 
 */
var app = angular.module('EmployeeAngular',[]);

app.controller("SampleController",sampleFunction);

function sampleFunction($scope){
	$scope.welcomeMsg = "Hello World";
	$scope.newVar = ["This is some string", "How is Everyone?", "I don't like snow"];
	$scope.dropDown="This is some string";
	$scope.clickButton=function(){
		$scope.newMessage = $scope.welcomeMsg;
		$scope.welcomeMsg = "";
	}
	
	$scope.selectDropDown=function(index){
		console.log(index);
		$scope.newMessage = $scope.dropDown;
	}
	
}

