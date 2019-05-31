import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken, parseJwt } from "./securityStuff.js";

var getRACSAverageRating = "/racsAdmin/getAverageRACSRating";

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

var email = parseJwt(token).sub;

// if user isn't admin
if (!checkRoleFromToken(token, "ROLE_ADMIN")) {
    history.go(-1);
}

$(document).ready(function(){
    loadNavbar('RACSHomepageNavItem');
    
    $("#viewRACSAverageRating").on('click', function(e) {
        
        e.preventDefault();
        
        $("#all").remove();
        $("#error").remove();
        
        $.ajax({
            url: getRACSAverageRating + "/" + email,
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            headers: { "Authorization": "Bearer " + token}, 
            success: function(averageRating) {
                $(document.documentElement).append("<h3 id=\"all\">"+averageRating+"</h3>");
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
                console.log(error);
            }
        });
    });
    
    

    $("#startDate").datepicker({
        uiLibrary: 'bootstrap'
    });
    $("#endDate").datepicker({
        uiLibrary: 'bootstrap'
    });

});