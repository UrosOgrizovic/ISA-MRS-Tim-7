import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken } from "./securityStuff.js";

var searchUrl = "http://localhost:8080/hotels/searchHotels";
var mapa;
var emailSelect;

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

$(document).ready(function(){ loadNavbar("hotelsHomepageNavItem"); });

window.search_hotel = search_hotel;
function validate_inputs(myForm)
{
	if(myForm.averageScore.value.trim()=="")
	{
		return true;
	}
	else if(isNaN(myForm.averageScore.value) )
	{
		return false;
	}
	else if(myForm.averageScore.value>=0 && myForm.averageScore.value<=5)//rating is between 0 and 5
	{
		return false;
	}
	else return true;
}

function search_hotel()
{
	var hotel =
		 {
			name 		: "",
			city 		: "",
			state		: "",
			averageScore: ""
		 }
	var myForm = document.getElementById("hotelSearchForm");
	if(validate_inputs(myForm))
	{
		if(myForm.name.value.trim()!="")
		{
			hotel.name = myForm.name.value;
		}
		if(myForm.city.value.trim()!="")
		{
			hotel.city = myForm.city.value;
		}
		if(myForm.state.value.trim()!="")
		{
			hotel.state = myForm.state.value;
		}
		if(myForm.averageScore.value.trim()!="")
		{
			hotel.averageScore = myForm.averageScore.value;
		}
	}
	document.getElementById("name").value = "";
	document.getElementById("averageScore").value="";
	document.getElementById("city").value = "";
	document.getElementById("state").value = "";
	
    $.ajax({
        url: "http://localhost:8080/hotels/searchHotels" + "?name=" + hotel.name + "&city=" + hotel.city + "&state=" + hotel.state + "&averageScore=" + hotel.averageScore + "&searchHotel=", //+ myForm.searchHotel.value
        method: "GET",				
        dataType: "json",
        contentType: "application/json",
        headers: { "Authorization": "Bearer " + token}, 
        success: function(hotels) {
            display_search_results(hotels);
        }, error: function(error) {
            console.log(error);
        }
    });
}

function display_search_results(hotels)
{
	console.log("Pretraga uspjesna!");
	
	var results = document.getElementById("results");
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
	/*
    var text = "<div id=\"foundHotels\"><h2>Search results: <h2><br>";
    var hotel;
    for (hotel of hotels) {
        text += "<div ><table border=1><tr><td>"+ " " + hotel.name + " " + hotel.city + " " + hotel.state + " " + hotel.averageScore + " " + hotel.longitude + " " + hotel.latitude + "</tr></td></div><br>";
        
    }
    
    text += "</div>";
    
    */
    results.innerHTML = text;
    document.body.appendChild(results);
    //$(document).insertAdjacentHTML(text);
    
    /*
    var row = document.getElementById("red"); // find row to copy
    var table = document.getElementById("tabela"); // find table to append to
    var clone = row.cloneNode(true); // copy children too
    clone.id = "newID"; // change id or other attributes/contents
    table.appendChild(clone); // add new row to end of table
    */
}