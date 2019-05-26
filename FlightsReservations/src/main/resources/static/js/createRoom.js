
function validate_inputs(myForm)
{
    console.log(myForm);

    if(myForm.name.value.trim()=="")
    {
    	alert("You must enter Room's name!");
    	return false;
    }
    
    if(isInteger(myForm.number.value))
    {
        alert("You must Room number must be a number!");
        return false;
    }
    else//in case of wrong type (add later)
    {

    }
    
    if(isInteger(myForm.floor.value))
    {
        alert("You must Room number must be a number!");
        return false;
    }
    else//in case of wrong type (add later)
    {

    }
    
    if(isInteger(myForm.overnightStay.value))
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
    myForm = document.getElementById("createRoomForm");
    if(!validate_inputs(myForm))
    {
        return;
    }
    else
    {
        var room =
                {
        			name : myForm.name.value,
                    number : myForm.number.value,
                    floor : myForm.floor.value,
                    overnightStay : myForm.overnightStay.value,
                    type : myForm.type.value,
                    overallRating : 0.0
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
            data: JSON.stringify(hotel),
            cache: false,
            crossDomain: true,
            success: function(result) { },
            })
    }
}