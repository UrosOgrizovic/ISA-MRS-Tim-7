var airline = "airserbia";

$(document).ready(function(){
	$.ajax({
		url: "http://localhost:8080/airlines/" + airline,
		method: "GET",
		dataType: "json",
		success: function (result) {
			setInputs(result);
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

function setInputs(airline){
	var key = idSelect.val();
	$("#name").val(airline.name);
	$("#location").val(mapa[key].location);
	$("#promoDesc").val(mapa[key].promoDescription);
}
