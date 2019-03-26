var searchUrl = "http://localhost:8080/racss/searchCars";

function searchCars() {
    if (!validateInputs()) {
		alert("Inputs are invalid!");
		return;
	}
    var car = {};
    car.name = $("#name").val();
    car.yearOfManufacture = $("#yearOfManufacture").val();
    car.manufacturer = $("#manufacturer").val();

    console.log(car);

    $.ajax({
		url: searchUrl,
		method: "GET",
		contentType: "application/json",
		dataType: "json",	
		data: JSON.stringify(car),
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