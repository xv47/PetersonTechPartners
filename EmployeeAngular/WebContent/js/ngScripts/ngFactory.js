/*
 * 
 * Factory for logins and registration 
 * 
 */
app
		.factory(
				'loginFactory',
				function($http) {
					return {
						// Get emails for login validation:
						getEmails : function() {
							return $http
									.post("http://localhost:8080/EmployeeAngular/UserAggregate");

						},
						sendLogin: function(email,password){
							var requestData = {
									"email" : email,
									"password" : password};
							return
								$http.post("http://localhost:8080/EmployeeAngular/Login",
										$.param(requestData),
										{
											headers : {
												'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
											}
										});
							
							
						},
						// Send data for registration to servlet:
						uploadRegister : function(employeeData) {
							var requestData = angular.toJson(employeeData);
							console.log(requestData);
							return $http
									.post(
											"http://localhost:8080/EmployeeAngular/Register",
											requestData,
											{
												headers : {
													'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
												}
											});
						},
						bootConsole : function() {
							return $http
									.post("http://localhost:8080/EmployeeAngular/Console");
						}
					}

				});
/*
 * 
 * Factory for groups
 * 
 */
app
		.factory(
				'groupFactory',
				function($http) {
					return {
						joinGroup : function(group) {
							$http
									.post(
											"http://localhost:8080/EmployeeAngular/JoinGroupServlet",
											{
												// Data packets:
												'groupCname' : group
											});
						},
						getGroupMembers : function() {
							return $http
									.post("http://localhost:8080/EmployeeAngular/GetGroupMembers");
						},
						addGroupMember : function(uID) {

							var obj = {
								'email' : uID
							};
							console.log("Passing " + uID + " to servlet");
							return $http
									.post(
											"http://localhost:8080/EmployeeAngular/AddGroupMember",
											$.param(obj),
											{
												headers : {
													'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
												}
											});
						},
						removeGroupMember : function(uID, gID) {
							var obj = {
								id : uID,
								gID : gID
							};
							return $http
									.post(
											"http://localhost:8080/EmployeeAngular/RemoveGroupMember",
											$.param(obj),
											{
												headers : {
													'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
												}

											});
						}
					}
				});

/*
 * Approval Factory
 * 
 */
app
		.factory(
				'approvalFactory',
				function($http) {
					return {
						rejectUser : function(id) {
							var obj = {
								"userID" : id
							}
							$http
									.post(
											"http://localhost:8080/EmployeeAngular/AdminUserReject",
											$.param(obj),
											{
												headers : {
													'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
												}
											});
						},
						
						approveUser : function(id) {
							var obj = {
								"userID" : id
							}
							$http
									.post(
											"http://localhost:8080/EmployeeAngular/AdminUserApprove",
											$.param(obj),
											{
												headers : {
													'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
												}
											});
						}
					}
				});
