var getAllHotelsLink = "/hotels/getAll";
var rateLink = "/companies/rate";

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

$(document).ready(function(){
    
    $("#viewAllHotels").on('click', function(e) {
        e.preventDefault();
        
        $("#all").remove();
        $("#error").remove();
        
        $.ajax({
            url: getAllHotelsLink,
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            headers: { "Authorization": "Bearer " + token}, 
            success: function(hotels) {
                displayHotels(hotels);
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
                console.log(error);
            }
        });
    });
    
});

function displayHotels(hotels) {
    var text = "<table id=\"all\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
    text += "<thead>";
    text += "<tr>";
    text += "<th>Name</th>";
    text += "<th>Longitude</th>";
    text += "<th>Latitude</th>";
    text += "<th>Description</th>";
    text += "<th>Pricelist</th>";
    text += "<th>Rooms</th>";
    text += "<th>Average rating</th>";
    text += "<th>Your rating</th>";
    text += "</tr>";
    text += "</thead><tbody>";
    
    for (var hotel of hotels) {
        text += "<tr>";
        text += "<td>" + hotel.name + "</td>";
        text += "<td>" + hotel.longitude + "</td>";
        text += "<td>" + hotel.latitude + "</td>";
        text += "<td>" + hotel.promoDescription + "</td>";
        

        text += "<td><br><div class=\"dropdown\">";
        text += "<button class=\"btn btn-secondary dropdown-toggle\" type=\"button\" id=\"dropdownPricelistButton\" data-toggle=\"dropdown\"aria-haspopup=\"true\" aria-expanded=\"false\">Pricelist</button>";
        text += "<div class=\"dropdown-menu\" aria-labelledby=\"dropdownPricelistButton\">";
        for (var item of hotel.pricelist) {
            var textItem = JSON.stringify(item, null, 4);
            textItem = textItem.substring(1, textItem.length - 1);
            textItem = textItem.split('"').join('');
            textItem += "$";
            text += "<a class=\"dropdown-item\" href=\"#\">" + textItem + "</a>";
        }
        text += "</div></div></td>";

        
        text += "<td><br><div class=\"dropdown\">";
        text += "<button class=\"btn btn-secondary dropdown-toggle\" type=\"button\" id=\"dropdownRoomsButton\" data-toggle=\"dropdown\"aria-haspopup=\"true\" aria-expanded=\"false\">Rooms</button>";
        text += "<div class=\"dropdown-menu\" aria-labelledby=\"dropdownRoomsButton\">";
        //TODO: ISPIS SOBE
        for (var room of hotel.roomConfiguration) {
            var carText = "#" + room.id + " rating: " + room.overallRating + " overnight: " + room.overNightStay + "$";
            text += "<a class=\"dropdown-item\" href=\"#\">" + carText + "</a>";
        }
        text += "</div></div></td>";

        // Average rating

        text += "<td>";
        var avgstar5id = "avgstar5" + hotel.name;
        var avgstar4id = "avgstar4" + hotel.name;
        var avgstar3id = "avgstar3" + hotel.name;
        var avgstar2id = "avgstar2" + hotel.name;
        var avgstar1id = "avgstar1" + hotel.name;
        /* each radio group has to have a different name, otherwise only one 
        one of them will be checked
            */
        var groupName = "avg" + hotel.name;
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
        "<div id=\"avgscore"+hotel.name+"\"></div>" +
        "</div>";        

        text += "</td>"; 

        // Your rating

        text += "<td>";
        var star5id = "star5" + hotel.name;
        var star4id = "star4" + hotel.name;
        var star3id = "star3" + hotel.name;
        var star2id = "star2" + hotel.name;
        var star1id = "star1" + hotel.name;
        /* each radio group has to have a different name, otherwise only one 
        one of them will be checked
            */
        var groupName = "rate" + hotel.name;
        text += "<div class=\"rate\">" +
        "<input type=\"radio\" id=\""+star5id+"\" name=\""+groupName+"\" onclick=\"rateHotel("+star5id+")\" value=\"5\" />" + 
        "<label for=\""+star5id+"\">5 stars</label>" + 
        "<input type=\"radio\" id=\""+star4id+"\" name=\""+groupName+"\" onclick=\"rateHotel("+star4id+")\" value=\"4\" />" +
        "<label for=\""+star4id+"\">4 stars</label>" +
        "<input type=\"radio\" id=\""+star3id+"\" name=\""+groupName+"\" onclick=\"rateHotel("+star3id+")\" value=\"3\" />" +
        "<label for=\""+star3id+"\">3 stars</label>" +
        "<input type=\"radio\" id=\""+star2id+"\" name=\""+groupName+"\" onclick=\"rateHotel("+star2id+")\" value=\"2\" />" +
        "<label for=\""+star2id+"\">2 stars</label>" +
        "<input type=\"radio\" id=\""+star1id+"\" name=\""+groupName+"\" onclick=\"rateHotel("+star1id+")\" value=\"1\" />" +
        "<label for=\""+star1id+"\">1 star</label>" +
        "</div>";
    
        text += "</td>"; 

        text += "</tr>";
    }
    text += "</tbody></table>";
    $(document.documentElement).append(text);
    for (var hotel of hotels) {
        displayHotelRating(hotel);
    }
}

function displayHotelRating(hotel) {
    if (hotel.averageScore >= 4.5) {
        document.getElementById("avgstar5"+hotel.name).checked = true;
    } else if (hotel.averageScore >= 3.5) {
        document.getElementById("avgstar4"+hotel.name).checked = true;
    } else if (hotel.averageScore >= 2.5) {
        document.getElementById("avgstar3"+hotel.name).checked = true;
    } else if (hotel.averageScore >= 1.5) {
        document.getElementById("avgstar2"+hotel.name).checked = true;
    } else {
        document.getElementById("avgstar1"+hotel.name).checked = true;
    }
    document.getElementById("avgscore"+hotel.name).innerHTML = "("+hotel.averageScore.toString().match(/^-?\d+(?:\.\d{0,2})?/)[0]+")";
}

function rateHotel(el) {
    var arr = el.id.split("star");
    var ratingHotelName = arr[1].split("");
    
    var obj = {};
    obj.name = ratingHotelName.slice(1).join("");
    obj.averageScore = parseFloat(ratingHotelName[0]);

    $.ajax({
        url: rateLink,
        method: "PUT",
        data: JSON.stringify(obj),
        contentType: "application/json",
        dataType: "json",
        headers: { "Authorization": "Bearer " + token}, 
        success: function(hotel) {
            displayHotelRating(hotel);
        },
        error: function(err) {
            console.log(err);
        }
    })
}