var my_url = "http://localhost:8080";

var token = localStorage.getItem("token");

function validate_inputs(myForm)
{
    if (!localStorage.getItem("loggedIn")) {
        location.replace("/html/login.html");
    }
    console.log(myForm);
    //alert(""+myForm.adminType.type);
    if(myForm.adminType.value==0)
    {
    	alert("You must select Administrator's type!");
        return false;
    }
    else if(myForm.adminType.value==1)
    {
    	my_url += "/airlineAdmin";
    }
    else if(myForm.adminType.value==2)
    {
    	my_url += "/hotelAdmin";
    }
    else if(myForm.adminType.value==3)
    {
    	my_url += "/racsAdmin";
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
        var admin =
                {
                    id : 1,//Fix later
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
            url: my_url + "/create",//link assigned to method in HotelController
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