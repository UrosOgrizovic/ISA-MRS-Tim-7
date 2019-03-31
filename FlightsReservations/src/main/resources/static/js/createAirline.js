
function validate_inputs(myForm)
{
    console.log(myForm);

    if(myForm.name.value.trim()=="")
    {
        alert("You must enter Airline's name!");
        return false;
    }
    else//in case of wrong type (add later)
    {

    }

    if(myForm.location.value.trim()=="")
    {
        alert("You must enter Airline's location!");
        return false;
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
                    location : myForm.location.value,
                    promoDescription : myForm.promoDescription.value,
                    /*//this is a DTO => not all field are necessary
                    airlineAdmin : null,//Object
                    overallRating : null,//double
                    additionalServices : null,//Set
                    */
                    logoPath : ""//TODO: add uploaded image location
                };
        //TODO: add administator later

         //$("#createHotel").click(function(){
            $.ajax(
            {
            url: "http://localhost:8080/airlines/create",//link assigned to method in HotelController
            method: "POST",//POST request
            dataType: 'json',//
            contentType: "application/json",
            data: JSON.stringify(airline),
            cache: false,
            crossDomain: true,
            success: function(result) { },
            })
    }
}