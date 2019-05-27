import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken } from "./securityStuff.js";
var mapa = new Map();
var nameSelect = $("#racs_name_select");
var idSelect = $("#racs_id_select");
var carSelect = $("#car_select");
var carIdSelect = $("#car_id_select");

window.updateRACS = updateRACS;
window.addCarToRACS = addCarToRACS;
window.removeCarFromRACS = removeCarFromRACS;
window.updateCar = updateCar;
window.isNumber = isNumber;

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

if (!checkRoleFromToken(token, "ROLE_ADMIN")) history.go(-1);
$(document).ready(function(){
    
	$.ajax({
		url: "http://localhost:8080/racss/getAll",
		method: "GET",
		dataType: "json",
        crossDomain: true,
        headers: { "Authorization": "Bearer " + token}, 
		success: function (result) {
            if (result != null && result.lenght > 0) {
                for (var i = 0; i < result.length; i++) {
                    mapa[result[i].id] = result[i];
                    nameSelect.append("<option>"+result[i].name+"</option>");
                    idSelect.append("<option>" + result[i].id + "</option>");
                }
                setInputs();
            }

			
        },
        error: function(err) {
            console.log(err);
        }
    });	
    
    loadNavbar('RACSHomepageNavItem');
});

function updateRACS() {
    var ids = ["name", "longitude", "latitude", "description", "averageScore", "numberOfVotes"];
	if (!validateInputs(ids)) {
		alert("Inputs are invalid!");
		return;
	}
	
    var key = idSelect.val();

	mapa[key].name = $("#name").val();
    mapa[key].longitude = $("#longitude").val();
    mapa[key].latitude = $("#latitude").val();
    mapa[key].promoDescription = $("#description").val();
    mapa[key].averageScore = $("#averageScore").val();
    mapa[key].numberOfVotes = $("#numberOfVotes").val();
    var carsOfRacs = [];
    
    var currentCar = {};
    $("#car_select > option").each(function() {
        currentCar.manufacturer = this.text.split(" ")[1];
        currentCar.name = this.text.split(" ")[2];
        currentCar.color = this.text.split(" ")[3];
        currentCar.yearOfManufacture = parseInt(this.text.split(" ")[4]);
        currentCar.pricePerHour = parseFloat(this.text.split(" ")[5]);
        carsOfRacs.push(currentCar);
    });

    mapa[key].cars = carsOfRacs;
    
		
	$.ajax({
		url: "http://localhost:8080/racss/update",
		method: "PUT",
		contentType: "application/json",
		dataType: "text",	
        data: JSON.stringify(mapa[key]),
        headers: { "Authorization": "Bearer " + token}, 
		success: function(result) {
            
		}, error: function(err) {
            console.log(err);
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
	$("#longitude").val(mapa[key].longitude);
	$("#latitude").val(mapa[key].latitude);
    $("#description").val(mapa[key].promoDescription);
    $("#averageScore").val(mapa[key].averageScore);
    $("#numberOfVotes").val(mapa[key].numberOfVotes);

    carSelect.empty();
    carIdSelect.empty();

    var idx = 0;
    mapa[key].cars.sort(compareCars);
    for (var car of mapa[key].cars) {
        idx++;
        carIdSelect.append("<option>"+car.id+"</option>");
        carSelect.append("<option>"+ "#" + idx + " " + car.manufacturer + " " + car.name + " " + car.color + " " + car.yearOfManufacture + " " + car.pricePerHour +"</option>");
    }
}

/*  if the 2nd item in one selectbox is selected,
then the 2nd item is selected in all other
selectboxes as well
*/
$(function(){
    $('#racs_name_select').change(function(){ // when one changes
        var idx = $('#racs_name_select').prop('selectedIndex');
        document.getElementById('racs_id_select').selectedIndex = idx;
        
        setInputs();
    })
    

    $("#car_select").change(function() {
        var idx = $("#car_select").prop('selectedIndex');
        document.getElementById('car_id_select').selectedIndex = idx;
    
    })
    
})

function addCarToRACS() {
    var ids = ["carName", "carManufacturer", "carColor", "carYOM", "carPricePerHour"];
    if (!validateInputs(ids)) {
		alert("Inputs are invalid!");
		return;
    }

    var newCar = {};
    newCar.name = $("#carName").val();
    newCar.manufacturer = $("#carManufacturer").val();
    newCar.color = $("#carColor").val();
    newCar.yearOfManufacture = $("#carYOM").val();
    newCar.pricePerHour = $("#carPricePerHour").val();
    

    newCar.racs_id = idSelect.val();
    
    $.ajax({
		url: "http://localhost:8080/racss/addCar",
		method: "PUT",
		contentType: "application/json",
		dataType: "json",	
        data: JSON.stringify(newCar),
        headers: { "Authorization": "Bearer " + token}, 
		success: function(car) {
            
            $("#car_select").append("<option>"+"#"+car.id+" "+car.manufacturer+" "+car.name+" "+car.yearOfManufacture + " " + car.pricePerHour+"</option>");
		}, error: function(error) {
            console.log(error);
        }
	});
    
}

function removeCarFromRACS() {
    var carID = carIdSelect.val();
    $.ajax({
        url: "http://localhost:8080/cars/removeCar/" + carID,
        method: "DELETE",
        data: {},
        contentType: "application/json",
        dataType: "json",
        headers: { "Authorization": "Bearer " + token}, 
        success: function(allCars) {
            carIdSelect.empty();
            carSelect.empty();
            allCars.sort();
            var idx = 0;
            for (var car of allCars) {
                idx++;
                carIdSelect.append("<option>"+car.id+"</option>");
                carSelect.append("<option>"+ "#" + idx + " " + car.manufacturer + " " + car.name + " " + car.color + " " + car.yearOfManufacture + " " + car.pricePerHour +"</option>");
            }
        },
        error: function(err) {
            console.log(err);
        }
    });
}

function updateCar() {
    var carID = carIdSelect.val();
    var editedCar = {};
    editedCar.manufacturer = $("#editCarManufacturer").val();
    editedCar.color = $("#editCarColor").val();
    editedCar.yearOfManufacture = parseInt($("#editCarYearOfManufacture").val());
    editedCar.name = $("#editCarName").val();
    editedCar.pricePerHour = parseFloat($("#editCarPricePerHour").val());
    editedCar.id = parseInt(carID);
    
    $.ajax({
        url: "http://localhost:8080/cars/update",
        method: "PUT",
        data: JSON.stringify(editedCar),
        contentType: "application/json",
        dataType: "text",
        headers: { "Authorization": "Bearer " + token}, 
        success: function(result) {
            location.reload();
        },
        error: function(err) {
            console.log(err);
        }
    });
}

// used for sorting cars array
function compareCars(a, b) {
    if (a.id < b.id) return -1;
    else if (a.id > b.id) return 1;
    else return 0;
}

// allow only numbers to be entered where required
function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    // at most 4 digits
    evt.target.value = evt.target.value.slice(0, 3);
    return true;
}