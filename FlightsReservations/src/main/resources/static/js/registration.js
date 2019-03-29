$(document).ready(function(){
    $("#registration_form").on('submit', function(e) {
        e.preventDefault();
       
        if($("#password").value != $("#confirmPassword").value) {
            alert("Error: Passwords don't match");
            $("#password").focus();
            return false;
          } else {
            var user = {};
            user.firstName = document.getElementById("firstName").value;
            user.lastName = document.getElementById("lastName").value;
            user.email = document.getElementById("email").value;
            user.address = document.getElementById("address").value;
            user.phone = document.getElementById("phone").value;
            user.password = document.getElementById("password").value;

            console.log(user);
            $.ajax({
                url: "http://localhost:8080/users/add",
                method: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(user),
                success: function(result) {
                                    
                }, error: function(error) {
                    console.log(error);
                }
            });
            alert("Registration successful");
            return true;      
          }
    });
});