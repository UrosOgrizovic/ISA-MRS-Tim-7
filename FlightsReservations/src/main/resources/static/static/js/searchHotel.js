var searchUrl = "http://localhost:8080/hotels/searchHotels";
var mapa;
var emailSelect;

var token = localStorage.getItem("token");

// everyone can search, so there's no role-checking here

function validate_inputs(myForm)
{
	if(myForm.averageScore.value.trim()=="")
	{
		return true;
	}
	else if(isNaN(myForm.averageScore.value) )
	{
		return false;
	}
	else if(myForm.averageScore.value>=0 && myForm.averageScore.value<=5)//rating is between 0 and 5
	{
		return false;
	}
	else return true;
}

function search_hotel()
{
	var hotel;
	myForm = document.getElementById("hotelSearchForm");
	if(validate_inputs(myForm))
	{
		if(myForm.name.value.trim()!="")
		{
			hotel.name = myForm.name.value;
		}
		if(myForm.city.value.trim()!="")
		{
			hotel.city = myForm.city.value;
		}
		if(myForm.state.value.trim()!="")
		{
			hotel.state = myForm.state.value;
		}
		if(myForm.averageScore.value.trim()!="")
		{
			hotel.averageScore = myForm.averageScore.value;
		}
	}
    $.ajax({
        url: "http://localhost:8080/hotels/searchHotels"
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        headers: { "Authorization": "Bearer " + token}, 
        success: function(result) {
            displaySearchResults(result);
        }, error: function(error) {
            console.log(error);
        }
    });
}