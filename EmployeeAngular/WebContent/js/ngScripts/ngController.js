
/*
 * 
 * Controller for the login page
 * 
 */
app.controller("loginCtrl",  function($scope,$http,$cookies,loginFactory){
	 
	$scope.emails = [];
	loginFactory.getEmails().success(function(data){
		$scope.emails = data;
	});
	
	$scope.isValid = false;	
	
	console.log("emails in controller: " + $scope.emails);
	
	$scope.init = function(){
		if($cookies.get("email") != null){
			window.location = "/EmployeeAngular/console.html";
		}
	}
	
	$scope.checkEmail = function(){
		for(var i = 0; i < $scope.emails.length; i++){
			console.log($scope.userEmail);
			console.log($scope.emails[i].email);
			if(($scope.userEmail != $scope.emails[i].email)){
				$scope.isValid = false;
			}
			else{
				$scope.isValid = true;
				break;
			}
		}
		console.log("allEmails: " + email);
	};
	$scope.loginButton = function(){
		window.location = "/EmployeeAngular/Login";
	}
	
});
/*
 * 
 * Controller for the registration page:
 * 
 */
app.controller("registerCtrl", function($scope,loginFactory){
	$scope.isAvailable = true;
	
	$scope.emails = [];
	loginFactory.getEmails().success(function(data){
		$scope.emails = data;
	});
	//On Registration, check if Email is already registered:
	$scope.checkEmail = function(){
		for(var i = 0; i < $scope.emails.length; i++){
			console.log($scope.userEmail);
			console.log($scope.emails[i].email);
			if(($scope.userEmail != $scope.emails[i].email)){
				$scope.isAvailable = true;
			}
			else{
				$scope.isAvailable = false;
				break;
			}
		}
		console.log("allEmails: " + email);
	};
	//Upload data to servlet:
	$scope.register = function(){
		$scope.regData = [];
		
		//Assemble the data into one object
		$scope.regData.push($scope.userEmail,$scope.pw,$scope.fname,
				$scope.lname, $scope.street, $scope.city, $scope.state,
				$scope.zip, $scope.zip);
		console.log($scope.regData);
		//Send object to servlet:
		loginFactory.uploadRegister($scope.regData).success(function(){
			//On success, send them to login page:
			console.log("Successfully registered data to servlet");
			window.location = "http://localhost:8080/EmployeeAngular/login.html";
		}).error(function(){
			//Send them to the registration page again since it didn't work
			console.log("Failed to upload registration data");
			window.location = "http://localhost:8080/EmployeeAngular/register.html";
		});
	};
	
	//Pictures Uploading:
	$scope.fileUpload = function(){
		var em = $scope.userEmail;
		console.log("Email for hashing: " + em);
		 
		$('input[type="file"]').ajaxfileupload({
			'action' : 'UploadFile',
			'params' : {
				'email' : em
			},
			'onComplete' : function(response) {
				$('#uploadComment').hide();
				//$('#imgName').html(filename);
			},
			'onStart' : function() {
				$('#uploadComment').show();

			}
		});
	};
});
/*
 * 
 * Main Console Controller:
 * 
 */

app.controller("consoleCtrl", function($scope,$http,$cookies,loginFactory){
	$scope.emp = [];	
	var personal = [];
	var mgrGroup = [];
	var memGroup = [];
	var approvals = [];
	

	$scope.init = function(){
		//First check if user is logged in
		if ($cookieStore.get("email") == undefined) {
			console.log("Cannot find cookies");
			window.location = "/EmployeeAngular/login.html";
	
		}else{
			//Load logged in employee data:
			loginFactory.bootConsole().success(function(data){
				
				console.log("Data retrieved from servlet: " + data);
				$scope.emp = data;
				//Break down the array:
				personal = $scope.emp[0];	//Personal Information
				mgrGroup = $scope.emp[1];	//Manager: groups being managed
				memGroup = $scope.emp[2];	//Dev & QA: group membership
				approvals = $scope.emp[3];  //employees to approve	
				
				$("#regGroups").hide();
	
			});
		
		}		
		
	};
	
	$scope.logout = function(){
		$cookieStore.remove("email");
		
	}
	
});

/*
 * 
 * Groups Controller:
 * 
 */
app.controller("groupCtrl", function($scope,groupFactory){
	$scope.mGroup = [];

	$scope.joinGroup = function(){
		groupFactory.joinGroup($scope.groupName).success(function(){
			console.log("Successfully joined group " + $scope.groupName);
		}).error(function(){
			console.log("Unable to join group " + $scope.groupName);
			$("#noGroup").show();
		});
	}
	$scope.groupModal = function(index){
		//Passing data from ng-repeat object:
		var group = angular.copy($scope.emp[1][index]);
		
		console.log("Index: " + index);
		//Set the cookie for servlet collection
		console.log(group);
		$.cookie("groupID", group.id);
				
		$scope.mGroup = groupFactory.getGroupMembers().success(function(data){
			$scope.mGroup = data;
		});
		
		$("#groupModal"+index).modal();
		
	}
	$scope.removeMember = function(userID, groupID){
		console.log("Remove " + userID + " From " + groupID);
		groupFactory.removeGroupMember($scope.mGroup[userID].id,groupID).success(function(){
			$scope.mGroup.splice(userID,1);
		});
	}
	$scope.addMember = function(member){
		console.log("Add " + member);
		groupFactory.addGroupMember(member).success(function(data){
			//Successfully Added Member
			console.log(data);
			$scope.mGroup.push(data);
		});
	}
});
/*
 * 
 * Approval Controller
 * 
 */
app.controller("approvalCtrl", function($scope,approvalFactory){
	
	$scope.func = function(index){
		var user = angular.copy($scope.emp[3][index]);
		
		console.log("index: " + index);
		$("#userModal" + index).modal();
	};
	
	$scope.rejectUser = function(index){
		var user = angular.copy($scope.emp[3][index]);
		
		console.log("Employee to delete: " + user.id);
		approvalFactory.rejectUser(user.id).success(function(){
			//Popup saying all went ewll
		});
	};
	
	$scope.approve = function(index){
		var user1 = angular.copy($scope.emp[3][index]);
		
		console.log("Employee to approve: " + user1.id);
		random();
		
		approvalFactory.approveUser(9).success(random());
		
	};
});
function random(){
	console.log("randomadslfjadsfhioadsf");
}
/*
 * 
 * Timesheet Controller
 * 
 * 
 */
app.controller("timesheetCtrl", function($scope){
	
});
