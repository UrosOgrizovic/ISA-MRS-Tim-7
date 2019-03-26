var searchUrl = "http://localhost:8080/racss/searchCars";

function searchCars() {
    if (!validateInputs()) {
		alert("Inputs are invalid!");
		return;
	}
    var name = $("#name").val();
    var yearOfManufacture = $("#yearOfManufacture").val();
    var manufacturer = $("#manufacturer").val();

    $.ajax({
		url: searchUrl + "?name=" + name + "&manufacturer=" + manufacturer + "&yearOfManufacture=" + yearOfManufacture,
		method: "GET",
		contentType: "application/json",
		dataType: "json",	
		data: {},
		success: function(result) {
            console.log(result);
		}
	});
}

function validateInputs() {
	var ids = ["name", "yearOfManufacture", "manufacturer"];
	var flag = true;
	
	for (var i = 0; i < ids.length; i++)
		if ($("#"+ids[i]).val().trim() == "") {
			flag = false;
			break;
		}
	return flag;
}