var getAllFriendsLink = "http://localhost:8080/users/getFriends";
var getAllCarReservationsLink = "http://localhost:8080/users/getCarReservations";
var getAllFlightReservationsLink = "http://localhost:8080/users/getFlightReservations";
var getAllHotelReservationsLink = "http://localhost:8080/users/getHotelReservations";
var cancelCarReservationLink = "http://localhost:8080/carReservations/cancel/";


$(document).ready(function(){
    $("#viewAllFriends").on('click', function(e) {
        e.preventDefault();
        getAllFriends();
    });

    $("#viewAllCarReservations").on('click', function(e) {
        e.preventDefault();
        getAllCarReservations();
    })
});

function getAllCarReservations() {
    var email = $("#email").text();
    $("#all").remove();
    $("#error").remove();
    
    $.ajax({
        url: getAllCarReservationsLink,
        method: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(email),
        success: function(carReservations) {
            displayCarReservations(carReservations);
        }, error: function(error) {
            $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
            console.log(error);
        }
    });
}

function getAllFriends() {
    var email = $("#email").text();

    $("#all").remove();
    $("#error").remove();
    
    $.ajax({
        url: getAllFriendsLink,
        method: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(email),
        success: function(friends) {
            displayFriends(friends);
        }, error: function(error) {
            $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
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

function displayCarReservations(carReservations) {
    var text = "<table id=\"all\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
    text += "<caption>All car reservations</caption>"
    text += "<thead>";
    text += "<tr>";
    text += "<th>Price (USD)</th>";
    text += "<th>Confirmed</th>";
    text += "<th>Date of reservation</th>";
    text += "<th>Start time</th>";
    text += "<th>End time</th>";
    text += "</tr>";
    text += "</thead><tbody>";
    for (var cr of carReservations) {
        text += "<tr>";

        text += "<td>" + cr.price + "</td>";
        if (cr.confirmed) {
            
            text += "<td>" + "<button class=\"btn btn-secondary\" onClick=\"cancelCarReservation("+cr.id+")\">Cancel</button>" + "</td>";
        } else {
            text += "<td>Canceled</td>";
        }
        
        text += "<td>" + cr.dateOfReservation + "</td>";
        text += "<td>" + cr.startTime + "</td>";
        text += "<td>" + cr.endTime + "</td>";
        
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
        success: function() {
            getAllCarReservations();
        }, error: function(error) {
            $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
            console.log(error);
        }
    });
}