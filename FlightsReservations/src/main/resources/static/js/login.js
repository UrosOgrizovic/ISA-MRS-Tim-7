var loginLink1 = "/users/login";
var loginLink2 = "/auth/login";

$(document).ready(function() {
    // no user should be logged in while on this page
    
    var temp = localStorage.getItem("lastPage");
    localStorage.clear();
    localStorage.setItem("lastPage", temp);

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
                //console.log(result.email);
                localStorage.setItem("token", result.accessToken);
                localStorage.setItem("email", result.email);
                localStorage.setItem("expiresIn", result.expiresIn);
                localStorage.setItem("firstName", result.firstName);
                localStorage.setItem("type", result.type);
                
                if (result.type == "AA") {
                	location.replace("/html/adminHomepage.html");
                } else if (result.type == "RA") {
                	location.replace("/racs_admin");
                } else if (result.type == "HA") {
                	location.replace("/hotel_admin")
                } else {
                	location.replace("/html/userHomepage.html");
                }
                
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Wrong email/password</h3>");
                console.log(error);
            }
        });

    });
});