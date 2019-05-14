
var token = localStorage.getItem("token");

function validate_inputs(myForm)
{
    if (!localStorage.getItem("loggedIn")) {
        location.replace("/html/login.html");
    }
    console.log(myForm);
    if(myForm.name.value.trim()=="")
    {
        alert("You must enter Airline's name!");
        return false;
    }
    else//in case of wrong type (add later)
    {

    }
    if(myForm.latitude.value.trim()=="")
    {
        alert("You must enter Airline's latitude!");
        return false;
    }

    if(myForm.longitude.value.trim()=="")
    {
        alert("You must enter Airline's longitude!");
        return false;
    }
    
    if(myForm.promoDescription.value.trim()=="")
    {
        alert("You must enter Airline's location!");
        return false;
    }
    
    return true;
}

function create_airline()
{
    myForm = document.getElementById("createAirlineForm");
    if(!validate_inputs(myForm))
    {
        return;
    }
    else
    {
        var airline =
                {
                    id : 1,//Fix later
                    name : myForm.name.value,
                    longitude : myForm.longitude.value,
                    latitude : myForm.latitude.value,
                    promoDescription : myForm.promoDescription.value,
                    averageScore: 0,//default value
                    numberOfVotes: 0//default value
                    /*//this is a DTO => not all field are necessary
                    airlineAdmin : null,//Object
                    overallRating : null,//double
                    additionalServices : null,//Set
                    */
                };
        //TODO: add administator later

         //$("#createHotel").click(function(){
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
    }
}