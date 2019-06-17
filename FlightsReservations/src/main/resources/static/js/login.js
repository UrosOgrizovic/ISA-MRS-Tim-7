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
                if (localStorage.getItem("lastPage") == null)
                    location.replace("/html/userHomepage.html");
                else 
                    location.replace(localStorage.getItem("lastPage"))
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Wrong email/password</h3>");
                console.log(error);
            }
        });

    });
});