var searchUrl = "http://localhost:8080/hotels/searchRooms";
var mapa;
var emailSelect;

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

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
	var hotel =
		 {
			name 		: "",
			city 		: "",
			state		: "",
			averageScore: ""
		 }
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
        url: "http://localhost:8080/hotels/searchRooms" + "?type=" + hotel.name + "&overnightStay=" + hotel.city + "&averageScore=" + hotel.state + "&searchRoom=", //+ myForm.searchHotel.value
        method: "GET",				
        dataType: "json",
        contentType: "application/json",
        headers: { "Authorization": "Bearer " + token}, 
        success: function(hotels) {
            display_search_results(hotels);
        }, error: function(error) {
            console.log(error);
        }
    });
}

function display_search_results(cars) {
    var text = "<div id=\"foundRooms\"><h2>Search results: <h2><br>";
    for (car of cars) {
        text += "<h3>"+ " " + car.manufacturer + " " + car.name + " " + car.color + " " + car.yearOfManufacture + " " + car.pricePerHour + "</h3><br>";
    }
    text += "</div>";


    var tabela = document.getElementById("tabela");
    tabela.innerHTML = "<tr><td>My"+text+"</td></tr>";
    document.body.appendChild(tabela);
}

// allow only numbers to be entered where required
function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    // at most 4 digits
    evt.target.value = evt.target.value.slice(0, 3);
    return true;
}