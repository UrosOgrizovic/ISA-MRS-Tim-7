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
		var room =
		 {
			number : "",
			floor : "",
			numberOfGuests : "",
			overNightStay : "",
			hotelName : localStorage.getItem("hotel_name"),
			averageScore: ""
		 };
		
	    $.ajax({
	        url: "http://localhost:8080/rooms/searchRooms" + "?hotelName=" + room.hotelName + "&number=" + room.number + "&floor=" + room.floor + "&numberOfGuests=" + room.numberOfGuests + "&averageScore=" + room.averageScore + "&searchRoom=", //+ myForm.searchHotel.value
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
	var room =
		 {
			number 		: document.getElementById("number").value,
			floor 		: document.getElementById("floor").value,
			numberOfGuets	: document.getElementById("number").value,
			hotelName : localStorage.getItem("hotel_name"),
			overNightStay : document.getElementById("overNightStay").value,
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
	document.getElementById("number").value = "";
	document.getElementById("floor").value = "";
	document.getElementById("numberOfGuests").value = "";
	document.getElementById("averageScore").value="";
	document.getElementById("overNightStay").value="";
	
    $.ajax({
        url: "http://localhost:8080/rooms/searchRooms" + "?hotelName=" + room.hotelName + "&number=" + room.number + "&floor=" + room.floor + "&numberOfGuests=" + room.numberOfGuests + "&averageScore=" + room.averageScore + "&searchRoom=", //+ myForm.searchHotel.value
        method: "GET",				
        dataType: "json",
        contentType: "application/json",
        headers: { "Authorization": "Bearer " + token}, 
        success: function(hotels) {
            display_search_results(rooms);
        }, error: function(error) {
            console.log(error);
        }
    });
}

function display_search_results(rooms)
{
	console.log("Pretraga uspjesna!");
	
	var results = document.getElementById("results");
    var text = "<table id=\"all\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
    text += "<thead>";
    text += "<tr>";
    text += "<th>Number</th>";
    text += "<th>Floor</th>";
    text += "<th>Number of Guests</th>";
    text += "<th>One Night Stay price</th>";
    text += "<th></th>";
    text += "</tr>";
    text += "</thead><tbody>";
    var i = 0;
    for (var room of rooms) {
        text += "<tr>";
        text += "<td>" + room.number + "</td>";
        text += "<td>" + room.floor + "</td>";
        text += "<td>" + room.numberOfGuests + "</td>";
        text += "<td>" + room.overNightStay + "</td>";
        
        // Average rating
        /*
        text += "<td>";
        var avgstar5id = "avgstar5" + hotel.name;
        var avgstar4id = "avgstar4" + hotel.name;
        var avgstar3id = "avgstar3" + hotel.name;
        var avgstar2id = "avgstar2" + hotel.name;
        var avgstar1id = "avgstar1" + hotel.name;
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
		*/
        text += "</td>"; 
        text += "<td><button class=\"btn btn-primary\" id=\"button"+i+"\" onclick=\"book_room("+room.id+");\">Book room</button>";
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

function book_room(room_id)
{
	console.log(room_id);
	localStorage.setItem("room_id", room_id);
	location.replace("/html/roomReservation.html");
}