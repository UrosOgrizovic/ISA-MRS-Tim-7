var searchUrl = "http://localhost:8080/racss/searchCars";
var mapa;
var emailSelect;

$(document).ready(function(){
    $("#carSearchForm").on('submit', function(e) {
        e.preventDefault();
        if (!validateInputs()) {
            alert("Inputs are invalid!");
            return;
        }
        var name = $("#name").val();
        var yearOfManufacture = $("#yearOfManufacture").val();
        var manufacturer = $("#manufacturer").val();
    
        $.ajax({
            url: searchUrl + "?name=" + name + "&manufacturer=" + manufacturer + "&yearOfManufacture=" + yearOfManufacture,
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function(result) {
                $("#foundCars").remove();
                displaySearchResults(result);
            }, error: function(error) {
                console.log(error);
            }
        });
    });
    
    
});

function displaySearchResults(cars) {
    var text = "<div id=\"foundCars\"><h2>Search results: <h2><br>";
    for (car of cars) {
        text += "<h3>"+ " " + car.manufacturer + " " + car.name + " " + car.yearOfManufacture + "</h3><br>";
    }
    text += "</div>";
    $(document.documentElement).append(text);
}

function validateInputs() {
	var ids = ["name", "yearOfManufacture", "manufacturer"];
	var flag = true;
	
	for (var i = 0; i < ids.length; i++)
		if ($("#"+ids[i]).val().trim() == "") {
			flag = false;
			break;
		}
	return flag;
}

function setInputs(){
	var key = emailSelect.val();
	$("#firstName").val(mapa[key].firstName);
	$("#lastName").val(mapa[key].lastName);
	$("#phone").val(mapa[key].phone);
	$("#address").val(mapa[key].address);
}
