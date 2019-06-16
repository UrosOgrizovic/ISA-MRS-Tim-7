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
window.rate = rate;
window.displayRatingStarsOnLoad = displayRatingStarsOnLoad;
window.checkCarRatings = checkCarRatings;


var msg = localStorage.getItem("successMessageForToastr");
var companyRatingsToCheck = [];
var carRatingsToCheck = [];
// used in displayRatingStarsOnLoad
const racsStars = ["racsstar1|", "racsstar2|", "racsstar3|", "racsstar4|", "racsstar5|"];
const carStars = ["carstar1|", "carstar2|", "carstar3|", "carstar4|", "carstar5|"];

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
        rate(el.target);
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
    text += "<th>Your RACS rating</th>";
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

        text += "<td>";
        var star5id = "racsstar5|" + cr.id + "|" + cr.racsBranchOfficeId;
        var star4id = "racsstar4|" + cr.id + "|" + cr.racsBranchOfficeId;
        var star3id = "racsstar3|" + cr.id + "|" + cr.racsBranchOfficeId;
        var star2id = "racsstar2|" + cr.id + "|" + cr.racsBranchOfficeId;
        var star1id = "racsstar1|" + cr.id + "|" + cr.racsBranchOfficeId;
        /* each radio group has to have a different name, otherwise only one 
        one of them will be checked
         */
        var groupName = "racsrate" + cr.id;
        
        // if already rated, display without star class and add to companyRatingsToCheck
        if (cr.rating.companyRating > 0) {
            text += "<div class=\"rate\">" +
            "<input type=\"radio\" id=\""+star5id+"\" name=\""+groupName+"\"  value=\"5\" />" + 
            "<label for=\""+star5id+"\">5 stars</label>" + 
            "<input type=\"radio\" id=\""+star4id+"\" name=\""+groupName+"\" value=\"4\" />" +
            "<label for=\""+star4id+"\">4 stars</label>" +
            "<input type=\"radio\" id=\""+star3id+"\" name=\""+groupName+"\" value=\"3\" />" +
            "<label for=\""+star3id+"\">3 stars</label>" +
            "<input type=\"radio\" id=\""+star2id+"\" name=\""+groupName+"\" value=\"2\" />" +
            "<label for=\""+star2id+"\">2 stars</label>" +
            "<input type=\"radio\" id=\""+star1id+"\" name=\""+groupName+"\" value=\"1\" />" +
            "<label for=\""+star1id+"\">1 star</label>" +
            "</div>";

            if (cr.rating.companyRating == 1) {
                companyRatingsToCheck.push(star1id);
            } else if (cr.rating.companyRating == 2) {
                companyRatingsToCheck.push(star2id);
            } else if (cr.rating.companyRating == 3) {
                companyRatingsToCheck.push(star3id);
            } else if (cr.rating.companyRating == 4) {
                companyRatingsToCheck.push(star4id);
            } else if (cr.rating.companyRating == 5) {
                companyRatingsToCheck.push(star5id);
            }
        } else {
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

        }

        

            
        
        text += "</td>"; 
        

        // car rating

        text += "<td>";
        var star5id = "carstar5|" + cr.id + "|" + cr.racsBranchOfficeId;
        var star4id = "carstar4|" + cr.id + "|" + cr.racsBranchOfficeId;
        var star3id = "carstar3|" + cr.id + "|" + cr.racsBranchOfficeId;
        var star2id = "carstar2|" + cr.id + "|" + cr.racsBranchOfficeId;
        var star1id = "carstar1|" + cr.id + "|" + cr.racsBranchOfficeId;
        /* each radio group has to have a different name, otherwise only one 
        one of them will be checked
         */
        var groupName = "carrate" + cr.id;

        // if already rated, display without star class and add to carRatingsToCheck
        if (cr.rating.flightRoomCarRating > 0) {
            text += "<div class=\"rate\">" +
            "<input type=\"radio\" id=\""+star5id+"\" name=\""+groupName+"\"  value=\"5\" />" + 
            "<label for=\""+star5id+"\">5 stars</label>" + 
            "<input type=\"radio\" id=\""+star4id+"\" name=\""+groupName+"\" value=\"4\" />" +
            "<label for=\""+star4id+"\">4 stars</label>" +
            "<input type=\"radio\" id=\""+star3id+"\" name=\""+groupName+"\" value=\"3\" />" +
            "<label for=\""+star3id+"\">3 stars</label>" +
            "<input type=\"radio\" id=\""+star2id+"\" name=\""+groupName+"\" value=\"2\" />" +
            "<label for=\""+star2id+"\">2 stars</label>" +
            "<input type=\"radio\" id=\""+star1id+"\" name=\""+groupName+"\" value=\"1\" />" +
            "<label for=\""+star1id+"\">1 star</label>" +
            "</div>";

            if (cr.rating.flightRoomCarRating == 1) {
                carRatingsToCheck.push(star1id);
            } else if (cr.rating.flightRoomCarRating == 2) {
                carRatingsToCheck.push(star2id);
            } else if (cr.rating.flightRoomCarRating == 3) {
                carRatingsToCheck.push(star3id);
            } else if (cr.rating.flightRoomCarRating == 4) {
                carRatingsToCheck.push(star4id);
            } else if (cr.rating.flightRoomCarRating == 5) {
                carRatingsToCheck.push(star5id);
            }

        } else {
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
        }

        text += "</td>"; 
        
        text += "</tr>";
    }
    
    text += "</tbody></table>";
    $(document.documentElement).append(text);
    displayRatingStarsOnLoad();
    
}

