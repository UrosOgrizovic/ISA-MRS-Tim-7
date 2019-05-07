
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

    if(myForm.address.value.trim()=="")
    {
        alert("You must enter Hotel's address!");
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

function create_hotel()
{
    myForm = document.getElementById("createHotelForm");
    if(!validate_inputs(myForm))
    {
        return;
    }
    else
    {
        var hotel =
                {
                    id : 1,//Fix later
                    name : myForm.name.value,
                    address : myForm.address.value,
                    promoDescription : myForm.promoDescription.value,
                    /*//this is a DTO => not all field are necessary
                    hotelAdmin : null,//Object
                    roomConfiguration : null,//Set
                    overallRating : null,//double
                    additionalServices : null,//Set
                    */
                    logoPath : ""//TODO: add uploaded image location
                };
        //add image later
        //add administator later

         //$("#createHotel").click(function(){
            $.ajax(
            {
            url: "http://localhost:8080/hotel/create",//link assigned to method in HotelController
            method: "POST",//POST request
            dataType: 'json',//
            contentType: "application/json",
            data: JSON.stringify(hotel),
            cache: false,
            crossDomain: true,
            success: function(result) { },
            })
    }
}