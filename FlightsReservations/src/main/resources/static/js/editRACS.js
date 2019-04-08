var mapa = new Map();
var nameSelect = $("#name_select");
var idSelect = $("#id_select");
var carSelect = $("#car_select");
var carIdSelect = $("#car_id_select");


$(document).ready(function(){
	$.ajax({
		url: "http://localhost:8080/racss/getAll",
		method: "GET",
		dataType: "json",
		crossDomain: true,
		success: function (result) {
			for (var i = 0; i < result.length; i++) {
                mapa[result[i].id] = result[i];
                nameSelect.append("<option>"+result[i].name+"</option>");
                idSelect.append("<option>" + result[i].id + "</option>");
			}
			setInputs();
		}
	});	
});

function updateRACS() {
    var ids = ["name", "address", "description"];
	if (!validateInputs(ids)) {
		alert("Inputs are invalid!");
		return;
	}
	
    var key = idSelect.val();

	mapa[key].name = $("#name").val();
	mapa[key].address = $("#address").val();
	mapa[key].description = $("#description").val();
		
	$.ajax({
		url: "http://localhost:8080/racss/update",
		method: "PUT",
		contentType: "application/json",
		dataType: "json",	
		data: JSON.stringify(mapa[key]),
		success: function(result) {
		}
	});
}

function validateInputs(ids) {
	
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
	$("#address").val(mapa[key].address);
    $("#description").val(mapa[key].description);
    
    carSelect.empty();
    carIdSelect.empty();

    for (var car of mapa[key].cars) {
        carIdSelect.append("<option>"+car.id+"</option>");
        carSelect.append("<option>"+ "#" + car.id + " " + car.manufacturer + " " + car.name + " " + car.color + " " + car.yearOfManufacture +"</option>");
    }
}

/*  if the 2nd item in one selectbox is selected,
then the 2nd item is selected in all other
selectboxes as well
*/
$(function(){
    $('#name_select').change(function(){ // when one changes
        var idx = $('#name_select').prop('selectedIndex');
        document.getElementById('id_select').selectedIndex = idx;
        document.getElementById('car_select').selectedIndex = idx;
        document.getElementById('car_id_select').selectedIndex = idx;
        
        setInputs();
    })
    $('#id_select').change(function(){ // when one changes
        var idx = $('#id_select').prop('selectedIndex');
        document.getElementById('name_select').selectedIndex = idx;
        document.getElementById('car_select').selectedIndex = idx;
        document.getElementById('car_id_select').selectedIndex = idx;
        
        setInputs();
    })

    $("#car_select").change(function() {
        var idx = $("#car_select").prop('selectedIndex');
        document.getElementById('car_id_select').selectedIndex = idx;
        document.getElementById('id_select').selectedIndex = idx;
        document.getElementById('name_select').selectedIndex = idx;

        setInputs();
    })
    $("#car_id_select").change(function() {
        var idx = $("#car_id_select").prop('selectedIndex');
        document.getElementById('car_select').selectedIndex = idx;
        document.getElementById('id_select').selectedIndex = idx;
        document.getElementById('name_select').selectedIndex = idx;

        setInputs();
    })
})

function addCarToRACS() {
    var ids = ["carName", "carManufacturer", "carColor", "carYOM"];
    if (!validateInputs(ids)) {
		alert("Inputs are invalid!");
		return;
    }

    var newCar = {};
    newCar.name = $("#carName").val();
    newCar.manufacturer = $("#carManufacturer").val();
    newCar.color = $("#carColor").val();
    newCar.yearOfManufacture = $("#carYOM").val();
    var racs = {id: 1};
    newCar.racs = racs;
    newCar.racs.id = idSelect.val();
    console.log(newCar);
    $.ajax({
		url: "http://localhost:8080/racss/addCar",
		method: "PUT",
		contentType: "application/json",
		dataType: "json",	
		data: JSON.stringify(newCar),
		success: function(car) {
            //TODO: ovde sta treba
            console.log("AAA " + car);
            $("#car_select").append("<option>"+"#"+car.id+" "+car.manufacturer+" "+car.name+" "+car.yearOfManufacture+"</option>");
		}, error: function(error) {
            console.log(error);
        }
	});
    
}