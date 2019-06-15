import {loadNavbar} from "./navbar.js";

var loginLink2 = "/auth/login";

var user = {};

$(document).ready(function() {
    // no user should be logged in while on this page
    
    var temp = localStorage.getItem("lastPage");
    localStorage.clear();
    localStorage.setItem("lastPage", temp);

    loadNavbar('loginNavItem');

    $("#login_form").on('submit', function(e) {
        e.preventDefault();
        
        user = {};
        user.email = document.getElementById("email").value;
        user.password = document.getElementById("password").value;

        $("#error").remove();
        console.clear();
        $.ajax({
            url: loginLink2,
            method: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(user),
            success: function(result) {
                //console.log(result);
                console.log(result.accessToken);
                localStorage.setItem("token", result.accessToken);
                localStorage.setItem("email", result.email);
                localStorage.setItem("expiresIn", result.expiresIn);
                localStorage.setItem("firstName", result.firstName);
                if (localStorage.getItem("lastPage") == null || localStorage.getItem("lastPage") == "null")
                    location.replace("/html/userHomepage.html");
                else 
                    location.replace(localStorage.getItem("lastPage"))
            }, error: function(error) {
                toastr.error("Wrong email or password");
                console.log(error);
            }
        });

    });
});