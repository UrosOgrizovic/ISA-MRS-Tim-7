var loginLink1 = "/users/login";
var loginLink2 = "/auth/login";

$(document).ready(function() {
    // no user should be logged in while on this page
    localStorage.removeItem("token");
    localStorage.removeItem("email");
    localStorage.removeItem("expiresIn");
    localStorage.setItem("loggedIn", false);

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
                localStorage.setItem("token", result.accessToken);
                localStorage.setItem("email", result.email);
                localStorage.setItem("expiresIn", result.expiresIn);
                localStorage.setItem("loggedIn", true);
                location.replace("/html/userHomepage.html");        
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Wrong email/password</h3>");
                console.log(error);
            }
        });

    });
});