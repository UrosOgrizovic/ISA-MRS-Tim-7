var getAllLink = "/flights";
var rateLink = "/flights/rate";

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

$(document).ready(function(){
    
    $("#viewAllFlights").on('click', function(e) {
        
        e.preventDefault();
        
        $("#all").remove();
        $("#error").remove();
        
        $.ajax({
            url: getAllLink,
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            headers: { "Authorization": "Bearer " + token}, 
            success: function(flights) {
                displayFlights(flights);
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
                console.log(error);
            }
        });
        

    });
});

function displayFlights(flights) {
    var text = "<table id=\"all\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
    text += "<thead>";
    text += "<tr>";
    text += "<th>Name</th>";
    text += "<th>Takeoff time</th>";
    text += "<th>Landing time</th>";
    text += "<th>Flight distance</th>";
    text += "<th>Airline</th>";
    text += "<th>Start airport</th>";
    text += "<th>End airport</th>";
    text += "<th>Average rating</th>";
    text += "<th>Your rating</th>";
    text += "</tr>";
    text += "</thead><tbody>";
    
    for (var flight of flights) {
        text += "<tr>";
        text += "<td>" + flight.id + "</td>";
        text += "<td>" + flight.takeoffTime + "</td>";
        text += "<td>" + flight.landingTime + "</td>";
        text += "<td>" + flight.flightDistance + "</td>";
        text += "<td>" + flight.airlineName + "</td>";
        text += "<td>" + flight.startAirport + "</td>";
        text += "<td>" + flight.endAirport + "</td>";
       
        // Average rating

        text += "<td>";
        var avgstar5id = "avgstar5" + flight.id;
        var avgstar4id = "avgstar4" + flight.id;
        var avgstar3id = "avgstar3" + flight.id;
        var avgstar2id = "avgstar2" + flight.id;
        var avgstar1id = "avgstar1" + flight.id;
        /* each radio group has to have a different name, otherwise only one 
        one of them will be checked
         */
        var groupName = "avg" + flight.id;
        text += "<div class=\"rate\">" +
        "<input type=\"radio\" id=\""+avgstar5id+"\" name=\""+groupName+"\" value=\"5\" />" + 
        "<label for=\""+avgstar5id+"\">5 stars</label>" + 
        "<input type=\"radio\" id=\""+avgstar4id+"\" name=\""+groupName+"\" value=\"4\" />" +
        "<label for=\""+avgstar4id+"\">4 stars</label>" +
        "<input type=\"radio\" id=\""+avgstar3id+"\" name=\""+groupName+"\" value=\"3\" />" +
        "<label for=\""+avgstar3id+"\">3 stars</label>" +
        "<input type=\"radio\" id=\""+avgstar2id+"\" name=\""+groupName+"\" value=\"2\" />" +
        "<label for=\""+avgstar2id+"\">2 stars</label>" +
        "<input type=\"radio\" id=\""+avgstar1id+"\" name=\""+groupName+"\" value=\"1\" />" +
        "<label for=\""+avgstar1id+"\">1 star</label>" +
        "<div id=\"avgscore"+flight.id+"\"></div>" +
        "</div>";        

        text += "</td>"; 

        // Your rating

        text += "<td>";
        var star5id = "star5" + flight.id;
        var star4id = "star4" + flight.id;
        var star3id = "star3" + flight.id;
        var star2id = "star2" + flight.id;
        var star1id = "star1" + flight.id;
        /* each radio group has to have a different name, otherwise only one 
        one of them will be checked
         */
        var groupName = "rate" + flight.id;
        text += "<div class=\"rate\">" +
        "<input type=\"radio\" id=\""+star5id+"\" name=\""+groupName+"\" onclick=\"rateFlight("+star5id+")\" value=\"5\" />" + 
        "<label for=\""+star5id+"\">5 stars</label>" + 
        "<input type=\"radio\" id=\""+star4id+"\" name=\""+groupName+"\" onclick=\"rateFlight("+star4id+")\" value=\"4\" />" +
        "<label for=\""+star4id+"\">4 stars</label>" +
        "<input type=\"radio\" id=\""+star3id+"\" name=\""+groupName+"\" onclick=\"rateFlight("+star3id+")\" value=\"3\" />" +
        "<label for=\""+star3id+"\">3 stars</label>" +
        "<input type=\"radio\" id=\""+star2id+"\" name=\""+groupName+"\" onclick=\"rateFlight("+star2id+")\" value=\"2\" />" +
        "<label for=\""+star2id+"\">2 stars</label>" +
        "<input type=\"radio\" id=\""+star1id+"\" name=\""+groupName+"\" onclick=\"rateFlight("+star1id+")\" value=\"1\" />" +
        "<label for=\""+star1id+"\">1 star</label>" +
        "</div>";
    
        text += "</td>"; 

        text += "</tr>";
    }
    text += "</tbody></table>";
    $(document.documentElement).append(text);
    
    for (var flight of flights) {        
        displayFlightRating(flight);
    }
}

function displayFlightRating(flight) {
    if (flight.averageScore >= 4.5) {
        document.getElementById("avgstar5"+flight.id).checked = true;
    } else if (flight.averageScore >= 3.5) {
        document.getElementById("avgstar4"+flight.id).checked = true;
    } else if (flight.averageScore >= 2.5) {
        document.getElementById("avgstar3"+flight.id).checked = true;
    } else if (flight.averageScore >= 1.5) {
        document.getElementById("avgstar2"+flight.id).checked = true;
    } else {
        document.getElementById("avgstar1"+flight.id).checked = true;
    }
    document.getElementById("avgscore"+flight.id).innerHTML = "("+flight.averageScore.toString().match(/^-?\d+(?:\.\d{0,2})?/)[0]+")";
}

function rateFlight(el) {
    var arr = el.id.split("star");
    var ratingFlightID = arr[1].split("");
    
    var obj = {};
    obj.id = ratingFlightID.slice(1).join("");
    obj.averageScore = parseFloat(ratingFlightID[0]);

    $.ajax({
        url: rateLink,
        method: "PUT",
        data: JSON.stringify(obj),
        contentType: "application/json",
        dataType: "json",
        headers: { "Authorization": "Bearer " + token}, 
        success: function(flight) {
            displayFlightRating(flight);
        },
        error: function(err) {
            console.log(err);
        }
    })
}
