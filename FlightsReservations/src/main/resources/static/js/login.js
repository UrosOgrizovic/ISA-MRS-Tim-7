$(document).ready(function(){
    $("#login_form").on('submit', function(e) {
        e.preventDefault();
        

        email = document.getElementById("email").value;
        password = document.getElementById("password").value;

        $("#loggedIn").remove();
        $("#error").remove();
        console.clear();
        $.ajax({
            url: "http://localhost:8080/users/login" + "?email=" + email + "&password=" + password,
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            success: function(result) {
                $(document.documentElement).append("<h3 id=\"loggedIn\">Logged in successfully as user with first name <strong>" + result.firstName 
                + "</strong> and last name <strong>" + result.lastName + "<strong></h3>");
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Wrong email/password</h3>");
                console.log(error);
            }
        });

    });
});