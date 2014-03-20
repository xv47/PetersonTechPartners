
describe("loginCtrl", function(){
	var scope;
	
	beforeEach(angular.mock.module("ptech"));
	beforeEach(angular.mock.inject(function($rootScope, $controller){
		scope = $rootScope.$new();
		
		$controller("loginCtrl", {$scope : scope});
	}));
	
	
});