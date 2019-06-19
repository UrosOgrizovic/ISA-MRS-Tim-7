$(document).ready(function() {
	checkAuth();
	airline = JSON.parse(localStorage.getItem("admin")).airlineName;
	
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
	airport.name = $("#name").val().trim();
	airport.city = $("#city").val().trim();
	airport.state = $("#state").val().trim();
	airport.latitude = $("#lat").val().trim();
	airport.longitude = $("#lng").val().trim();
	
	if (validate(airport)) {
		airport = makeAjaxCall(airport);
		if (airport != null) {
			$("#closeCreateModal").click();
		}
	} else {
		toastr.info("Inputs are invalid.");
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
		url: "/airports",
		method: "POST",
		contentType: "application/json",
		dataType: "json",	
		data: JSON.stringify(airport),
		async: false,
		success: function(result) {
			rtn = result;
			toastr.success("Airport created and added to system.");
			toastr.info("You now need to add it to your airline.");
		},
		error: function(result) {
			toastr.error("Something is wrong with your request.(update details)");
		}
	});	
	return rtn;
}

