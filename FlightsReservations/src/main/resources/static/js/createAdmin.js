var my_url = "http://localhost:8080";

function validate_inputs(myForm)
{
    console.log(myForm);
    //alert(""+myForm.adminType.type);
    if(myForm.adminType.value==0)
    {
    	alert("You must select Administrator's type!");
        return false;
    }
    else if(myForm.adminType.value==1)
    {
    	my_url += "/airlineAdministrator";
    }
    else if(myForm.adminType.value==2)
    {
    	my_url += "/hotelAdministrator";
    }
    else if(myForm.adminType.value==3)
    {
    	my_url += "/RACSAdministrator";
    }
    
    if(myForm.firstName.value.trim()=="")
    {
        alert("You must enter Administrator's fist name!");
        return false;
    }

    if(myForm.lastName.value.trim()=="")
    {
    	alert("You must enter Administrator's last name!");
        return false;
    }
    
    if(myForm.email.value.trim()=="")
    {
    	alert("You must enter Administrator's email address!");
        return false;
    }
    else//in case of wrong type (add later)
    {

    }

    
    if(myForm.password.value.trim()=="")
    {
    	alert("You must enter Administrator's password!");
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

function create_admin()
{
    myForm = document.getElementById("createAdminForm");
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
                    hotelAdmin : null,//Object
                    roomConfiguration : null,//Set
                    overallRating : null,//double
                    additionalServices : null,//Set
                    logotPath : ""//String
                };
        //add image later
        //add administator later

         //$("#createHotel").click(function(){
            $.ajax(
            {
            url: my_url + "/create",//link assigned to method in HotelController
            method: "POST",//POST request
            dataType: 'json',//
            contentType: "application/json",
            data: JSON.stringify(hotel),
            cache: false,
            crossDomain: true,
            success: function(result) {window.alert("Poslato!"); },
            })
    }
}