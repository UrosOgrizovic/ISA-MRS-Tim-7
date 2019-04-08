var getAllLink = "/hotels/getAll";

$(document).ready(function(){
    $("#viewAllHotels").on('click', function(e) {
        
        e.preventDefault();
        
        
        $("#all").remove();
        $("#error").remove();
        console.clear();
        
        
        $.ajax({
            url: getAllLink,
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            success: function(hotels) {
                var text = "<table id=\"all\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
                text += "<thead>";
                text += "<tr>";
                text += "<th>Name</th>";
                text += "<th>Address</th>";
                text += "<th>Description</th>";
                text += "<th>Pricelist</th>";
                text += "<th>Rooms</th>";
                text += "<th>Overall rating</th>";
                text += "</tr>";
                text += "</thead><tbody>";
                
                for (var hotel of hotels) {
                    text += "<tr>";
                    text += "<td>" + hotel.name + "</td>";
                    text += "<td>" + hotel.address + "</td>";
                    text += "<td>" + hotel.promoDescription + "</td>";
                    

                    text += "<td><br><div class=\"dropdown\">";
                    text += "<button class=\"btn btn-secondary dropdown-toggle\" type=\"button\" id=\"dropdownPricelistButton\" data-toggle=\"dropdown\"aria-haspopup=\"true\" aria-expanded=\"false\">Pricelist</button>";
                    text += "<div class=\"dropdown-menu\" aria-labelledby=\"dropdownPricelistButton\">";
                    for (var item of hotel.servicesPriceList) {
                        var textItem = JSON.stringify(item, null, 4);
                        textItem = textItem.substring(1, textItem.length - 1);
                        textItem = textItem.split('"').join('');
                        textItem += "$";
                        text += "<a class=\"dropdown-item\" href=\"#\">" + textItem + "</a>";
                    }
                    text += "</div></div></td>";

                    
                    text += "<td><br><div class=\"dropdown\">";
                    text += "<button class=\"btn btn-secondary dropdown-toggle\" type=\"button\" id=\"dropdownRoomsButton\" data-toggle=\"dropdown\"aria-haspopup=\"true\" aria-expanded=\"false\">Rooms</button>";
                    text += "<div class=\"dropdown-menu\" aria-labelledby=\"dropdownRoomsButton\">";
                    //TODO: ISPIS SOBE
                    for (var room of hotel.roomConfiguration) {
                        var carText = "#" + room.id + " rating: " + room.overallRating + " overnight: " + room.overNightStay + "$";
                        text += "<a class=\"dropdown-item\" href=\"#\">" + carText + "</a>";
                    }
                    text += "</div></div></td>";

                    text += "<td>" + hotel.overallRating + "</td>";
                    
                    text += "</tr>";
                }
                text += "</tbody></table>";
                $(document.documentElement).append(text);
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
                console.log(error);
            }
        });
        

    });
});

