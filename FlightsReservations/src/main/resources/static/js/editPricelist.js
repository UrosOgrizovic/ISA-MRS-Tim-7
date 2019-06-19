var airline = null;

$(document).ready(function(){
	// get pricelist for airline
	// populate fields
	checkAuth();
	airline = JSON.parse(localStorage.getItem("admin")).airlineName;
	
	$.ajax({
		url: "/airlines/pricelist/" + airline,
		method: "GET",
		dataType: "json",	
		success: function(result) {
			refreshInputs(result);
		},
		error: function(result) {
			toastr.error("Something is wrong with your request.(get pricelist)");
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
			url: "/airlines/pricelist",
			method: "POST",
			contentType: "application/json",
			data: JSON.stringify(pricelist),
			success: function(result) {
				toastr.success("Successfull update.");
			},
			error: function(result) {
				toastr.error("Something is wrong with your request.(update pricelist)");
			}
		});	
	} else {
		toastr.info("Enter all input fields.")
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

