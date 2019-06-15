import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken } from "./securityStuff.js";

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

window.create_hotel = create_hotel;
$(document).ready(function ()
		{
			var myForm = document.getElementById("createHotelForm");
            $.ajax(
                    {
        	            url: "http://localhost:8080/Admin/lookupHotelAdmins",//link assigned to method in AdminController
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

		loadNavbar('hotelsHomepageNavItem');
		}
	);



function validate_inputs(myForm)
{
    
    //console.log(myForm);

    if(myForm.name.value.trim()=="")
    {
        alert("You must enter Hotel's name!");
        return false;
    }
    else//in case of wrong type (add later)
    {

    }

    if(myForm.longitude.value.trim()=="")
    {
        alert("You must enter Hotel's longitude!");
        return false;
    }
    

    if(myForm.latitude.value.trim()=="")
    {
        alert("You must enter Hotel's latitude!");
        return false;
    }
    
    if(myForm.city.value.trim()=="")
    {
    	alert("You must enter Hotel's city!");
    }
    if(myForm.state.value.trim()=="")
    {
    	alert("You must enter Hotel's Country/State!");
    }
    if(myForm.hotelAdmin.value.trim()=="")
    {
    	alert("You must select Hotel's administrator!")
    }
/*
    if(myForm.hotelAdministrator.value=="")//add hotel administrators first ??
    {
        alert("You must enter a Hotel descritpion!");
        return false;
    }

    /*if(form.logotPath.value=="") //fix later
    {
       
        return false;
    }
    */
    
    return true;
}

function create_hotel()
{
	var myForm = document.getElementById("createHotelForm");
    if(!validate_inputs(myForm))
    {
        return;
    }
    else
    {
    	console.log(myForm.hotelAdmin.value);
        var hotel =
                {
                    name : myForm.name.value,
                    longitude : myForm.longitude.value,
                    latitude : myForm.latitude.value,
                    city : myForm.city.value,
                    state : myForm.state.value,
                    promoDescription : myForm.promoDescription.value,
                    averageScore : 0.0,
                    numberOfVotes: 0.0,
                    admin : myForm.hotelAdmin.value
                    /*//this is a DTO => not all field are necessary
                    hotelAdmin : null,//Object
                    roomConfiguration : null,//Set
                    overallRating : null,//double
                    additionalServices : null,//Set
                    */
                };
        //add image later
        //add administator later

         $("#createHotel").click(function(){
            $.ajax(
            {
	            url: "http://localhost:8080/hotels/add",//link assigned to method in HotelController
	            method: "POST",//POST request
	            dataType: 'json',//
	            contentType: "application/json",
	            data: JSON.stringify(hotel),
	            cache: false,
	            crossDomain: true,
	            headers: { "Authorization": "Bearer " + token}, 
	            success: function(result) { },
            })
         })
    }
}

function display_admins(admins)
{
	var results = document.getElementById("admins");
    var text = "<option value=\"\">select an available hotel admin:</option>";
    var admin = {};
    for (admin of admins) {
        text += "<option value=\""+admin.email+"\">" + admin.firstName + " " + admin.lastName + "</option>";
        
    }
    results.innerHTML = text;
    //document.body.appendChild(results);
}
