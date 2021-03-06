var my_url = "http://localhost:8080/Admin";

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");
var admin = {};

function validate_inputs(myForm)
{
    var admin_type;
    console.log(myForm);
    //alert(""+myForm.adminType.type);
    if(myForm.adminType.value==0)
    {
    	alert("You must select Administrator's type!");
        return false;
    }
    else if(myForm.adminType.value==1)
    {
    	my_url += "/airline/create";
    	admin_type = "AIRLINE";
    }
    else if(myForm.adminType.value==2)
    {
    	my_url += "/hotel/create";
    	admin_type = "HOTEL";
    }
    else if(myForm.adminType.value==3)
    {
    	my_url += "/racs/create";
    	admin_type = "RENTACAR";
    }
    else if(myForm.adminType.value==4)
    {
    	my_url += "/system/create";
    	admin_type = "SYSTEM";
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
        admin =
                {
                    //id : 1,//Fix later
                    firstName : myForm.firstName.value,
                    lastName : myForm.lastName.value,
                    email : myForm.email.value,
                    phone : myForm.phone.value,
                    address : myForm.address.value,
                    password : myForm.password.value,
                    picturePath : ""//TODO: add uploaded image location
                    /* // this is a DTO => not all fields are necessary
                    hotelSet : null
                     */
                };
        //add image later
        //add administator later

         //$("#createHotel").click(function(){
            $.ajax(
            {
            url: my_url,//link assigned to method in HotelController
            method: "POST",//POST request
            dataType: 'json',//
            contentType: "application/json",
            data: JSON.stringify(admin),
            cache: false,
            crossDomain: true,
            headers: { "Authorization": "Bearer " + token}, 
            success: function(result) { },
            })
    }
}