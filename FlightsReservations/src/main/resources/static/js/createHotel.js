
var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

function validate_inputs(myForm)
{
    
    console.log(myForm);

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
    myForm = document.getElementById("createHotelForm");
    if(!validate_inputs(myForm))
    {
        return;
    }
    else
    {
    	console.log(myForm.city.value);
        var hotel =
                {
                    id : 1,//Fix later
                    name : myForm.name.value,
                    longitude : myForm.longitude.value,
                    latitude : myForm.latitude.value,
                    city : myForm.city.value,
                    promoDescription : myForm.promoDescription.value,
                    overallRating : 0.0,
                    numberOfVotes: 0.0
                    /*//this is a DTO => not all field are necessary
                    hotelAdmin : null,//Object
                    roomConfiguration : null,//Set
                    overallRating : null,//double
                    additionalServices : null,//Set
                    */
                };
        //add image later
        //add administator later

         //$("#createHotel").click(function(){
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
    }
}