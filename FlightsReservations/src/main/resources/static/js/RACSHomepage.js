var getAllLink = "/racss/getAll";
var searchRACSByNameLink = "/racss/searchRACS";

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
            success: function(racss) {
                displayRACSS(racss);
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
                console.log(error);
            }
        });
        

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
            var carText = car.manufacturer + " " + car.name + " " + car.color + " " + car.yearOfManufacture
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

        
        text += "</tr>";
    }
    text += "</tbody></table>";
    $(document.documentElement).append(text);
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
        success: function(racss) {
            
            displayRACSS(racss);
            
        }, error: function(error) {
            $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
            console.log(error);
        }
    });   
}