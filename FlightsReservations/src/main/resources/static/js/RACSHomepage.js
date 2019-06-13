import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken, parseJwt } from "./securityStuff.js";
var getAllLink = "/racss/getAll";
var searchRACSByNameLink = "/racss/searchRACS";
var rateLink = "/companies/rate";

window.rateRACS = rateRACS;
window.searchRACSByName = searchRACSByName;

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
}

// exposing function to window object, because each module creates a scope to avoid name collisions
window.searchRACSByName = searchRACSByName;


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
                displayRACSS(racss);
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
                console.log(error);
            }
        });
    });
    
    loadNavbar('RACSHomepageNavItem');

    $(document).on('click', '.star', function(el) {
        rateRACS(el.target);

    });
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
    text += "<th>Average rating</th>";
    text += "<th>Your rating</th>";
    
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
        for (var car of racs.cars) {
            var carText = car.manufacturer + " " + car.name + " " + car.color + " " + car.yearOfManufacture + " " + car.pricePerHour;
            text += "<a class=\"dropdown-item\" href=\"#\">" + carText + "</a>";
        }
        text += "</div></div></td>";

        text += "<td><br><div class=\"dropdown\">";
        text += "<button class=\"btn btn-secondary dropdown-toggle\" type=\"button\" id=\"dropdownPricelistButton\" data-toggle=\"dropdown\"aria-haspopup=\"true\" aria-expanded=\"false\">Pricelist</button>";
        text += "<div class=\"dropdown-menu\" aria-labelledby=\"dropdownPricelistButton\">";
        for (var item of racs.pricelist) {
            var textItem = JSON.stringify(item, null, 4);
            textItem = textItem.substring(1, textItem.length - 1);
            textItem = textItem.split('"').join('') + "$";
            text += "<a class=\"dropdown-item\" href=\"#\">" + textItem + "</a>";
        }
        text += "</div></div></td>";

        text += "<td><br><div class=\"dropdown\">";
        text += "<button class=\"btn btn-secondary dropdown-toggle\" type=\"button\" id=\"dropdownBOButton\" data-toggle=\"dropdown\"aria-haspopup=\"true\" aria-expanded=\"false\">Branch offices</button>";
        text += "<div class=\"dropdown-menu\" aria-labelledby=\"dropdownBOButton\">";
        for (var bo of racs.branchOffices) {
            text += "<a class=\"dropdown-item\" href=\"#\">" + bo + "</a>";
        }
        text += "</div></div></td>";

        // Average rating

        text += "<td>";
        var avgstar5id = "avgstar5" + racs.name;
        var avgstar4id = "avgstar4" + racs.name;
        var avgstar3id = "avgstar3" + racs.name;
        var avgstar2id = "avgstar2" + racs.name;
        var avgstar1id = "avgstar1" + racs.name;
        /* each radio group has to have a different name, otherwise only one 
        one of them will be checked
         */
        var groupName = "avg" + racs.name;
        text += "<div class=\"rate\">" +
        "<input type=\"radio\" id=\""+avgstar5id+"\" name=\""+groupName+"\" value=\"5\" />" + 
        "<label for=\""+avgstar5id+"\">5 stars</label>" + 
        "<input type=\"radio\" id=\""+avgstar4id+"\" name=\""+groupName+"\" value=\"4\" />" +
        "<label for=\""+avgstar4id+"\">4 stars</label>" +
        "<input type=\"radio\" id=\""+avgstar3id+"\" name=\""+groupName+"\" value=\"3\" />" +
        "<label for=\""+avgstar3id+"\">3 stars</label>" +
        "<input type=\"radio\" id=\""+avgstar2id+"\" name=\""+groupName+"\" value=\"2\" />" +
        "<label for=\""+avgstar2id+"\">2 stars</label>" +
        "<input type=\"radio\" id=\""+avgstar1id+"\" name=\""+groupName+"\" value=\"1\" />" +
        "<label for=\""+avgstar1id+"\">1 star</label>" +
        "<div id=\"avgscore"+racs.name+"\"></div>" +
        "</div>";        

        text += "</td>"; 

        // Your rating

        text += "<td>";
        var star5id = "star5" + racs.name;
        var star4id = "star4" + racs.name;
        var star3id = "star3" + racs.name;
        var star2id = "star2" + racs.name;
        var star1id = "star1" + racs.name;
        /* each radio group has to have a different name, otherwise only one 
        one of them will be checked
         */
        var groupName = "rate" + racs.name;

        text += "<div class=\"rate\">" +
        "<input class=\"star\" type=\"radio\" id=\""+star5id+"\" name=\""+groupName+"\"  value=\"5\" />" + 
        "<label for=\""+star5id+"\">5 stars</label>" + 
        "<input class=\"star\" type=\"radio\" id=\""+star4id+"\" name=\""+groupName+"\" value=\"4\" />" +
        "<label for=\""+star4id+"\">4 stars</label>" +
        "<input class=\"star\" type=\"radio\" id=\""+star3id+"\" name=\""+groupName+"\" value=\"3\" />" +
        "<label for=\""+star3id+"\">3 stars</label>" +
        "<input class=\"star\" type=\"radio\" id=\""+star2id+"\" name=\""+groupName+"\" value=\"2\" />" +
        "<label for=\""+star2id+"\">2 stars</label>" +
        "<input class=\"star\" type=\"radio\" id=\""+star1id+"\" name=\""+groupName+"\" value=\"1\" />" +
        "<label for=\""+star1id+"\">1 star</label>" +
        "</div>";
    
        text += "</td>"; 

        text += "</tr>";
    }
    text += "</tbody></table>";
    $(document.documentElement).append(text);
    
    for (var racs of racss) {        
        displayRACSRating(racs);
    }
}

function displayRACSRating(racs) {
    if (racs.averageScore >= 4.5) {
        document.getElementById("avgstar5"+racs.name).checked = true;
    } else if (racs.averageScore >= 3.5) {
        document.getElementById("avgstar4"+racs.name).checked = true;
    } else if (racs.averageScore >= 2.5) {
        document.getElementById("avgstar3"+racs.name).checked = true;
    } else if (racs.averageScore >= 1.5) {
        document.getElementById("avgstar2"+racs.name).checked = true;
    } else {
        document.getElementById("avgstar1"+racs.name).checked = true;
    }
    document.getElementById("avgscore"+racs.name).innerHTML = "("+racs.averageScore.toString().match(/^-?\d+(?:\.\d{0,2})?/)[0]+")";
}

function rateRACS(el) {
    
    var arr = el.id.split("star");
    var ratingRACSName = arr[1].split("");
    
    var obj = {};
    obj.name = arr[1].substring(1);
    obj.averageScore = parseFloat(ratingRACSName[0]);

    $.ajax({
        url: rateLink,
        method: "PUT",
        data: JSON.stringify(obj),
        contentType: "application/json",
        dataType: "json",
        headers: { "Authorization": "Bearer " + token}, 
        success: function(racs) {
            displayRACSRating(racs);
        },
        error: function(err) {
            console.log(err);
        }
    })
}

function searchRACSByName() {
    $("#all").remove();
    $("#error").remove();
        
    var RACSName = $("#searchRACSName").val();
    $.ajax({
        url: searchRACSByNameLink + "/" + RACSName,
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        data: {},
        headers: { "Authorization": "Bearer " + token}, 
        success: function(racss) {
            displayRACSS(racss);
        }, error: function(error) {
            $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
            console.log(error);
        }
    });   
}