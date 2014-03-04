/*
 * 
 * Controller for the login page
 * 
 */
app.controller("loginCtrl",
		function($scope, $http, $cookieStore, loginFactory) {

			$scope.emails = [];
			loginFactory.getEmails().success(function(data) {
				$scope.emails = data;
			});

			$scope.isValid = false;

			console.log("emails in controller: " + $scope.emails);

			$scope.init = function() {
				var cook = $cookieStore.get("email");
				if (cook != null) {
					window.location = "/EmployeeAngular/console.html";
				}
			}

			$scope.checkEmail = function() {
				for (var i = 0; i < $scope.emails.length; i++) {
					console.log($scope.userEmail);
					console.log($scope.emails[i].email);
					if (($scope.userEmail != $scope.emails[i].email)) {
						$scope.isValid = false;
					} else {
						$scope.isValid = true;
						break;
					}
				}
				console.log("allEmails: " + email);
			};
			$scope.loginButton = function() {
				loginFactory.sendLogin($scope.userEmail, $scope.userPassword)
						.success(function() {
							window.location = "/EmployeeAngular/console.html";
						});

			}

		});
/*
 * 
 * Controller for the registration page:
 * 
 */
app
		.controller(
				"registerCtrl",
				function($scope, loginFactory, dialogService) {
					$scope.isAvailable = true;

					$scope.emails = [];
					loginFactory.getEmails().success(function(data) {
						$scope.emails = data;
					});
					// On Registration, check if Email is already registered:
					$scope.checkEmail = function() {
						for (var i = 0; i < $scope.emails.length; i++) {
							console.log($scope.userEmail);
							console.log($scope.emails[i].email);
							if (($scope.userEmail != $scope.emails[i].email)) {
								$scope.isAvailable = true;
							} else {
								$scope.isAvailable = false;
								break;
							}
						}
						console.log("allEmails: " + email);
					};
					// Upload data to servlet:
					$scope.register = function() {
						$scope.regData = [];

						// Assemble the data into one object
						$scope.regData.push($scope.userEmail, $scope.pw,
								$scope.fname, $scope.lname, $scope.street,
								$scope.city, $scope.state, $scope.zip,
								$scope.zip);
						console.log($scope.regData);
						// Send object to servlet:
						loginFactory
								.uploadRegister($scope.regData)
								.success(
										function() {
											// On success, send them to login
											// page:
											console
													.log("Successfully registered data to servlet");
											window.location = "http://localhost:8080/EmployeeAngular/login.html";
										})
								.error(
										function() {
											// Send them to the registration
											// page again since it didn't work
											console
													.log("Failed to upload registration data");
											window.location = "http://localhost:8080/EmployeeAngular/register.html";
										});
					};

					// Pictures Uploading:
					$scope.fileUpload = function() {
						var em = $scope.userEmail;
						console.log("Email for hashing: " + em);

						$('input[type="file"]').ajaxfileupload({
							'action' : 'UploadFile',
							'params' : {
								'email' : em
							},
							'onComplete' : function(response) {
								$('#uploadComment').hide();
								// $('#imgName').html(filename);
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

app.controller("consoleCtrl", function($scope, $http, $cookieStore,
		loginFactory) {
	$scope.emp = [];
	var personal = [];
	var mgrGroup = [];
	var memGroup = [];
	var approvals = [];

	// First check if user is logged in
	if ($cookieStore.get("email") == null) {
		console.log("Cannot find cookies");
		window.location = "/EmployeeAngular/login.html";

	} else {
		$scope.modalMessage = "Loading View...";
		$("#loadingModal").modal("show");
		// Load logged in employee data:
		loginFactory.bootConsole().success(function(data) {

			console.log("Data retrieved from servlet: " + data);
			$scope.emp = data;
			// Break down the array:
			personal = $scope.emp[0]; // Personal Information
			mgrGroup = $scope.emp[1]; // Manager: groups being managed
			memGroup = $scope.emp[2]; // Dev & QA: group membership
			approvals = $scope.emp[3]; // employees to approve

			console.log("Role: " + personal.role);

			if (personal.role != "MANAGER") {
				$scope.isManager = false;

			} else {
				$scope.isManager = true;
			}

		});

	}

	$scope.logout = function() {
		$cookieStore.remove("email");

	}

});

/*
 * 
 * Groups Controller:
 * 
 */
app.controller("groupCtrl", function($scope, $cookieStore, groupFactory) {
	$scope.mGroup = [];

	$scope.joinGroup = function() {
		groupFactory.joinGroup($scope.groupName).success(function() {
			console.log("Successfully joined group " + $scope.groupName);
		}).error(function() {
			console.log("Unable to join group " + $scope.groupName);
			$("#noGroup").show();
		});
	}
	$scope.groupModal = function(index) {
		// Passing data from ng-repeat object:
		var group = angular.copy($scope.emp[1][index]);

		console.log("Index: " + index);
		// Set the cookie for servlet collection
		console.log(group);
		$cookieStore.put("groupID", group.id);

		$scope.mGroup = groupFactory.getGroupMembers().success(function(data) {
			$scope.mGroup = data;
		});

		$("#groupModal" + index).modal();

	}
	$scope.removeMember = function(userID, groupID) {
		console.log("Remove " + userID + " From " + groupID);
		groupFactory.removeGroupMember($scope.mGroup[userID].id, groupID)
				.success(function() {
					$scope.mGroup.splice(userID, 1);
				});
	}
	$scope.addMember = function(member) {
		console.log("Add " + member);
		groupFactory.addGroupMember(member).success(function(data) {
			// Successfully Added Member
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
app.controller("approvalCtrl", [ '$scope', 'approvalFactory',
		function($scope, approvalFactory) {

			$scope.func = function(index) {
				var user = angular.copy($scope.emp[3][index]);

				console.log("index: " + index);
				$("#userModal" + index).modal();
			};

			$scope.rejectUser = function(index) {
				var user = angular.copy($scope.emp[3][index]);

				console.log("Employee to delete: " + user.id);
				approvalFactory.rejectUser(user.id).success(function() {
					// Popup saying all went ewll
				});
				$("#userModal" + index).modal('hide');
			};

			$scope.approve = function(index) {
				var user = angular.copy($scope.emp[3][index]);

				console.log("Employee to approve: " + user.id);

				approvalFactory.approveUser(user.id, index, $scope);

			};
			$scope.approveUserSuccessResponse = function(index, response) {
				$scope.emp[3].splice(index, 1);
				$("#userModal" + index).modal('hide');
			}
			$scope.approveUserFailureResponse = function(index, response) {

				console.log("Failed to approve");
			}

		} ]);

/*
 * 
 * Timesheet Controller
 * 
 * 
 */
app.controller("timesheetCtrl", function($scope, $http, timesheetFactory) {
	$scope.isSubmit = false;
	$scope.isApproved = false;
	$scope.isSuccess = false;

	$http.post("http://localhost:8080/EmployeeAngular/FetchTimesheet").success(
			function(data) {
				$scope.schedule = data;
				console.log("Timesheets: " + $scope.schedule);
				$scope.currentWeek = $scope.schedule.pop();

			});
	$http.post("http://localhost:8080/EmployeeAngular/FetchSubmitTimesheet")
			.success(function(data) {
				$scope.submittedTimesheet = data;
				console.log(data);
				$scope.tsPDF = []; // Holds the timesheets with PDFs
				for (var i = 0; i < data.length; i++) {
					if (data[i].pdf.length > 1 && data[i].approved == 0) {
						$scope.tsPDF.push(data[i]);
					}
				}
			});
	$scope.sumCurRegHours = function() {
		return $scope.currentWeek.mon + $scope.currentWeek.tue
				+ $scope.currentWeek.wed + $scope.currentWeek.thu
				+ $scope.currentWeek.fri + $scope.currentWeek.sat
				+ $scope.currentWeek.sun;
	}
	$scope.sumCurOTHours = function() {
		return $scope.currentWeek.mon_ot + $scope.currentWeek.tue_ot
				+ $scope.currentWeek.wed_ot + $scope.currentWeek.thu_ot
				+ $scope.currentWeek.fri_ot + $scope.currentWeek.sat_ot
				+ $scope.currentWeek.sun_ot;
	}
	$scope.showTimesheet = function(index) {
		console.log("Calling timesheet modal for index " + index);
		$scope.isSuccess = false;
		$("#tsModal" + index).modal();
	}
	$scope.checkSubmit = function(index) {
		return ($scope.schedule[index].submitted == 1) ? true : false;
	}
	$scope.checkApprove = function(index) {
		return ($scope.schedule[index].approved == 1) ? true : false;
	}

	$scope.checkCurWeekSubmit = function() {
		return ($scope.currentWeek.submitted == 1) ? true : false;
	}
	$scope.checkCurWeekApprove = function() {
		return ($scope.currentWeek.approved == 1) ? true : false;
	}

	$scope.updateTimesheet = function() {
		console.log($scope.currentWeek);
		timesheetFactory.updateTimesheet($scope.currentWeek);
		$scope.modalMsg = "Successfully Updated Timesheet";
		$("#tsMsg").modal();
	}

	$scope.submitTimesheet = function(idToSubmit) {
		console.log("Submitting timesheet id " + idToSubmit);
		var id = {
			"id" : idToSubmit
		}
		timesheetFactory.submitTimesheet(id);
		$scope.currentWeek.submitted = 1;
		$scope.modalMsg = "Successfully Submitted Timesheet";
		$("#tsMsg").modal();
	}
	$scope.submitOldTimesheet = function(index) {
		console.log("Submitting timesheet id " + $scope.schedule[index].id);
		$("#tsModal" + index).modal('hide');
		timesheetFactory.submitTimesheet($scope.schedule[index]);

		$scope.schedule[index].submitted = 1;

		$scope.modalMsg = "Successfully Submitted Timesheet";
		$("#tsMsg").modal();
	}
	$scope.generatePDF = function(week) {

		timesheetFactory.genPDF($scope.emp[0], week);
		console.log("PDF generated");
	}
	$scope.setFiles = function(element) {
		$scope.$apply(function($scope) {
			console.log('files:', element.files);
			// Turn the FileList object into an Array
			$scope.files = []
			for (var i = 0; i < element.files.length; i++) {
				$scope.files.push(element.files[i])
			}
			$scope.progressVisible = false;
			$scope.uploadButton = true;
		});
		
	};

	$scope.pdfUpload = function(id) {
		var em = $scope.emp[0].email;
		console.log("Email for hashing: " + em + " id of timesheet: " + id);

		var file = $scope.files[0];
		console.log(file);
		var uploadUrl = './UploadPDF';
		timesheetFactory.uploadFileToUrl(file, id, uploadUrl,$scope);

	};
	$scope.approveTimesheet = function(index) {
		console.log($scope.tsPDF[index].id);
		var id = $scope.tsPDF[index].id;
		timesheetFactory.approveTimesheet(id, index, $scope);
	}
	$scope.approveTimesheetSuccess = function(index, response) {
		$("#tsModal" + index).on('hidden.bs.modal', function() {
			location.reload();
			$scope.tsPDF.splice(index, 1);
		});

	}
	

});
