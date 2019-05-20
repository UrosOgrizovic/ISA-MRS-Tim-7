
import {loadNavbar} from "./navbar.js";
import { checkRoleFromToken } from "./securityStuff.js";

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

if (!checkRoleFromToken(token, "ROLE_USER")) history.go(-1);

$(document).ready(function(){
    loadNavbar('userHomepageNavItem');
});


