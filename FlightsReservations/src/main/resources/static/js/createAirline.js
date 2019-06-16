import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken } from "./securityStuff.js";

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

window.create_airline = create_airline;
$(document).ready(function ()
		{
			var myForm = document.getElementById("createAirlineForm");
            $.ajax(
                    {
        	            url: "http://localhost:8080/Admin/lookupAirlineAdmins",//link assigned to method in AdminController
        	            method: "GET",				
        	            dataType: "json",
        	            contentType: "application/json",
        	            headers: { "Authorization": "Bearer " + token}, 
        	            success: function(admins) {
        	                display_admins(admins);
        	            }, error: function(error) {
        	                console.log(error);
        	            }
                    })
            //console.log(admins);

		loadNavbar('airlinesHomepageNavItem');
		}
	);

function validate_inputs(myForm)
{
    
    if(myForm.name.value.trim()=="")
    {
        alert("You must enter Airline's name!");
        return false;
    }

    if(myForm.longitude.value.trim()=="")
    {
        alert("You must enter Airline's longitude!");
        return false;
    }
    

    if(myForm.latitude.value.trim()=="")
    {
        alert("You must enter Airline's latitude!");
        return false;
    }
    
    if(myForm.city.value.trim()=="")
    {
    	alert("You must enter Airline's city!");
    	return false;
    }
    if(myForm.state.value.trim()=="")
    {
    	alert("You must enter Airline's Country/State!");
    	return false;
    }
    if(myForm.airlineAdmin.value.trim()=="")
    {
    	alert("You must select Airline's administrator!")
    	return false;
    }
    
    return true;
}

function create_airline()
{
    var myForm = document.getElementById("createAirlineForm");
    if(!validate_inputs(myForm))
    {
        return;
    }
    else
    {
        var airline =
                {
        			id: 1,
                    name : myForm.name.value,
                    longitude : myForm.longitude.value,
                    latitude : myForm.latitude.value,
                    city : myForm.city.value,
                    state : myForm.state.value,
                    promoDescription : myForm.promoDescription.value,
                    averageScore: 0,//default value
                    numberOfVotes: 0,//default value
                    airlineAdmin: myForm.airlineAdmin.value
                };
        //TODO: add administator later

         $("#createAirline").click(function(){
            $.ajax(
            {
            url: "http://localhost:8080/airlines/add",//link assigned to method in HotelController
            method: "POST",//POST request
            dataType: 'json',//
            contentType: "application/json",
            data: JSON.stringify(airline),
            cache: false,
            crossDomain: true,
            headers: { "Authorization": "Bearer " + token}, 
            success: function(result) { },
            })
    });
    }
}

function display_admins(admins)
{
	var results = document.getElementById("admins");
    var text = "<option value=\"\">select an available airline admin:</option>";
    var admin = {};
    for (admin of admins) {
        text += "<option value=\""+admin.email+"\">" + admin.firstName + " " + admin.lastName + "</option>";
        
    }
    results.innerHTML = text;
    //document.body.appendChild(results);
}