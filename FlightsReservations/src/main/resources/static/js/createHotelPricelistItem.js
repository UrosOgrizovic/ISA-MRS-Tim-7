import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken } from "./securityStuff.js";

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

var hotel = "";
window.create_pricelistitem = create_pricelistitem;
$(document).ready(function ()
		{
			console.log(localStorage.getItem("email"));
			var email = localStorage.getItem("email");
	
			//var myForm = document.getElementById("createRoomForm");
            $.ajax(
                    {
        	            url: "http://localhost:8080/rooms/getHotel" + "?email=" + email, //+ "&createRoom=",//link assigned to method in AdminController
        	            method: "GET",				
        	            dataType: "json",
        	            contentType: "application/json",
        	            headers: { "Authorization": "Bearer " + token}, 
        	            success: function(result) {
        	                add_hotel(result);
        	            }, error: function(error) {
        	                console.log(error);
        	            }
                    })
            //console.log(admins);
		loadNavbar('hotelsHomepageNavItem');
		}
	);

function add_hotel(result)
{
	console.log(result);
	
	hotel = result;
	console.log(hotel);
}

function validate_inputs(myForm)
{
    if(myForm.name.trim()=="")
    {
        alert("You must select Hotel room's type!");
        return false;
    }
    if(isNaN( Number(myForm.price.value) ) )
    {
        alert("You must Room number must be a number!");
        return false;
    }
    return true;
}

function create_pricelistitem()
{
    var myForm = document.getElementById("createHotelPricelistItemForm");
    if(!validate_inputs(myForm))
    {
        return;
    }
    else
    {
        var hotelPricelistItem =
                {
                    name : myForm.name.value,
                    price : myForm.price.value,
                    hotelName : hotel
                    /*//this is a DTO => not all field are necessary
                    hotelName :  
                    roomConfiguration : null,//Set
                    
                    additionalServices : null,//Set
                    */
                };
        //add image later
        //add administator later

         //$("#createHotel").click(function(){
            $.ajax(
            {
            url: "http://localhost:8080/hotelPricelist/create",//link assigned to method in RoomController
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