/**
 * 
 */
var app = angular.module("ngTest",[]);

function Controller($scope) {
	$scope.counter = 0;
	$scope.change = function() {
	$scope.counter++;
	};
	
	
}
function Lister($scope){
	
	$scope.cities = ['chicago', 'miami', 'los angeles'];
}

function valueChoices($scope){
	 $scope.names = ['pizza', 'popcorn', 'candy'];
	 $scope.my = 'candy';
	 
	
}

function blurFocus($scope){
	$scope.bfClass = 'bgRed';
	
	$scope.press = function(){
		$scope.v = true;
	}
	
	$scope.doBlur = function(){
		console.log("Blur");
		$scope.bfClass = 'bgRed';
	};
	$scope.doFocus = function(){
		console.log("Focus");
		$scope.bfClass= 'bgBlue';
	};
	
}

function switchCtrl($scope){
	$scope.seasons = ['winter','spring','summer','fall'];
	
	$scope.switchOn = function(switcher){
		
	}
}

app.controller('bindHTML', ['$scope', function bindHTML($scope) {
	$scope.myHTML =
		'I am an <code>HTML</code>string with <a href="#">links!</a> and other <em>stuff</em>';
		}]);

