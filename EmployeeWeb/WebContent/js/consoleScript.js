//Globals:
var UserToApprove;
var group;


if ($.cookie("email") == undefined) {
	window.location = "/EmployeeWeb/login.html";

}
function joinGroup() {
	var g = $("#groupCname").val();
	console.log(g);
	if (g.length > 0) {
		$.ajax({
			type : 'POST',
			url : 'JoinGroupServlet',
			data : {
				groupCname : g
			},
			error : function() {
				$("#noGroup").show();
			}
		});
	}
}
function approveUser(u) {
	$.ajax({
		type : 'POST',
		url : 'AdminUserApprove',
		data : {
			userID : (u)
		},
		success : function() {
			$("#userModal").modal('hide').on(
					"hidden.bs.modal",
					function() {
						location.reload();
						/*
						$("#alertEmpActivatedSuccess").show().bind(
								"closed.bs.alert", function() {
									location.reload();

								});
								*/
					});

		}
	});
}
function rejectUser(u) {
	alert("rejecting " + u);
	$.ajax({
		type : 'POST',
		url : 'AdminUserReject',
		data : {
			userID : (u)
		},
		success : function() {
			$("#userModal").modal('hide').on(
					"hidden.bs.modal",
					function() {
						location.reload();
						/*
						$("#alertEmpRejectedSuccess").show().bind(
								"closed.bs.alert", function() {
									location.reload();

								});
								*/
					});

		}
	
	});
}
function removeMember(i,o){
	$.ajax({
		type: 'POST',
		url: 'RemoveGroupMember',
		data:{
			id : i,
			gID: o
		},success : function(){
			$("#groupModal").modal('hide').on(
					"hidden.bs.modal",
					function(){
						location.reload();
					});
		}
	});
}
function func(i, u) {

	$("#userModalBody").html(
			"<img src='" + UserToApprove[i].img
					+ "' class='img-rounded' width=280><br>"
					+ UserToApprove[i].fname + " " + UserToApprove[i].lname
					+ " <br>" + UserToApprove[i].street + "<br>"
					+ UserToApprove[i].city + ", " + UserToApprove[i].state
					+ " " + UserToApprove[i].zip + "<br>"
					+ UserToApprove[i].email + "<br>" + UserToApprove[i].role);
	$("#userModal").modal();
	$("#approveUserBtn").attr("onclick", "approveUser(" + u + ")");
	$("#rejectUserBtn").attr("onclick", "rejectUser(" + u + ")");
}

