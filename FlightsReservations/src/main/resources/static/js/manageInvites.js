import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken, parseJwt } from "./securityStuff.js";

var getAllInvitesLink = "/flightReservations/getInvites";
var acceptInviteLink = "/flightReservations/confirmInvite";
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
        var flights = invite.reservation.flights;
        html += `
            <tr>
                <td> ${invite.sender} </td>
                <td> ${invite.acceptDeadline} </td>
                <td> ${flights[0].startAirport} </td> // start destination
                <td> ${flights[flights.length - 1].endAirport} </td> // end destination
                
                <td> <button type="button" class="btn btn-success" onclick=createPassportModal("${invite.reservation.id}") >Accept invite</button>  </td>
                <td> <button type="button" class="btn btn-danger" onclick=declineInvite("${invite.reservation.id}")>Decline invite</button> </td></tr>
            </tr>`;        
	});
	if (html) {
		$("#invitesTable tbody").html(html);
		$("#invitesTable").show();
	}
}

window.createPassportModal = createPassportModal;
function createPassportModal(inviteId) {
    inviteId = parseInt(inviteId);
    
   var modalBody = "<div class=\"modal-body\">" +
   "<label for=\"passport\">Passport number</label>" +
   "<input type=\"text\" class=\"form-control\" id=\"passport"+inviteId+"\"></div>";
   var modalFooter = "<div class=\"modal-footer\">" +
   "<button type=\"button\" class=\"btn btn-primary\" onclick=acceptInvite("+inviteId+")>Save changes</button>" +
   "<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\" onclick=refreshModal()>Close</button></div>";
   /*$(".modal-body").append(modalBody);
   $(".modal-footer").append(modalFooter);*/
    document.getElementById("passportModal").setAttribute("aria-hidden", "false");
    
    
    $(".modal-content").append(modalBody);
    $(".modal-content").append(modalFooter);
    $('#passportModal').modal('show');
}

window.refreshModal = refreshModal;
function refreshModal() {
    $("#passportModal").modal("hide");
    $(".modal-body").remove();
    $(".modal-footer").remove();
    //console.log(document);
}

window.acceptInvite = acceptInvite;
function acceptInvite(inviteId) {
    var passportNumber = document.getElementById("passport" + inviteId).value;
    
    $.ajax({
		url: acceptInviteLink + "/" + inviteId + "/" + passportNumber,
		method: "PUT",
        headers: {"Authorization": "Bearer " + token},
		success: function() {
            refreshModal();
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
		method: "PUT",
        headers: {"Authorization": "Bearer " + token},
		success: function() {
            refreshModal();
            toastr.success("Invite decline successful");
			getAllInvites();
		},	
		error: function(error) {
            toastr.error("Invite decline unsuccessful");
            console.log(error);
		}
	});
}