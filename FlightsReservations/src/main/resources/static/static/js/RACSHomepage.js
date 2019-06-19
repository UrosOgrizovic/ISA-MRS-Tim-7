import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken, parseJwt } from "./securityStuff.js";
var getAllLink = "/racss/getAll";


window.viewRACSBranchOffice = viewRACSBranchOffice;

var token = localStorage.getItem("token");


// everyone can access this page
if (token != null) {

    // if user isn't registered
    if (!checkRoleFromToken(token, "ROLE_USER")) {
        document.getElementById("reserveCar").style.display = "none";
    }

    // if user isn't admin
    if (!checkRoleFromToken(token, "ROLE_ADMIN")) {
        document.getElementById("editRACS").style.display = "none";
        document.getElementById("createRACS").style.display = "none";
    }

} else {
    document.getElementById("reserveCar").style.display = "none";
    document.getElementById("editRACS").style.display = "none";
    document.getElementById("createRACS").style.display = "none";
    document.getElementById("RACSReports").style.display = "none";
}

$(document).ready(function(){
    
    $("#viewAllRACS").on('click', function(e) {
        
        e.preventDefault();
        
        $("#all").remove();
        $("#error").remove();
        
        $.ajax({
            url: getAllLink,
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            headers: { "Authorization": "Bearer " + token}, 
            success: function(racss) {
                if (racss != null && racss.length > 0) 
                    displayRACSS(racss);
                else 
                    toastr.info("No rent-a-car services to display")
            }, error: function(error) {
                toastr.error("Could not display rent-a-car services");
                console.log(error);
            }
        });
    });
    
    loadNavbar('RACSHomepageNavItem');

    
});

function displayRACSS(racss) {
    var text = "<table id=\"all\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
    text += "<thead>";
    text += "<tr>";
    text += "<th>Name</th>";
    text += "<th>Longitude</th>";
    text += "<th>Latitude</th>";
    text += "<th>Description</th>";
    text += "<th>Cars</th>";
    text += "<th>Pricelist</th>";
    text += "<th>Branch offices</th>";
    text += "</tr>";
    text += "</thead><tbody>";
    
    for (var racs of racss) {
        text += "<tr>";
        text += "<td>" + racs.name + "</td>";
        text += "<td>" + racs.longitude + "</td>";
        text += "<td>" + racs.latitude + "</td>";
        text += "<td>" + racs.promoDescription + "</td>";
        
        text += "<td><br><div class=\"dropdown\">";
        text += "<button class=\"btn btn-secondary dropdown-toggle\" type=\"button\" id=\"dropdownCarsButton\" data-toggle=\"dropdown\"aria-haspopup=\"true\" aria-expanded=\"false\">Cars</button>";
        text += "<div class=\"dropdown-menu\" aria-labelledby=\"dropdownCarsButton\">";
        for (var branchOffice of racs.branchOffices) {
            for (var car of branchOffice.cars) {
                var carText = car.manufacturer + " " + car.name + " " + car.color + " " + car.yearOfManufacture + " " + car.pricePerHour;
                text += "<a class=\"dropdown-item\" href=\"#\">" + carText + "</a>";
            }
        }
        
        text += "</div></div></td>";

        text += "<td><br><div class=\"dropdown\">";
        text += "<button class=\"btn btn-secondary dropdown-toggle\" type=\"button\" id=\"dropdownPricelistButton\" data-toggle=\"dropdown\"aria-haspopup=\"true\" aria-expanded=\"false\">Pricelist</button>";
        text += "<div class=\"dropdown-menu\" aria-labelledby=\"dropdownPricelistButton\">";
        for (var item of racs.pricelist) {
            text += "<a class=\"dropdown-item\" href=\"#\">" + item.name + " = " + item.price + "$" + "</a>";
        }
        text += "</div></div></td>";

        text += "<td><br><div class=\"dropdown\">";
        text += "<button class=\"btn btn-secondary dropdown-toggle\" type=\"button\" id=\"dropdownBOButton\" data-toggle=\"dropdown\"aria-haspopup=\"true\" aria-expanded=\"false\">Branch offices</button>";
        text += "<div class=\"dropdown-menu\" aria-labelledby=\"dropdownBOButton\">";
        for (var bo of racs.branchOffices) {
            text += "<a class=\"dropdown-item\" href=\"#\" style=\"white-space: nowrap;\">" + bo.name + " <div onclick=\"viewRACSBranchOffice('"+bo.name+"')\" style=\"color: blue; font-weight: bold; font-size: 12px; display: inline; text-decoration: underline;\">GO</div>" + "</a>";
        }
        text += "</div></div></td>";

        text += "</tr>";
    }
    text += "</tbody></table>";
    $(document.documentElement).append(text);
    
    
}



function viewRACSBranchOffice(boName) {
    localStorage.setItem("branchOfficeName", boName);
    location.replace("RACSBranchOffice.html");
}