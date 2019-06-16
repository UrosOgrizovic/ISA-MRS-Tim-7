import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken } from "./securityStuff.js";

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

window.create_room = create_room;
$(document).ready(function ()
		{
	/*
			var myForm = document.getElementById("createRoomForm");
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
	 */
		loadNavbar('hotelsHomepageNavItem');
		}
	);

function validate_inputs(myForm)
{
    
    if(!Number.isInteger( Number(myForm.number.value) ) )
    {
        alert("Room number must be an integer!");
        return false;
    }

    
    if(!Number.isInteger( Number(myForm.numberOfGuests.value) ) )
    {
        alert("Number of guests must be an integer!");
        return false;
    }
    else//in case of wrong type (add later)
    {

    }
    
    if(isNaN( Number(myForm.overnightStay.value) ) )
    {
        alert("You must Room number must be a number!");
        return false;
    }
    else//in case of wrong type (add later)
    {

    }

    if(myForm.type.value.trim()=="")
    {
        alert("You must select Hotel room's type!");
        return false;
    }
    
    return true;
}

function create_room()
{
    var myForm = document.getElementById("createRoomForm");
    if(!validate_inputs(myForm))
    {
        return;
    }
    else
    {
        var room =
                {
                    number : myForm.number.value,
                    numberOfGuests : myForm.numberOfGuests.value,
                    overnightStay : myForm.overnightStay.value,
                    type : myForm.type.value,
                    averageScore : 0.0,
                    floor : myForm.floor.value,
                    numberOfVotes: 0
                    
                    /*//this is a DTO => not all field are necessary
                    hotelAdmin : null,//Object
                    roomConfiguration : null,//Set
                    
                    additionalServices : null,//Set
                    */
                };
        //add image later
        //add administator later

         //$("#createHotel").click(function(){
            $.ajax(
            {
            url: "http://localhost:8080/hotels/addRoom",//link assigned to method in RoomController
            method: "POST",//POST request
            dataType: 'json',//
            contentType: "application/json",
            data: JSON.stringify(room),
            cache: false,
            crossDomain: true,
            success: function(result) { },
            })
    }
}