function displayRatingStarsOnLoad() {
    
    var afterSplit = [];
    for (var id of companyRatingsToCheck) {
        document.getElementById(id).checked = true;
        afterSplit = id.split("|");
        for (var star of racsStars) {
            document.getElementById(star + afterSplit[1] + "|" + afterSplit[2]).disabled = true;
        }
    }
    for (var id of carRatingsToCheck) {
        document.getElementById(id).checked = true;
        afterSplit = id.split("|");
        for (var star of carStars) {
            document.getElementById(star + afterSplit[1] + "|" + afterSplit[2]).disabled = true;
        }
    }
}

function checkCarRatings() {

}

function rate(el) {
    
    console.log(el);
    var arr = el.id.split("star");
    var ratingObjectName = arr[1].split("|");
    
    var obj = {};
    
    if (arr[0].includes("racs")) {
        obj.companyRating = parseInt(ratingObjectName[0]);
        obj.flightRoomCarRating = 0;
    } else {
        obj.companyRating = 0;
        obj.flightRoomCarRating = parseInt(ratingObjectName[0]);
    }
    obj.reservationId = parseInt(ratingObjectName[1]);
    obj.companyBranchOfficeId = parseInt(ratingObjectName[2]);
    console.log(obj);
    $.ajax({
        url: rateLink,
        method: "PUT",
        data: JSON.stringify(obj),
        contentType: "application/json",
        dataType: "json",
        headers: { "Authorization": "Bearer " + token}, 
        success: function(rating) {
            displayRating(rating);
        },
        error: function(err) {
            toastr.error("Could not display rent-a-car service rating");
            console.log(err);
        }
    })
}

function displayRating(rating) {
    var elementId = "";
    if (rating.companyRating == 5) {
        elementId = "racsstar5|" + rating.reservationId + "|" + rating.companyBranchOfficeId;
        document.getElementById(elementId).checked = true;
    } else if (rating.companyRating == 4) {
        elementId = "racsstar4|" + rating.reservationId + "|" + rating.companyBranchOfficeId;
        document.getElementById(elementId).checked = true;
    } else if (rating.companyRating == 3) {
        elementId = "racsstar3|" + rating.reservationId + "|" + rating.companyBranchOfficeId;
        document.getElementById(elementId).checked = true;
    } else if (rating.companyRating == 2) {
        elementId = "racsstar2|" + rating.reservationId + "|" + rating.companyBranchOfficeId;
        document.getElementById(elementId).checked = true;
    } else if (rating.companyRating == 1) {
        elementId = "racsstar1|" + rating.reservationId + "|" + rating.companyBranchOfficeId;
        document.getElementById(elementId).checked = true;
        
    }
    
    // remove class 'star' so that user cannot rate again
    if (rating.companyRating > 0) {
        var elIds = ["racsstar5|" + rating.reservationId + "|" + rating.companyBranchOfficeId, 
                    "racsstar4|" + rating.reservationId + "|" + rating.companyBranchOfficeId,
                    "racsstar3|" + rating.reservationId + "|" + rating.companyBranchOfficeId,
                    "racsstar2|" + rating.reservationId + "|" + rating.companyBranchOfficeId,
                    "racsstar1|" + rating.reservationId + "|" + rating.companyBranchOfficeId];
        for (var elId of elIds) {
            document.getElementById(elId).classList.remove("star");
            document.getElementById(elId).disabled = true;
        }
        
    }
    
    if (rating.flightRoomCarRating == 5) {
        elementId = "carstar5|" + rating.reservationId + "|" + rating.companyBranchOfficeId;
        document.getElementById(elementId).checked = true;
        document.getElementById(elementId).classList.remove("star");
    } else if (rating.flightRoomCarRating == 4) {
        elementId = "carstar4|" + rating.reservationId + "|" + rating.companyBranchOfficeId;
        document.getElementById(elementId).checked = true;
        document.getElementById(elementId).classList.remove("star");
    } else if (rating.flightRoomCarRating == 3) {
        elementId = "carstar3|" + rating.reservationId + "|" + rating.companyBranchOfficeId;
        document.getElementById(elementId).checked = true;
        document.getElementById(elementId).classList.remove("star");
    } else if (rating.flightRoomCarRating == 2) {
        elementId = "carstar2|" + rating.reservationId + "|" + rating.companyBranchOfficeId;
        document.getElementById(elementId).checked = true;
        document.getElementById(elementId).classList.remove("star");
    } else if (rating.flightRoomCarRating == 1) {
        elementId = "carstar1|" + rating.reservationId + "|" + rating.companyBranchOfficeId;
        document.getElementById(elementId).checked = true;
        document.getElementById(elementId).classList.remove("star");
    }

    // remove class 'star' so that user cannot rate again
    if (rating.flightRoomCarRating > 0) {
        var elIds = ["carstar5|" + rating.reservationId + "|" + rating.companyBranchOfficeId, 
                    "carstar4|" + rating.reservationId + "|" + rating.companyBranchOfficeId,
                    "carstar3|" + rating.reservationId + "|" + rating.companyBranchOfficeId,
                    "carstar2|" + rating.reservationId + "|" + rating.companyBranchOfficeId,
                    "carstar1|" + rating.reservationId + "|" + rating.companyBranchOfficeId];
        for (var elId of elIds) {
            document.getElementById(elId).classList.remove("star");
            document.getElementById(elId).disabled = true;
        }
    }
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