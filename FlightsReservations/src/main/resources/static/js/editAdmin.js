var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

$(document).ready(function(){
	$.ajax({
		url: "/users/myDetails",
		method: "GET",
		dataType: "json",
        crossDomain: true,
        headers: { "Authorization": "Bearer " + token}, 
		success: function (result) {
			$("#email").val(result.email);
			$("#firstName").val(result.firstName);
			$("#lastName").val(result.lastName);
			$("#phone").val(result.phone);
			$("#address").val(result.address);
		},
		error: function(result) {
			toastr.error("Something is wrong with your request.(get details)");
		}
    });	
    $("#changePasswordBtn").click(editPassword);
    $("#updateUserBtn").click(updateUser);
});



function editPassword() {
	var dto = {
		oldPassword : $("#old").val().trim(),
		newPassword : $("#new").val().trim()
	};
	
	if (!validateInputs(Object.values(dto))) {
		toastr.info("Please enter all inputs.");
		return;
	}
	
	$.ajax({
		url: "/auth/change-password",
		method: "POST",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(dto),
		success: function(result) {
			toastr.success("Password changed successfully.");
			$("#changePasswordModal").modal("toggle");
		},
		error: function(result) {
			toastr.error("Something is wrong with your request.(change password)");
		}
	});
}




function updateUser() {
	var dto = {
		firstName : $("#firstName").val().trim(),
		lastName  : $("#lastName").val().trim(),
 		phone     : $("#phone").val().trim(),
		address   : $("#address").val().trim(),
		email     : $("#email").val().trim()
	};
	
	if (!validateInputs(Object.values(dto))) {
		toastr.info("Please enter all inputs.");
		return;
	}
	
	$.ajax({
		url: "/users/update",
		method: "PUT",
		contentType: "application/json",
        data: JSON.stringify(dto),
        headers: { "Authorization": "Bearer " + token}, 
		success: function(result) {
			toastr.success("Profile details are updated.");
		}, 
		error: function(result) {
			toastr.error("Something is wrong with your request.(update profile)");
		}
	});
}

function validateInputs(inputs) {
	for (var input of inputs)
		if (!input)
			return false;
	return true;
}

