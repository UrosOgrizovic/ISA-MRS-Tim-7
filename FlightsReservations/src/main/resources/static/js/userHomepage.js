
import {loadNavbar} from "./navbar.js";


var token = localStorage.getItem("token");
var msg = localStorage.getItem("successMessageForToastr");

$(document).ready(function(){
    if (msg != "" && msg != null) {
        toastr.success(msg);
    }
    loadNavbar('userHomepageNavItem');
});


