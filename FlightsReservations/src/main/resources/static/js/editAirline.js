var mapa;
var idSelect;

var token = localStorage.getItem("token");

$(document).ready(function(){
    if (!localStorage.getItem("loggedIn")) {
        location.replace("/html/login.html");
    }
	mapa = new Map();
	idSelect = $("#airlineId");
	idSelect.change(setInputs);
	
	$.ajax({
		url: "http://localhost:8080/airlines/getAll",
		method: "GET",
        dataType: "json",
        headers: { "Authorization": "Bearer " + token}, 
		success: function (result) {
			for (var i = 0; i < result.length; i++) {
				mapa[result[i].id] = result[i];
				idSelect.append("<option>"+result[i].id+"</option>");
			}
			setInputs();
		}
	});	
});

function updateAirline() {
	if (!validateInputs()) {
		alert("Inputs are invalid!");
		return;
	}
	
	var key = idSelect.val();
	mapa[key].name = $("#name").val();
	mapa[key].location = $("#location").val();
	mapa[key].promoDescription = $("#promoDesc").val();
			
	$.ajax({
		url: "http://localhost:8080/airlines/update",
		method: "PUT",
		contentType: "application/json",
		dataType: "json",	
        data: JSON.stringify(mapa[key]),
        headers: { "Authorization": "Bearer " + token}, 
		success: function(result) {
			alert("Successfull update!");
		}
	});
}

function validateInputs() {
	var ids = ["name", "location", "promoDesc"];
	var flag = true;
	
	for (var i = 0; i < ids.length; i++)
		if ($("#"+ids[i]).val().trim() == "") {
			flag = false;
			break;
		}
	return flag;
}

function setInputs(){
	var key = idSelect.val();
	$("#name").val(mapa[key].name);
	$("#location").val(mapa[key].location);
	$("#promoDesc").val(mapa[key].promoDescription);
}
