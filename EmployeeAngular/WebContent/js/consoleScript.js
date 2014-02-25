//Globals:
var UserToApprove;
/*
if ($.cookie("email") == undefined) {
	window.location = "/EmployeeAngular/login.html";

}
*/
/*
 * 
 * Deprecated with factory
 * 
 */
function joinGroup(g) {
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

function func(index) {
	console.log("index: " + index);
	$("#userModal" + index).modal();
	/*
	$("#userModalBody").html(
			"<img src='" + UserToApprove[i].img
					+ "' class='img-rounded' width=280><br>"
					+ UserToApprove[i].fname + " " + UserToApprove[i].lname
					+ " <br>" + UserToApprove[i].street + "<br>"
					+ UserToApprove[i].city + ", " + UserToApprove[i].state
					+ " " + UserToApprove[i].zip + "<br>"
					+ UserToApprove[i].email + "<br>" + UserToApprove[i].role);
	
	$("#approveUserBtn").attr("onclick", "approveUser(" + u + ")");
	$("#rejectUserBtn").attr("onclick", "rejectUser(" + u + ")");
	*/
}
$(document).ready(
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
		
		$("#alertEmpActivatedSuccess").hide();
		$("#alertEmpRejectedSuccess").hide();
		$("#regGroups").hide();
		$("#approval").hide();
		$.post("http://localhost:8080/EmployeeAngular/Console",
			function(data) {
				var jObj = eval("(" + data + ")");
				group = jObj[1];
				userGroup = jObj[2];
				UserToApprove = jObj[3];
				console.log(userGroup);
				// Set the Cookie:
				// $.cookie("email",jObj[0].email);

				$("#login-info").hide();
		
				
				
			
				if ((String(jObj[0].role) == "DEVELOPER")
						|| (jObj[0].role == "QA")) {

					$("#CreateGroupHeading").text(
							"Join Group");
					$("#GroupButton").text("Join");
					$("#doGroup").removeAttr("action");
					$("#doGroup").attr(	"onclick",	joinGroup($("#groupCname").val()));
					
					
				}
				if (jObj[0].admin == "true") {

					$("#approvalTab").show();
					/*
					$("#approvalTab")
							.append(
									"<a href='#approval' data-toggle='tab'>Approval</a>");
									
									*/

					

				}

			});

		function logout() {
			$.ajax({
				type : "POST",
				url : "EmployeeAngular/Logout"
			});
		}

		$("#logoutButton").click(function() {
			$.removeCookie("email");

			window.location = "login.html";
		});
	});
