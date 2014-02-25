
$(document).ready(function(){
	$.validate({
		validateOnBlur : false, // disable validation when input looses focus
    	errorMessagePosition : 'top', // Instead of 'element' which is default
   		scrollToTopOnError : true // Set this property to true if you have a long form
	});
	
	$("#clientList > button").click(function(){
		 var id = $(this).attr("id");
		 
		 if(id == "cSears"){
			 $("#clientFrame").attr("src","http://www.sears.com");
		 }
		 else if(id == "cGrainger"){
			 $("#clientFrame").attr("src","http://www.grainger.com");
		 }
		 else if(id == "cBB"){
			 $("#clientFrame").attr("src","http://www.bestbuy.com");
		 }
	  });
	
	$("#tabs").tab();
	$("#slides").slidesjs({
        width: 940,
        height: 528,
		navigation: false
      });
	  
	  $("#loginButton").click(function(){
		  window.location = "/EmployeeAngular/login.html";
	  });
	  
});
function showClient(){
	var id = $(this).attr("id");

	 if(id == "cSears"){
		 $("#clientFrame").attr("src","http://www.sears.com");
	 }
	 else if(id == "cGrainger"){
		 $("#clientFrame").attr("src","http://www.grainger.com");
	 }
	 else if(id == "cBB"){
		 $("#clientFrame").attr("src","http://www.bestbuy.com");
	 }
}