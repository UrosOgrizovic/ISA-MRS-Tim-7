var loginLink1 = "/users/login";
var loginLink2 = "/auth/login";

$(document).ready(function(){
    $("#login_form").on('submit', function(e) {
        e.preventDefault();
        
        user = {};
        user.email = document.getElementById("email").value;
        user.password = document.getElementById("password").value;

        $("#loggedIn").remove();
        $("#error").remove();
        console.clear();
        $.ajax({
            url: loginLink2,
            method: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(user),
            success: function(result) {
                console.log(result);
                $(document.documentElement).append("<h3 id=\"loggedIn\">Logged in successfully as user with access token <strong>" + result.accessToken 
                + "</strong> that expires in <strong>" + result.expiresIn + "<strong></h3>");
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Wrong email/password</h3>");
                console.log(error);
            }
        });

    });
});