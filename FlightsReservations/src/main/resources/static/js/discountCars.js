import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken, parseJwt, isTokenExpired } from "./securityStuff.js";
var getAllDiscountCarsForPeriod = "/cars/getAllDiscountCarsForPeriod";
var carReservationLink = "/carReservations";

var rps = localStorage.getItem("reservationPeriodStart");
var reservationPeriodStart = dateFormatter(rps);
var stringReservationPeriodStart = dateToString(rps);


var rpe = localStorage.getItem("reservationPeriodEnd");
var reservationPeriodEnd = dateFormatter(rpe);
var stringReservationPeriodEnd = dateToString(rpe);

// used for converting String to Date of desired format (dd-MM-yyyy HH:mm)
function dateFormatter(obj) {
    var objDate = obj.split(" ")[0].split("-")[0];
    if (objDate.length == 1) objDate = "0" + objDate;
    var objMonth = obj.split(" ")[0].split("-")[1];
    if (objMonth.length == 1) objMonth = "0" + objMonth;
    var objYear = obj.split(" ")[0].split("-")[2];
    var objHours = obj.split(" ")[1].split(":")[0];
    var objMinutes = obj.split(" ")[1].split(":")[1];
    return new Date(objYear, objMonth, objDate, objHours, objMinutes);
}

// used for converting Date to string (dd-MM-yyyy HH:mm)
function dateToString(obj) {
    var objDate = obj.split(" ")[0].split("-")[0];
    if (objDate.length == 1) objDate = "0" + objDate;
    var objMonth = obj.split(" ")[0].split("-")[1];
    if (objMonth.length == 1) objMonth = "0" + objMonth;
    var objYear = obj.split(" ")[0].split("-")[2];
    var objHours = obj.split(" ")[1].split(":")[0];
    var objMinutes = obj.split(" ")[1].split(":")[1];

    return objDate + "-" + objMonth + "-" + objYear + " " + objHours + ":" + objMinutes;
}

var token = localStorage.getItem("token");

if (token == null || isTokenExpired(token)) location.replace("/html/login.html");


if (!checkRoleFromToken(token, "ROLE_USER")) history.go(-1);
window.fastBook = fastBook;

$(document).ready(function() {
    $("#discountCars").remove();
    $("#error").remove();
    
    $.ajax({
        url: getAllDiscountCarsForPeriod + "/" + stringReservationPeriodStart + "/" + stringReservationPeriodEnd,
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        data: {},
        success: function(cars) {
            if (cars != null && cars.length > 0) {
                displayDiscountCars(cars);
            } else {
                toastr.info("No cars to display");
            }
            
        }, error: function(error) {
            toastr.error("Could not get cars for period");
            console.log(error);
        }
    });

    loadNavbar('RACSHomepageNavItem');
});

function displayDiscountCars(cars) {
    var text = "<table id=\"discountCars\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
    text += "<thead>";
    text += "<tr>";
    text += "<th>Manufacturer</th>";
    text += "<th>Name</th>";
    text += "<th>Year of manufacture</th>";
    text += "<th>Color</th>";
    text += "<th>Total reservation price</th>";
    text += "<th>Start time</th>";
    text += "<th>End time</th>";
    text += "<th>Fast book</th>";
    
    text += "</tr>";
    text += "</thead><tbody>";

    for (var car of cars) {
        text += "<tr>";
        text += "<td>"+car.manufacturer+"</td>";
        text += "<td>"+car.name+"</td>";
        text += "<td>"+car.yearOfManufacture+"</td>";
        text += "<td>"+car.color+"</td>";
        text += "<td>"+car.totalPrice+"</td>";
        var start = car.startTime.split("+")[0];
       
        var end = car.endTime.split("+")[0];
        var startTimeString = discountDateToString(start);
        var endTimeString = discountDateToString(end);
        text += "<td>"+startTimeString+"</td>";
        text += "<td>"+endTimeString+"</td>";
        var obj = {};
        obj.carId = car.id;
        obj.startTime = startTimeString;
        obj.endTime = endTimeString;
        localStorage.setItem("fastBookCarStartTime", startTimeString);
        localStorage.setItem("fastBookCarEndTime", endTimeString);
        text += "<td><button class=\"btn btn-primary\" onclick=\"fastBook("+car.id+")\" >Fast book this car</button></td>";
        
        text += "</tr>";
    }
    text += "</tbody></table>";
    $(document.documentElement).append(text);
}



// used to convert date to string in displayDiscountCars
function discountDateToString(obj) {
    var time = obj.split("T")[1].substring(0, 5);
    var date = obj.split("T")[0];
    return date.substring(8)+"-"+date.substring(5,8)+date.substring(0,4)+" "+time;
}

function fastBook(carId) {
    var carReservationRequestDTO = {};
    carReservationRequestDTO.ownerEmail = localStorage.getItem("email");
    carReservationRequestDTO.carId = carId;
    
    carReservationRequestDTO.startTime = localStorage.getItem("fastBookCarStartTime");
    carReservationRequestDTO.endTime = localStorage.getItem("fastBookCarEndTime");
    
    
    
    $.ajax({
        url: carReservationLink,
        method: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(carReservationRequestDTO),
        headers: { "Authorization": "Bearer " + token },
        success: function(carReservationDTO) {
            localStorage.setItem("successMessageForToastr", "Reservation successful");
            location.replace("/html/userProfilePage.html");
        }, error: function(error) {
            toastr.error("Could not fast book car");
            console.log(error);
        }
    });
    
    
    
}