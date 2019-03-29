
function validateInputs(myForm)
{
    console.log(myForm);

    if(myForm.name.value.trim()=="")
    {
        alert("You must enter a Hotel's name!");
        return false;
    }
    else//in case of wrong type (add later)
    {

    }

    if(myForm.address.value.trim()=="")
    {
        alert("You must enter a Hotel's address!");
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
function createHotel()
{
    myForm = document.getElementById("createHotelForm");
    {
        var hotel =
                {
                    id : 1,//Fix later
                    name : myForm.name.value,
                    address : myForm.address.value,
                    promoDescritpion : myForm.promoDescription.value,
                    hotelAdmin : null,//Object
                    roomConfiguration : null,//Set
                    overallRating : null,//double
                    additionalServices : null,//Set
                    logotPath : ""//String
                };
        //add image later
        //add administator later
        var hotel2 = JSON.stringify(hotel);
        console.log(hotel2);
         //$("#createHotel").click(function(){
            $.ajax(
            {
            url: "http://localhost:8080/hotel/create",//link assigned to method in HotelController
            method: "POST",//POST request
            dataType: 'json',//
            contentType: "application/json",
            data: hotel2,
            cache: false,
            crossDomain: true,
            success: function(result) {window.alert("Poslato!"); },
            error: function(jqXHR, textStatus, errorThrown) {
                alert('An error occurred... Look at the console (F12 or Ctrl+Shift+I, Console tab) for more information!');
            }
         })
    } //}
}