import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken, parseJwt, isTokenExpired } from "./securityStuff.js";

var getAllFriendsLink = "http://localhost:8080/users/getFriends";
var getAllCarReservationsLink = "http://localhost:8080/users/getCarReservations";
var getAllFlightReservationsLink = "http://localhost:8080/users/getFlightReservations";
var getAllRoomReservationsLink = "http://localhost:8080/users/getRoomReservations";
var cancelCarReservationLink = "http://localhost:8080/carReservations/cancel/";
var cancelFlightReservationLink = "http://localhost:8080/flightReservations/cancel/";
var cancelRoomReservationLink = "http://localhost:8080/roomReservations/cancel/";
var rateLink = "/companies/rate";

var email = localStorage.getItem("email");

var token = localStorage.getItem("token");
if (token == null || isTokenExpired(token)) location.replace("/html/login.html");

if (!checkRoleFromToken(token, "ROLE_USER")) history.go(-1);

window.cancelCarReservation = cancelCarReservation;
window.cancelFlightReservation = cancelFlightReservation;
window.cancelRoomReservation = cancelRoomReservation;
window.rateRACS = rateRACS;

var msg = localStorage.getItem("successMessageForToastr");

$(document).ready(function(){
    if (msg != "" && msg != null) {
        toastr.success(msg);
    }
    localStorage.setItem("successMessageForToastr", "");
    $("#viewAllFriends").on('click', function(e) {
        e.preventDefault();
        getAllFriends();
    });

    $("#viewAllCarReservations").on('click', function(e) {
        e.preventDefault();
        getAllCarReservations();
    });

    $("#viewAllFlightReservations").on('click', function(e) {
        e.preventDefault();
        getAllFlightReservations();
    });
    
    $("#viewAllRoomReservations").on('click', function(e) {
        e.preventDefault();
        getAllRoomReservations();
    });

    $(document).on('click', '.star', function(el) {
        rateRACS(el.target);
    });

    loadNavbar('profileHomepageNavItem');
});

function getAllCarReservations() {
    $("#all").remove();
    $("#error").remove();
    
    $.ajax({
        url: getAllCarReservationsLink + "/" + email,
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        data: {},
        headers: { "Authorization": "Bearer " + token}, 
        success: function(carReservations) {
            if (carReservations != null && carReservations.length > 0) {
                displayCarReservations(carReservations);
            } else {
                toastr.info("No car reservations to display");
            }
            
        }, error: function(error) {
            toastr.error("Could not get all car reservations");
            console.log(error);
        }
    });
}

function getAllFlightReservations() {
    $("#all").remove();
    $("#error").remove();
    
    var l = getAllFlightReservationsLink + "/" + email;
    
    $.ajax({
        url: l,
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        data: {},
        headers: { "Authorization": "Bearer " + token}, 
        success: function(flightReservations) {
            if (flightReservations != null && flightReservations.length > 0) {
                displayFlightReservations(flightReservations);
            } else {
                toastr.info("No flight reservations to display");
            }
            
        }, error: function(error) {
            toastr.error("Could not get all flight reservations");
            console.log(error);
        }
    });
}


function getAllRoomReservations() {
    $("#all").remove();
    $("#error").remove();
    
    $.ajax({
        url: getAllRoomReservationsLink + "/" + email,
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        data: {},
        headers: { "Authorization": "Bearer " + token}, 
        success: function(roomReservations) {
            if (roomReservations != null && roomReservations.length > 0) {
                displayRoomReservations(roomReservations);
            } else {
                toastr.info("No room reservations to display");
            }
            
        }, error: function(error) {
            toastr.error("Could not get all room reservations");            
            console.log(error);
        }
    });
}

function getAllFriends() {

    $("#all").remove();
    $("#error").remove();
    
    $.ajax({
        url: getAllFriendsLink + "/" + email,
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        data: {},
        headers: { "Authorization": "Bearer " + token}, 
        success: function(friends) {
            if (friends != null && friends.length > 0) {
                displayFriends(friends);
            } else {
                toastr.info("No friends to display");
            }
        }, error: function(error) {
            toastr.error("Could not get all friends");            
            console.log(error);
        }
    });
}

function displayFriends(friends) {
    var text = "<table id=\"all\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
    text += "<caption>All friends</caption>"
    text += "<thead>";
    text += "<tr>";
    text += "<th>First name</th>";
    text += "<th>Last name</th>";
    text += "<th>Email</th>";
    text += "<th>Address</th>";
    text += "<th>Phone</th>";
    text += "</tr>";
    text += "</thead><tbody>";
    for (var friend of friends) {
        text += "<tr>";

        text += "<td>" + friend.firstName + "</td>";
        text += "<td>" + friend.lastName + "</td>";
        text += "<td>" + friend.email + "</td>";
        text += "<td>" + friend.address + "</td>";
        text += "<td>" + friend.phone + "</td>";
        
        text += "</tr>";
    }
    
    text += "</tbody></table>";
    $(document.documentElement).append(text);
    
}

