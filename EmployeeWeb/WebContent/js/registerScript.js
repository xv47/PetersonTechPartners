function enableUpload() {

	if ($("#email").val().length > 0) {

		$("#imgUpload").prop("disabled", false);
	} else {

		$("#imgUpload").prop("disabled", true);
	}
}
$("#email").on("keyup", function() {
	$("#imgUpload").prop("disabled", false);
});
$(document).ready(function() {
	$.validate({
		validateOnBlur : false, // disable validation when input looses focus
		errorMessagePosition : 'top', // Instead of 'element' which is default
		scrollToTopOnError : true
	// Set this property to true if you have a long form
	});

});
function submit() {
	var email = $("#email").val();
	var pass1 = $("#pass1").val();
	var pass2 = $("#pass2").val();
	var fname = $("#fname").val();
	var lname = $("#lname").val();
	var street = $("#street").val();
	var city = $("#city").val();
	var state = $("#state").val();
	var zip = $("#zip").val();
	var role = $("#role").val();

	$.ajax({
		type : "POST",
		url : "/Register",
		data : "email=" + email + "&pass1=" + pass1 + "&pass2=" + pass2
				+ "&fname=" + fname + "&lname=" + lname + "&street=" + street
				+ "&city=" + city + "&state=" + state + "&zip=" + zip
				+ "&role=" + role
	});
}
function onFileUpload() {

	var em = $("#email").val();
	//var filename = $(this).val();
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

}
/*
 * $(function () { 'use strict'; // Change this to the location of your
 * server-side upload handler: var url = window.location.hostname ===
 * 'ImgUploadServlet'; $('#fileupload').fileupload({ url: url, dataType: 'json',
 * done: function (e, data) { $.each(data.result.files, function (index, file) {
 * $('<p/>').text(file.name).appendTo('#files'); }); }, progressall: function
 * (e, data) { var progress = parseInt(data.loaded / data.total * 100, 10);
 * $('#progress .progress-bar').css( 'width', progress + '%' ); }
 * }).prop('disabled', !$.support.fileInput)
 * .parent().addClass($.support.fileInput ? undefined : 'disabled'); }); }
 */