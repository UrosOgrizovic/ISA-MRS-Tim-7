//import {parseJwt} from "/.securityStuff.js";
export function loadNavbar(id) {
    $("#nav-placeholder").load("navbar.html", function() {
        document.getElementById(id).classList.add('active');
        /*
        var token = localStorage.getItem("token");
        if (token != null) {
            token = parseJwt(localStorage.getItem("token"));
            document.getElementById("profileHomepageNavItem").innerHTML = token.sub;
        } else {
            document.getElementById("profileHomepageNavItem").display = "none";
        }
         */
    });
}