/* car reservations */

function displayCarReservations(carReservations) {
    var text = "<table id=\"all\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
    text += "<caption>All car reservations</caption>"
    text += "<thead>";
    text += "<tr>";
    text += "<th>Price (USD)</th>";
    text += "<th>Confirmed&nbsp<a title=\"Reservation cannot be canceled less than two days before its start time\"><i class=\"fa fa-question-circle\" aria-hidden=\"true\"></i></a></th>";
    
    text += "<th>Date of reservation</th>";
    text += "<th>Start time</th>";
    text += "<th>End time</th>";
    text += "<th>Average RACS rating</th>";
    text += "<th>Your RACS rating</th>";
    text += "<th>Average car rating</th>";
    text += "<th>Your car rating</th>";
    text += "</tr>";
    text += "</thead><tbody>";
    for (var cr of carReservations) {
        text += "<tr>";
        var currentTime = new Date();
        var day = cr.startTime.substring(0, 2);
        // months start at 0 in js, hence the - 1
        var month = parseInt(cr.startTime.substring(3, 5)) - 1;
        var year = cr.startTime.substring(6, 10);
        var hour = cr.startTime.substring(11, 13);
        var minute = cr.startTime.substring(14);
        var startTimeDate = new Date(year, month, day, hour, minute);
        var diff = startTimeDate - currentTime;
        var oneDayInMillis = 3600000 * 24;
        text += "<td>" + cr.price + "</td>";
        if (diff <= 2 * oneDayInMillis) {
            text += "<td>Can't be canceled</td>";
        } else if (cr.confirmed) {
            
            text += "<td>" + "<button class=\"btn btn-secondary\" onClick=\"cancelCarReservation("+cr.id+")\">Cancel</button>" + "</td>";
        } else {
            text += "<td>Canceled</td>";
        }
        
        text += "<td>" + cr.dateOfReservation + "</td>";
        text += "<td>" + cr.startTime + "</td>";
        text += "<td>" + cr.endTime + "</td>";









        // RACS rating
                

        // Your rating

        text += "<td>";
        var star5id = "star5" + racs.name;
        var star4id = "star4" + racs.name;
        var star3id = "star3" + racs.name;
        var star2id = "star2" + racs.name;
        var star1id = "star1" + racs.name;
        /* each radio group has to have a different name, otherwise only one 
        one of them will be checked
         */
        var groupName = "rate" + racs.name;

        text += "<div class=\"rate\">" +
        "<input class=\"star\" type=\"radio\" id=\""+star5id+"\" name=\""+groupName+"\"  value=\"5\" />" + 
        "<label for=\""+star5id+"\">5 stars</label>" + 
        "<input class=\"star\" type=\"radio\" id=\""+star4id+"\" name=\""+groupName+"\" value=\"4\" />" +
        "<label for=\""+star4id+"\">4 stars</label>" +
        "<input class=\"star\" type=\"radio\" id=\""+star3id+"\" name=\""+groupName+"\" value=\"3\" />" +
        "<label for=\""+star3id+"\">3 stars</label>" +
        "<input class=\"star\" type=\"radio\" id=\""+star2id+"\" name=\""+groupName+"\" value=\"2\" />" +
        "<label for=\""+star2id+"\">2 stars</label>" +
        "<input class=\"star\" type=\"radio\" id=\""+star1id+"\" name=\""+groupName+"\" value=\"1\" />" +
        "<label for=\""+star1id+"\">1 star</label>" +
        "</div>";
    
        text += "</td>"; 


        // car rating


        
        text += "</tr>";
    }
    
    text += "</tbody></table>";
    $(document.documentElement).append(text);
    
}


function rateRACS(el) {
    
    var arr = el.id.split("star");
    var ratingRACSName = arr[1].split("");
    
    var obj = {};
    obj.name = arr[1].substring(1);
    obj.averageScore = parseFloat(ratingRACSName[0]);

    $.ajax({
        url: rateLink,
        method: "PUT",
        data: JSON.stringify(obj),
        contentType: "application/json",
        dataType: "json",
        headers: { "Authorization": "Bearer " + token}, 
        success: function(racs) {
            displayRACSRating(racs);
        },
        error: function(err) {
            toastr.error("Could not display rent-a-car service rating");
        }
    })
}

