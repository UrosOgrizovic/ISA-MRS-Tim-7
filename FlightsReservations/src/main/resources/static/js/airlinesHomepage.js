var getAllLink = "/airlines";
var rateLink = "/companies/rate";

var token = localStorage.getItem("token");

$(document).ready(function(){
    if (!localStorage.getItem("loggedIn")) {
        location.replace("/html/login.html");
    }
    
    $("#viewAllAirlines").on('click', function(e) {
        
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
            success: function(airlines) {
                displayAirlines(airlines);
                
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
                console.log(error);
            }
        });
        

    });
});

function displayAirlines(airlines) {
    var text = "<table id=\"all\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
    text += "<thead>";
    text += "<tr>";
    text += "<th>Name</th>";
    text += "<th>Longitude</th>";
    text += "<th>Latitude</th>";
    text += "<th>Description</th>";
    text += "<th>Average rating</th>";
    text += "<th>Your rating</th>";

    text += "</tr>";
    text += "</thead><tbody>";
    
    for (var al of airlines) {
        
        text += "<tr>";
        text += "<td>" + al.name + "</td>";
        text += "<td>" + al.longitude + "</td>";
        text += "<td>" + al.latitude + "</td>";
        text += "<td>" + al.promoDescription + "</td>";
                            
        
        // Average rating

        text += "<td>";
        var avgstar5id = "avgstar5" + al.name;
        var avgstar4id = "avgstar4" + al.name;
        var avgstar3id = "avgstar3" + al.name;
        var avgstar2id = "avgstar2" + al.name;
        var avgstar1id = "avgstar1" + al.name;
        /* each radio group has to have a different name, otherwise only one 
        one of them will be checked
         */
        var groupName = "avg" + al.name;
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
        "<div id=\"avgscore"+al.name +"\"></div>" +
        "</div>";        

        text += "</td>"; 

        // Your rating

        text += "<td>";
        var star5id = "star5" + al.name;
        var star4id = "star4" + al.name;
        var star3id = "star3" + al.name;
        var star2id = "star2" + al.name;
        var star1id = "star1" + al.name;
        /* each radio group has to have a different name, otherwise only one 
        one of them will be checked
         */
        var groupName = "rate" + al.name;
        text += "<div class=\"rate\">" +
        "<input type=\"radio\" id=\""+star5id+"\" name=\""+groupName+"\" onclick=\"rateAirline("+star5id+")\" value=\"5\" />" + 
        "<label for=\""+star5id+"\">5 stars</label>" + 
        "<input type=\"radio\" id=\""+star4id+"\" name=\""+groupName+"\" onclick=\"rateAirline("+star4id+")\" value=\"4\" />" +
        "<label for=\""+star4id+"\">4 stars</label>" +
        "<input type=\"radio\" id=\""+star3id+"\" name=\""+groupName+"\" onclick=\"rateAirline("+star3id+")\" value=\"3\" />" +
        "<label for=\""+star3id+"\">3 stars</label>" +
        "<input type=\"radio\" id=\""+star2id+"\" name=\""+groupName+"\" onclick=\"rateAirline("+star2id+")\" value=\"2\" />" +
        "<label for=\""+star2id+"\">2 stars</label>" +
        "<input type=\"radio\" id=\""+star1id+"\" name=\""+groupName+"\" onclick=\"rateAirline("+star1id+")\" value=\"1\" />" +
        "<label for=\""+star1id+"\">1 star</label>" +
        "</div>";
    
        text += "</td>"; 

        text += "</tr>";


        text += "</tr>";
    }

    text += "</tbody></table>";

    $(document.documentElement).append(text);
    
    for (var airline of airlines) {        
        displayAirlineRating(airline);
    }
}

function displayAirlineRating(airline) {
    if (airline.averageScore >= 4.5) {
        document.getElementById("avgstar5"+airline.name).checked = true;
    } else if (airline.averageScore >= 3.5) {
        document.getElementById("avgstar4"+airline.name).checked = true;
    } else if (airline.averageScore >= 2.5) {
        document.getElementById("avgstar3"+airline.name).checked = true;
    } else if (airline.averageScore >= 1.5) {
        document.getElementById("avgstar2"+airline.name).checked = true;
    } else {
        document.getElementById("avgstar1"+airline.name).checked = true;
    }
    document.getElementById("avgscore"+airline.name).innerHTML = "("+airline.averageScore.toString().match(/^-?\d+(?:\.\d{0,2})?/)[0]+")";
}

function rateAirline(el) {
    var arr = el.id.split("star");
    var ratingAirlineName = arr[1].split("");
    
    var obj = {};
    obj.name = ratingAirlineName.slice(1).join("");
    obj.averageScore = parseFloat(ratingAirlineName[0]);

    $.ajax({
        url: rateLink,
        method: "PUT",
        data: JSON.stringify(obj),
        contentType: "application/json",
        dataType: "json",
        headers: { "Authorization": "Bearer " + token}, 
        success: function(airline) {
            displayAirlineRating(airline);
        },
        error: function(err) {
            console.log(err);
        }
    })
}