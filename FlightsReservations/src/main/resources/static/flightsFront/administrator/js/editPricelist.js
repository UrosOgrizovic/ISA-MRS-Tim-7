var airline = "airserbia";

$(document).ready(function(){
	// get pricelist for airline
	// populate fields
	
	$.ajax({
		url: "http://localhost:8080/airlines/pricelist/" + airline,
		method: "GET",
		dataType: "json",	
		success: function(result) {
			if (result != null)
				refreshInputs(result);
			else
				alert("Airline doesn't exists!")
		},
		statusCode: {
			400: function() { alert("400 Bad request.")},
			500: function() { alert("500 Server error.")}
		}
	})
});

function refreshInputs(pricelist) {
	$("#first").val(pricelist.first);
	$("#business").val(pricelist.business);
	$("#economic").val(pricelist.economic);
}

function updatePricelist() {
	// create dto
	// if valid
	//    ajax request
	//    refresh
	// else
	//    alert
	var pricelist = new Object();
	pricelist.first = $("#first").val();
	pricelist.business = $("#business").val();
	pricelist.economic = $("#economic").val();
	pricelist.airline = airline;
	
	if (validate(pricelist)) {
		$.ajax({
			url: "http://localhost:8080/airlines/pricelist",
			method: "POST",
			contentType: "application/json",
			dataType: "json",	
			data: JSON.stringify(pricelist),
			success: function(result) {
				alert("Successfull update.");
				refreshInputs(result);
			},
			statusCode: {
				400: function() { alert("400 Bad request.")},
				500: function() { alert("500 Server error.")}
			}
		});	
	} else {
		alert("Invalid inputs!");
	}
}

function validate(pricelist) {
	for (var property in pricelist) {
		if (pricelist.hasOwnProperty(property)) {
			if (pricelist[property] === "")
				return false;
		}
	}
	return true;
}