$(document)
		.ready(
				function() {
					$.post("JoinGroupServlet", function(data) {
						if (data.message == "invalid") {
							alert("Group is not real");
						}

					});
					$.validate({
						validateOnBlur : false, // disable validation when input
						// looses focus
						errorMessagePosition : 'top', // Instead of 'element'
						// which is default
						scrollToTopOnError : true
					// Set this property to true if you have a long form

					});
					$("#tabs").tab();
					
					$("#alertEmpActivatedSuccess").hide();
					$("#alertEmpRejectedSuccess").hide();
					$.post(
									"http://localhost:8080/EmployeeWeb/Console",
									function(data) {
										var jObj = eval("(" + data + ")");
										group = jObj[1];
										userGroup = jObj[2];
										UserToApprove = jObj[3];
										console.log(userGroup);
										// Set the Cookie:
										// $.cookie("email",jObj[0].email);

										$("#proPic")
												.html(
														"<img src='"
																+ jObj[0].img
																+ "' class='img-circle' width='140'>");
										$("#name").html(jObj[0].fname + " "+ jObj[0].lname);
										$("#lname").html(jObj[0].lname);
										$("#add1").html(jObj[0].street);
										$("#add2").html(jObj[0].city + ", "	+ jObj[0].state + " "+ jObj[0].zip);
										$("#email").html(jObj[0].email);
										$("#role").html(jObj[0].role);
										$("#login-info").hide();

										// Do Manager stuff here:
										if (jObj[0].role === "MANAGER") {
											for (var i = 0; i < group.length; i++) {
												
												$("#curGroupM")
														.append("<div class='col-md-3'><div class='panel panel-default text-center' id='btnOpenGroupModal' data-id='"
																+ i	+ "'  onclick='groupModal(" + i + ", " + group[i].id + ")' data-target='groupModal'>"
																+ group[i].name + "</h4></div>");
												/*				
												$("#curGroupM").append(
														"<table class='table'><tr><td>" + group[i].name +
														"</td><td><span class='glyphicon glyphicon-remove-sign'></span></td></tr></table>"
												);
												*/
												// $("#curGroupM
												// table>tr").append("<tr><td>"
												// + group[i].name + "</td><td>"
												// + group[i].mgr + "</td><td>"
												// + group[i].completed
												// +"</td></tr>");
											}
										}
										if ((String(jObj[0].role) == "DEVELOPER")
												|| (jObj[0].role == "QA")) {

											$("#CreateGroupHeading").text(
													"Join Group");
											$("#GroupButton").text("Join");
											$("#doGroup").removeAttr("action");
											$("#GroupButton").attr(	"onclick","joinGroup()");
											if(userGroup != null){
												$("#curGroupM")
														.append("<table class='table'><tr><th>Name</th><th>Manager</th><th>Completed</th></tr>"+
																"<tr><td>"
																		+ userGroup.name
																		+ "</td><td>"
																		+ userGroup.mgr
																		+ "</td><td>"
																		+ userGroup.completed
																		+ "</td></tr></table>");
											}

										}
										if (jObj[0].admin == "true") {

											$("#approvalTab")
													.append(
															"<a href='#approval' data-toggle='tab'>Approval</a>");

											for (var i = 0; i < UserToApprove.length; i++) {
												// $("#usersToApprove").append("<div
												// class='col-md-3'><button
												// class='btn btn-default'
												// id='btnOpenModal' data-id='"
												// + i + "' onclick=func("+i+")
												// data-target='userModal'>" +
												// UserToApprove[i].fname + " "
												// + UserToApprove[i].lname +
												// "</button></div>");
												$("#usersToApprove")
														.append(
																"<div class='col-md-3'><div class='panel panel-default text-center' id='btnOpenModal' data-id='"
																		+ i
																		+ "' onclick=func("	+ i+ ","+ UserToApprove[i].id+ ") data-target='userModal'>"
																		+ "<div class='panel-heading'><strong>"	+ UserToApprove[i].role
																		+ "</strong></div><img src='"+ UserToApprove[i].img	+ "' class='img-circle' width=140>"
																		+ "<h4>"+ UserToApprove[i].fname + " "+ UserToApprove[i].lname	+ "</h4></div>");
											}

										}

									});

					function logout() {
						$.ajax({
							type : "POST",
							url : "EmployeeWeb/Logout"
						});
					}

					$("#logoutButton").click(function() {
						$.removeCookie("email");

						window.location = "login.html";
					});
				});
function groupModal(o,c){
	var members = 0;
	console.log("Groups: " +group);
	$.cookie("groupID", c);
	console.log("GroupID cookie: " + $.cookie("groupID"));
	
	$("#groupModalHeader").html("<h3>" + group[o].name + "</h3>");
	$("#groupModalBody").empty();
	if(c>0){
		$.post("GetGroupMembers",
				function(data){
					members = eval("(" + data + ")");
					console.log(members);
					
					for(var i = 0; i < members.length; i++){
						
						$("#groupModalBody").html(
								"<table class='table'><tr><td>" + members[i].fname + " " + members[i].lname + "</td><td>" + members[i].email + "</td><td>" +
										"<button type='button' class='btn btn-danger'>" +
										"<span class='glyphicon glyphicon-remove' onclick='removeMember(" + members[i].id + ", " + o + ")'></span>Remove</div></td></tr></table>"
						
						);
						
					}
		});
		
	}
	
	console.log(members);
	//$("#groupModalHeader").html(group[i].name);
	
	
	$("#groupModal").modal();
}
function addMember(){
	var mem = $("#memberToAdd").val();
	var gp = $("#groupModalHeader h3").val();
	
	$.ajax({
		type: 'POST',
		url: 'AddGroupMember',
		data: {
			email: mem,
			group: gp
		}, success : function(){
			$("#groupModal").modal('hide').on(
					"hidden.bs.modal",
					function(){
						location.reload();
					});
			
		}
	});
}