function displayRoomReservations(roomReservations) {
    var text = "<table id=\"all\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
    text += "<caption>All room reservations</caption>"
    text += "<thead>";
    text += "<tr>";
    text += "<th>Price (USD)</th>";
    text += "<th>Confirmed&nbsp<a title=\"Reservation cannot be cancelled less than two days before its start time\"><i class=\"fa fa-question-circle\" aria-hidden=\"true\"></i></a></th>";
    
    text += "<th>Date of reservation</th>";
    text += "<th>Start time</th>";
    text += "<th>End time</th>";
    text += "</tr>";
    text += "</thead><tbody>";
    for (var rr of roomReservations) {
        text += "<tr>";
        var currentTime = new Date();
        var day = rr.startTime.substring(0, 2);
        // months start at 0 in js, hence the - 1
        var month = parseInt(rr.startTime.substring(3, 5)) - 1;
        var year = rr.startTime.substring(6, 10);
        var hour = rr.startTime.substring(11, 13);
        var minute = rr.startTime.substring(14);
        var startTimeDate = new Date(year, month, day, hour, minute);
        var diff = startTimeDate - currentTime;
        var oneDayInMillis = 3600000 * 24;
        text += "<td>" + rr.price + "</td>";
        if (diff <= 2 * oneDayInMillis) {
            text += "<td>Can't be canceled</td>";
        } else if (rr.confirmed) {
            
            text += "<td>" + "<button class=\"btn btn-secondary\" onClick=\"cancelRoomReservation("+rr.id+")\">Cancel</button>" + "</td>";
        } else {
            text += "<td>Canceled</td>";
        }
        
        text += "<td>" + rr.dateOfReservation + "</td>";
        text += "<td>" + rr.startTime + "</td>";
        text += "<td>" + rr.endTime + "</td>";
        
        text += "</tr>";
    }
    text += "</tbody></table>";
    $(document.documentElement).append(text);
}

function cancelCarReservation(id) {
    $.ajax({
        url: cancelCarReservationLink + id,
        method: "PUT",
        dataType: "json",
        contentType: "application/json",
        data: {},
        headers: { "Authorization": "Bearer " + token}, 
        success: function() {
            getAllCarReservations();
        }, error: function(error) {
            toastr.error("Could not cancel car reservation");            
            console.log(error);
        }
    });
}


/* flight reservations */

function displayFlightReservations(flightReservations) {
    var text = "<table id=\"all\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
    text += "<caption>All flight reservations</caption>"
    text += "<thead>";
    text += "<tr>";
    text += "<th>Price (USD)</th>";
    text += "<th>Confirmed&nbsp<a title=\"Reservation cannot be canceled less than three hours before its start time\"><i class=\"fa fa-question-circle\" aria-hidden=\"true\"></i></a></th>";
    
    text += "<th>Date of reservation</th>";
    text += "<th>Flights</th>";
    text += "</tr>";
    text += "</thead><tbody>";
    for (var fr of flightReservations) {
        text += "<tr>";
        
        text += "<td>" + fr.totalPrice + "</td>";
        console.log(fr.flights);
        var currentTime = new Date();
        var takeoffTime = fr.flights[0].takeoffTime;
        var day = takeoffTime.substring(0, 2);
        // months start at 0 in js, hence the - 1
        var month = parseInt(takeoffTime.substring(3, 5)) - 1;
        var year = takeoffTime.substring(6, 10);
        var hour = takeoffTime.substring(11, 13);
        var minute = takeoffTime.substring(14);
        var startTimeDate = new Date(year, month, day, hour, minute);
        var diff = startTimeDate - currentTime;
        var oneHourInMillis = 3600000;
        
        if (diff <= 3 * oneHourInMillis) {
            text += "<td>Can't be canceled</td>";
        } else if (fr.confirmed) {
            text += "<td>" + "<button class=\"btn btn-secondary\" onClick=\"cancelFlightReservation("+fr.id+")\">Cancel</button>" + "</td>";
        } else {
            text += "<td>Canceled</td>";
        }
        
        text += "<td>" + fr.dateOfReservation + "</td>";
        text += "<td><select class=\"form-control\">"
        var lastStop = "";
        for (var flight of fr.flights) {
            
            lastStop = flight.startAirport + " " + flight.takeoffTime;
            for (var stop of flight.stops) {
                text += "<option>Departure: " + lastStop + ", Arrival: " + stop + "</option>";
                lastStop = stop;
            }
            text += "<option>Departure: " + lastStop + ", Arrival: "+ flight.endAirport + " " + flight.landingTime + " "  + "</option>";
        }
        
        text += "</select><td>";
        
        text += "</tr>";
    }
    
    text += "</tbody></table>";
    $(document.documentElement).append(text);
}

function cancelFlightReservation(id) {
    $.ajax({
        url: cancelFlightReservationLink + id,
        method: "PUT",
        dataType: "json",
        contentType: "application/json",
        data: {},
        headers: { "Authorization": "Bearer " + token}, 
        success: function() {
            getAllFlightReservations();
            getAllRoomReservations();
        }, error: function(error) {
            toastr.error("Could not cancel flight reservation");            
            console.log(error);
        }
    });
}

function cancelRoomReservation(id) {
    $.ajax({
        url: cancelRoomReservationLink + id,
        method: "PUT",
        dataType: "json",
        contentType: "application/json",
        data: {},
        headers: { "Authorization": "Bearer " + token}, 
        success: function() {
            getAllFlightReservations();
            getAllRoomReservations();
        }, error: function(error) {
            toastr.error("Could not cancel room reservation");            
            console.log(error);
        }
    });
}