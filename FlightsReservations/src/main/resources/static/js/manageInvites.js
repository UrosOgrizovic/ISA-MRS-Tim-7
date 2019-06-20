import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken, parseJwt } from "./securityStuff.js";

var getAllInvitesLink = "/fligthReservations/getInvites";
var acceptInviteLink = "/flightReservations/acceptInvite";
var declineInviteLink = "/flightReservations/declineInvite";

loadNavbar('invitesNavItem'); 

var email = localStorage.getItem("email");
var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");



$(document).ready(function(){
	getAllInvites();
});

function getAllInvites() {
    $.ajax({
		url: getAllInvitesLink,
		method: "GET",
        data: "json",
        headers: {"Authorization": "Bearer " + token},
		success: function(invites) {
			if (invites.length > 0)
				displayInvites(invites);
			else
				toastr.info("No invites to display.");
		},	
		error: function(error) {
            toastr.error("Getting invites unsuccessful");
            console.log(error);
		}
	});
}

function displayInvites(invites) {
    var html = "";
	invites.forEach(function(invite){
		html += 
		`
        <tr>
            <td> </td>
			<td> ${invite.firstName} </td>
			<td> ${invite.flights[0].startAirport} </td> // start destination
            <td> ${invite.flights[flights.length - 1].endAirport} </td> // end destination
            <td> <button type="button" class="btn btn-success" onclick=acceptInvite("${invite.id}")>Accept invite</button>  </td>
			<td> <button type="button" class="btn btn-danger" onclick=declineInvite("${invite.id}")>Decline invite</button> </td></tr>
		</tr>
		`;
	});
	if (html) {
		$("#invitesTable tbody").html(html);
		$("#invitesTable").show();
	}
}

window.acceptInvite = acceptInvite;
function acceptInvite(id) {
    $.ajax({
		url: acceptInviteLink + "/" + id,
		method: "GET",
        data: "json",
        headers: {"Authorization": "Bearer " + token},
		success: function() {
            toastr.success("Invite accept successful")
			getAllInvites();
		},	
		error: function(error) {
            toastr.error("Invite accept unsuccessful");
            console.log(error);
		}
	});
}

window.declineInvite = declineInvite;
function declineInvite(id) {
    $.ajax({
		url: declineInviteLink + "/" + id,
		method: "GET",
        data: "json",
        headers: {"Authorization": "Bearer " + token},
		success: function() {
            toastr.success("Invite decline successful");
			getAllInvites();
		},	
		error: function(error) {
            toastr.error("Invite decline unsuccessful");
            console.log(error);
		}
	});
}