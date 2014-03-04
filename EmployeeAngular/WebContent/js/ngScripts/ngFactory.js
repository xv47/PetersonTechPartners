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
						
						approveUser : function(id,index,scope) {
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
											}).success(function(response){
												
												scope.approveUserSuccessResponse(index,response);
												console.log(JSON.stringify(response));
											}).error(function(response){
												scope.approveUserFailureResponse(index,response);
												console.log(JSON.stringify(response));
											});
						}
					}
				});

app.factory('timesheetFactory',function($http){
	return{
		updateTimesheet : function(timesheetData){
			
			$http.post("http://localhost:8080/EmployeeAngular/UpdateTimesheet",
					$.param(timesheetData),
					{
						headers : {
							'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
						}
					});
			},
		
		submitTimesheet: function(timesheetData){
			
			$http.post("http://localhost:8080/EmployeeAngular/SubmitTimesheet",
					$.param(timesheetData),
					{
						headers : {
							'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
						}
					});
		},
		approveTimesheet: function(timesheetData,index,scope){
			var requestData = {
					'id':timesheetData
			}
			$http.post("http://localhost:8080/EmployeeAngular/ApproveTimesheet",
					$.param(requestData),
					{
						headers : {
							'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
						}
					}).success(function(response){
						scope.approveTimesheetSuccess(index,response);
						console.log(JSON.stringify(response));
						
					});
		},
		genPDF: function(emp,ts){
			
			var doc = new jsPDF();
			doc.text(20,20,emp.fname + " " + emp.lname);
			doc.text(20,30,emp.street);
			doc.text(20,40,emp.city + ", " + emp.state + " " + emp.zip);
			
			doc.text(20,60, "Week of " + ts.week);
			
			doc.text(20,70, "Day");
			doc.text(50,70, "Regular Hours");
			doc.text(100,70, "Overtime Hours");
			
			doc.text(20,80, "Monday");
			doc.text(80,80, " " + ts.mon);
			doc.text(130,80, " " + ts.mon_ot);
			
			doc.text(20,90, "Tuesday");
			doc.text(80,90, " " + ts.tue);
			doc.text(130,90, " " + ts.tue_ot);
			
			doc.text(20,100, "Wednesday");
			doc.text(80,100, " " + ts.wed);
			doc.text(130,100, " " + ts.wed_ot);
			
			doc.text(20,110, "Thursday");
			doc.text(80,110, " " + ts.thu);
			doc.text(130,110, " " + ts.thu_ot);
			
			doc.text(20,120, "Friday");
			doc.text(80,120, " " + ts.fri);
			doc.text(130,120, " " + ts.fri_ot);
			
			doc.text(20,130, "Saturday");
			doc.text(80,130, " " + ts.sat);
			doc.text(130,130, " " + ts.sat_ot);
			
			doc.text(20,140, "Sunday");
			doc.text(80,140, " " + ts.sun);
			doc.text(130,140, " " + ts.sun_ot);
			
			
			doc.text(20, 170, "Employee Signature: _______________________________________");
			doc.text(20, 190, "Approving Manager: _________________________________________");
			
	        
			var string = doc.output('datauristring');
			  var x = window.open();
			  x.document.open();
			  x.document.location=string;
		},
		getSubmittedTimesheets : function(){
			$http.post("http://localhost:8080/EmployeeAngular/FetchSubmitTimesheet");
		},
		uploadFileToUrl : function(file, id, uploadUrl,scope){
	        var fd = new FormData();
	        fd.append('id', id);
	        fd.append('file', file);
	        
	        $http.post(uploadUrl, fd, {
	            transformRequest: angular.identity,
	            headers: {'Content-Type': undefined}
	        })
	        .success(function(){
	        	scope.isSuccess = true;
	        })
	        .error(function(){
	        });
	    }
	}
});
