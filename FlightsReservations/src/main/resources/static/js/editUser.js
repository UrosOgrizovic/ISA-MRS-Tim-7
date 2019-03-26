var mapa;
var emailSelect;

$(document).ready(function(){
	mapa = new Map();
	emailSelect = $("#emailSelect");
	emailSelect.change(setInputs);
	
	$.ajax({
		url: "http://localhost:8080/users/getAll",
		method: "GET",
		dataType: "json",
		crossDomain: true,
		success: function (result) {
			for (var i = 0; i < result.length; i++) {
				mapa[result[i].email] = result[i];
				emailSelect.append("<option>"+result[i].email+"</option>");
			}
			setInputs();
		}
	});	
});

function updateUser() {
	if (!validateInputs()) {
		alert("Inputs are invalid!");
		return;
	}
	
	var key = emailSelect.val();
	mapa[key].firstName = $("#firstName").val();
	mapa[key].lastName = $("#lastName").val();
	mapa[key].phone = $("#phone").val();
	mapa[key].address = $("#address").val();
		
	$.ajax({
		url: "http://localhost:8080/users/update",
		method: "PUT",
		contentType: "application/json",
		dataType: "json",	
		data: JSON.stringify(mapa[key]),
		success: function(result) {
			alert("Update successfull!");
		}
	});
}

function validateInputs() {
	var ids = ["firstName", "lastName", "phone", "address"];
	var flag = true;
	
	for (var i = 0; i < ids.length; i++)
		if ($("#"+ids[i]).val().trim() == "") {
			flag = false;
			break;
		}
	return flag;
}

function setInputs(){
	var key = emailSelect.val();
	$("#firstName").val(mapa[key].firstName);
	$("#lastName").val(mapa[key].lastName);
	$("#phone").val(mapa[key].phone);
	$("#address").val(mapa[key].address);
}
