import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken } from "./securityStuff.js";

var searchUrl = "http://localhost:8080/hotels/searchHotels";
var mapa;
var emailSelect;

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

$(document).ready(function()
	{
		loadNavbar("hotelsHomepageNavItem");
		var hotel =
		 {
			name 		: "",
			//city 		: localStorage.getItem("city"),
			//state		: localStorage.getItem("state"),
			city : "Bijeljina",
			state: "Srpska",
			averageScore: ""
		 };
		
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
	});

window.search_hotel = search_hotel;
window.book_hotel = book_hotel;
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
			city 		: localStorage.getItem("city"),
			state		: localStorage.getItem("state"),
			averageScore: ""
		 }
	var myForm = document.getElementById("hotelSearchForm");
	if(validate_inputs(myForm))
	{
		if(myForm.name.value.trim()!="")
		{
			hotel.name = myForm.name.value;
		}
		if(myForm.averageScore.value.trim()!="")
		{
			hotel.averageScore = myForm.averageScore.value;
		}
	}
	document.getElementById("name").value = "";
	document.getElementById("averageScore").value="";
	//document.getElementById("city").value = "";
	//document.getElementById("state").value = "";
	
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
    text += "<th>Average rating</th>";
    text += "<th></th>";
    text += "</tr>";
    text += "</thead><tbody>";
    var i = 0;
    for (var hotel of hotels) {
        text += "<tr>";
        text += "<td>" + hotel.name + "</td>";
        text += "<td>" + hotel.longitude + "</td>";
        text += "<td>" + hotel.latitude + "</td>";
        text += "<td>" + hotel.promoDescription + "</td>";
        
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
        text += `<td><button class="btn btn-primary" id="button${i}" onclick="book_hotel(${hotel.id}, ${hotel.name});"> Book hotel <button></td>`;
        text += "</tr>";
        i += 1;
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

function book_hotel(hotel_id, hotel_name)
{
	console.log(hotel_id);
	localStorage.setItem("hotel_id", hotel_id);
	localStorage.setItem("hotel_name", hotel_name);
	location.replace("/hmtl/hotelReservation2.html");
}