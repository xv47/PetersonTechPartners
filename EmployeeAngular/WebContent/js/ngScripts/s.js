function myFactory($cookieStore, $http, $rootScope) {
return {
		login : function(credentials, locationData) {
			$rootScope.doShowProgress();
			var requestData = angular.toJson(credentials);
			$http
					.post(
							"Login",
							requestData,
							{
								headers : {
									'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
								}
							}).success(
							function(responseData) {
								$rootScope.doHideProgress(
										$rootScope.sharedVars.SUCCESS,
										responseData, null, "Login");
							}).error(
							function(responseData) {
								$rootScope.doHideProgress(
										$rootScope.sharedVars.FAIL,
										responseData, null, "Login");
							});
		},
		register : function(businessOwner, locationData) {
			var transform = function(data) {
				return $.param(data);
			}
			$rootScope.doShowProgress();
			var requestData = angular.toJson(businessOwner);
			$http
					.post(
							"Register",
							requestData,
							{
								headers : {
									'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
								}
							}).success(
							function(responseData) {
								$rootScope.doHideProgress(
										$rootScope.sharedVars.SUCCESS,
										responseData, null, null);
							}).error(
							function(responseData) {
								$rootScope.doHideProgress(
										$rootScope.sharedVars.FAIL,
										responseData, null, null);
							});
		}
	}
}
app.factory('sharedApplication', myFactory);
