var token = localStorage.getItem("token");

$(document).ready(function(){
    if (!localStorage.getItem("loggedIn")) {
        location.replace("/html/login.html");
    }
});