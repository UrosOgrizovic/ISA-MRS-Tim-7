import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken } from "./securityStuff.js";

var searchUrl = "http://localhost:8080/hotels/searchHotels";
var mapa;
var emailSelect;

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

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
	myForm = document.getElementById("hotelSearchForm");
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
    var text = "<div id=\"foundHotels\"><h2>Search results: <h2><br>";
    for (hotel of hotels) {
        text += "<div ><table border=1><tr><td>"+ " " + hotel.name + " " + hotel.city + " " + hotel.state + " " + hotel.averageScore + " " + hotel.longitude + " " + hotel.latitude + "</tr></td></div><br>";
        
    }
    
    text += "</div>";
    /*
    console.log($(document.documentElement));
    $(document.documentElement).append(text);
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