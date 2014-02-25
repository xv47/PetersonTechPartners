$(document).ready(function(){
	/*
	if($.cookie("email") != null){
		window.location = "/EmployeeAngular/console.html";
	}
	*/
	$.validate({
		validateOnBlur : false, // disable validation when input looses focus
    	errorMessagePosition : 'top', // Instead of 'element' which is default
   		scrollToTopOnError : true // Set this property to true if you have a long form
	});
	/*
	 * Deprecated:
	 * 
	$("#submit").click(function(){
		window.location = "/EmployeeAngular/Login";
	});
	*/
});
