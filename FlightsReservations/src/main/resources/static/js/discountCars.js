var getAllDiscountCarsForPeriod = "/cars/getAllDiscountCarsForPeriod";

var rps = localStorage.getItem("reservationPeriodStart");
var rpsDate = rps.split(" ")[0].split("-")[0];
if (rpsDate.length == 1) rpsDate = "0" + rpsDate;
var rpsMonth = rps.split(" ")[0].split("-")[1];
if (rpsMonth.length == 1) rpsMonth = "0" + rpsMonth;
var rpsYear = rps.split(" ")[0].split("-")[2];
var rpsHours = rps.split(" ")[1].split(":")[0];
var rpsMinutes = rps.split(" ")[1].split(":")[1];

var reservationPeriodStart = new Date(rpsYear, rpsMonth, rpsDate, rpsHours, rpsMinutes);
var stringReservationPeriodStart = rpsDate + "-" + rpsMonth + "-" + rpsYear + " " + rpsHours + ":" + rpsMinutes;

var rpe = localStorage.getItem("reservationPeriodEnd");
var rpeDate = rpe.split(" ")[0].split("-")[0];
if (rpeDate.length == 1) rpeDate = "0" + rpeDate;
var rpeMonth = rpe.split(" ")[0].split("-")[1];
if (rpeMonth.length == 1) rpeMonth = "0" + rpeMonth;
var rpeYear = rpe.split(" ")[0].split("-")[2];
var rpeHours = rpe.split(" ")[1].split(":")[0];
var rpeMinutes = rpe.split(" ")[1].split(":")[1];

var reservationPeriodEnd = new Date(rpeYear, rpeMonth, rpeDate, rpeHours, rpeMinutes);
var stringReservationPeriodEnd = rpeDate + "-" + rpeMonth + "-" + rpeYear + " " + rpeHours + ":" + rpeMinutes;

$(document).ready(function() {
    $("#discountCars").remove();
    
    $.ajax({
        url: getAllDiscountCarsForPeriod + "/" + stringReservationPeriodStart + "/" + stringReservationPeriodEnd,
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        data: {},
        success: function(cars) {
            displayDiscountCars(cars);
        }, error: function(error) {
            $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
            console.log(error);
        }
    });
});

function displayDiscountCars(cars) {
    var text = "<table id=\"discountCars\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
    text += "<thead>";
    text += "<tr>";
    text += "<th>Manufacturer</th>";
    text += "<th>Name</th>";
    text += "<th>Year of manufacture</th>";
    text += "<th>Color</th>";
    text += "<th>Price per hour</th>";
    text += "<th>Discount value</th>";
    text += "<th>Price per hour with discount</th>";
    text += "<th>Fast book</th>";
    
    text += "</tr>";
    text += "</thead><tbody>";

    for (var car of cars) {
        text += "<tr>";
        text += "<td>"+car.manufacturer+"</td>";
        text += "<td>"+car.name+"</td>";
        text += "<td>"+car.yearOfManufacture+"</td>";
        text += "<td>"+car.color+"</td>";
        var pph = car.pricePerHour;
        text += "<td>"+pph+"</td>";
        for (var discount of car.discounts) {
            if (! (reservationPeriodStart >= discount.endTime && reservationPeriodEnd <= discount.startTime) ) {
                var dv = discount.discountValue;
                text += "<td>"+dv+"</td>";
                pph = pph - (pph * dv / 100);
                text += "<td>"+pph+"</td>";        
            }
        }
        text += "<td><button class=\"btn btn-primary\" onclick=\"fastBook("+car.id+")\" >Fast book this car</button></td>";
        
        text += "</tr>";
    }
    text += "</tbody></table>";
    $(document.documentElement).append(text);
}

function fastBook(carId) {
    //TODO: implement fast book - what period should the car be booked for?
}