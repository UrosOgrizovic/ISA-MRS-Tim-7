import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken, parseJwt, isTokenExpired } from "./securityStuff.js";

var mapa = new Map();
var nameSelect = $("#racs_name_select");
var idSelect = $("#racs_id_select");
var carSelect = $("#car_select");
var carIdSelect = $("#car_id_select");

window.reserveCar = reserveCar;
var token = localStorage.getItem("token");

if (token == null || isTokenExpired(token)) location.replace("/html/login.html");

var email = parseJwt(token).sub;

if (!checkRoleFromToken(token, "ROLE_USER")) history.go(-1);

window.setInputs = setInputs;
window.reserveCar = reserveCar;
window.compareCars = compareCars;
window.checkDates = checkDates;

$(document).ready(function(){
    
    // Initialize a new plugin instance for all
    // e.g. $('input[type="range"]') elements.
    $('input[type="range"]').rangeslider({
        polyfill : false,
        onInit : function() {
            this.output = $( '<div class="range-output" />' ).insertAfter( this.$range ).html( this.$element.val() + ":00" );
        },
        onSlide : function(position, value ) {
            this.output.html( value );
        }
    });

    $("#startDate").datepicker({
        uiLibrary: 'bootstrap'
    });
    $("#endDate").datepicker({
        uiLibrary: 'bootstrap'
    });

    $.ajax({
		url: "http://localhost:8080/racss/getAll",
		method: "GET",
		dataType: "json",
        crossDomain: true,
        headers: { "Authorization": "Bearer " + token}, 
		success: function (result) {
            console.log(result);
            if (result != null && result.length != 0 && result != undefined) {
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

function setInputs(){
    var key = idSelect.val();

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

// used for sorting cars array
function compareCars(a, b) {
    if (a.id < b.id) return -1;
    else if (a.id > b.id) return 1;
    else return 0;
}

function reserveCar() {
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    if (startTime == "0") {
        startTime = "00";
    }
    if (endTime == "0") {
        endTime = "00";
    }
    
    var carReservation = {};
    
    carReservation.ownerEmail = email;
    carReservation.carId = carIdSelect.val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    if (!checkDates(startDate + " " + startTime + ":00", endDate + " " + endTime + ":00")) {
        return false;
    }

    var arr = startDate.split("/");
    startDate = arr[1] + "-" + arr[0] + "-" + arr[2];
    arr = endDate.split("/");
    endDate = arr[1] + "-" + arr[0] + "-" + arr[2];

    carReservation.startTime = startDate + " " + startTime + ":00";
    carReservation.endTime = endDate + " " + endTime + ":00";
    carReservation.discount = 0;
    
    $("#error").remove();


    $.ajax({
		url: "http://localhost:8080/carReservations",
		method: "POST",
		dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(carReservation),
        headers: { "Authorization": "Bearer " + token}, 
		success: function (result) {
            
            location.replace("/html/userProfilePage.html");
        },
        error: function(err) {
            console.log(err);
            $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
        }
    });	

}

function checkDates(startDate, endDate) {
    if (startDate.trim() == "" || endDate.trim() == "") {
        alert("Start time and end time must be chosen!")
        return false;
    }
        
    var currentTime = new Date();
    
    var dateStartDate = new Date(startDate);
    var dateEndDate = new Date(endDate);
    if (dateStartDate < currentTime) {
        alert("Start time must be after current time!");
        return false;
    } else if (dateEndDate < currentTime) {
        alert("End time must be after current time!");
        return false;
    }
    var diff = dateEndDate - dateStartDate;
    var oneDayInMillis = 3600000 * 24;
    if (diff < 0) {
        alert("Start time must be before end time!");
        return false;
    } else if (diff > 7 * oneDayInMillis) {
        alert("End time must be no more than 7 days after start time!");
        return false;
    }

    return true;
}
