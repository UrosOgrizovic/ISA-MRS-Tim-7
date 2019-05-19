import { checkRoleFromToken } from "./securityStuff.js";
var addRACSLink = "/racss/add";

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

if (!checkRoleFromToken(token, "ROLE_ADMIN")) history.go(-1);
$(document).ready(function(){
    
    $("#createRACSForm").on('submit', function(e) {
        e.preventDefault();
        
        racs = {};
        racs.name = document.getElementById("name").value;
        racs.promoDescription = document.getElementById("description").value;
        racs.pricelist = [];
        racs.cars = [];
        racs.branchOffices = [];
        racs.latitude = document.getElementById("latitude").value;
        racs.longitude = document.getElementById("longitude").value;
        
        $("#addedSuccessfully").remove();
        $("#error").remove();
        console.clear();
        
        $.ajax({
            url: addRACSLink,
            method: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(racs),
            headers: { "Authorization": "Bearer " + token}, 
            success: function(result) {
                console.log(result);
                $(document.documentElement).append("<h3 id=\"addedSuccessfully\">Rent-a-car service addedd successfully</h3>");
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
                console.log(error);
            }
        });

    });
});
