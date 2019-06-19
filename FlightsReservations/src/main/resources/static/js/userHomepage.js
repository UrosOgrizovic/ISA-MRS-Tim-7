
import {loadNavbar} from "./navbar.js";


var token = localStorage.getItem("token");


$(document).ready(function(){
    loadNavbar('userHomepageNavItem');
    location.replace("/html/airlinesHomepage.html")
});


