var searchUrl = "http://localhost:8080/racss/searchCars";
var mapa;
var emailSelect;

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

$(document).ready(function(){
    $("#carSearchForm").on('submit', function(e) {
        e.preventDefault();
        
        var name = $("#name").val();
        var yearOfManufacture = $("#yearOfManufacture").val();
        if (yearOfManufacture == "") {
            yearOfManufacture = 0;
        }
        var manufacturer = $("#manufacturer").val();
    
        $("#foundCars").remove();
        $.ajax({
            url: searchUrl + "?name=" + name + "&manufacturer=" + manufacturer + "&yearOfManufacture=" + yearOfManufacture,
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            success: function(result) {
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
        text += "<h3>"+ " " + car.manufacturer + " " + car.name + " " + car.color + " " + car.yearOfManufacture + " " + car.pricePerHour + "</h3><br>";
    }
    text += "</div>";
    $(document.documentElement).append(text);
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