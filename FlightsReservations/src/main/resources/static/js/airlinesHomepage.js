var getAllLink = "/airlines/getAll";

$(document).ready(function(){
    $("#viewAllAirlines").on('click', function(e) {
        
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
            success: function(airlines) {
                var text = "<table id=\"all\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
                text += "<thead>";
                text += "<tr>";
                text += "<th>Name</th>";
                text += "<th>Location</th>";
                text += "<th>Description</th>";
                text += "</tr>";
                text += "</thead><tbody>";
                
                for (var al of airlines) {
                    
                    text += "<tr>";
                    text += "<td>" + al.name + "</td>";
                    text += "<td>" + al.location + "</td>";
                    text += "<td>" + al.promoDescription + "</td>";
                                        
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

