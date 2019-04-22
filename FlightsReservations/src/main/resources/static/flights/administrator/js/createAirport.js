$(document).ready(function() {
	$("#createButton").click(createAirport);
});

function createAirport() {
	// validate inputs
	// if valid
	// 	  make ajax call for creating airport
	// 	  if ajax call is successfull 
	// 		  close modal
	//    else 
	//    	  alert with error
	// else
	//    alert with error
	
	var airport = new Object();
	airport.name = $("#name").val();
	airport.city = $("#city").val();
	airport.state = $("#state").val();
	airport.lat = $("#lat").val();
	airport.lng = $("#lng").val();
	
	if (validate(airport)) {
		airport = makeAjaxCall(airport);
		if (airport != null) {
			$("#closeCreateModal").click();
		}
	} else {
		alert("Inputs are invalid!");
	}
}



function validate(airport) {
	for (var property in airport) {
		if (airport.hasOwnProperty(property)) {
			if (airport[property] === "")
				return false;
		}
	}
	return true;
}

function makeAjaxCall(airport) {
	var rtn = null;
	$.ajax({
		url: "http://localhost:8080/airports",
		method: "POST",
		contentType: "application/json",
		dataType: "json",	
		data: JSON.stringify(airport),
		async: false,
		success: function(result) {
			rtn = result;
		},
		statusCode: {
			400: function() { alert("400 Bad request.")},
			500: function() { alert("500 Server error.")}
		}
	});	
	return rtn;
}

