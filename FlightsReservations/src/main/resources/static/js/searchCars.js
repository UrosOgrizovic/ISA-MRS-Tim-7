import {loadNavbar} from "./navbar.js"; 
var searchUrl = "http://localhost:8080/racss/searchCars";
var mapa;
var emailSelect;


window.mapa = mapa;
window.emailSelect = emailSelect;
window.isNumber = isNumber;

var token = localStorage.getItem("token");

// everyone can search, so there's no role-checking here

$(document).ready(function(){
    
    $("#carSearchForm").on('submit', function(e) {
        e.preventDefault();
        var racsName = $("#racsName").val();
        var name = $("#name").val();
        var yearOfManufacture = $("#yearOfManufacture").val();
        if (yearOfManufacture == "") {
            yearOfManufacture = 0;
        }
        var manufacturer = $("#manufacturer").val();
    
        $("#foundCars").remove();
        $.ajax({
            url: searchUrl + "?racsName=" + racsName + "&name=" + name + "&manufacturer=" + manufacturer + "&yearOfManufacture=" + yearOfManufacture,
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            headers: { "Authorization": "Bearer " + token}, 
            success: function(result) {
                if (result != null && result.length > 0) {
                    displaySearchResults(result);
                } else {
                    toastr.info("No search results to display");
                }
                
            }, error: function(error) {
                toastr.error("Could not display search results");
                console.log(error);
            }
        });
    });
    
    loadNavbar('RACSHomepageNavItem');
    
});

function displaySearchResults(cars) {
    var text = "<table id=\"foundCars\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
    text += "<thead>";
    text += "<th>Manufacturer</th><th>Name</th><th>Color</th><th>Year of manufacture</th><th>Price per hour</th><th>RACS branch office name</th>";
    text += "</thead><tbody>";
    for (var car of cars) {
        text += "<tr><td>" + car.manufacturer + "</td><td>" + car.name + "</td><td>" + car.color + "</td><td>" + car.yearOfManufacture + "</td><td>" + car.pricePerHour +"</td><td>"+car.racsBranchOfficeName+"</td>" + "</tr>";
    }
    text += "</tbody></table>";
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