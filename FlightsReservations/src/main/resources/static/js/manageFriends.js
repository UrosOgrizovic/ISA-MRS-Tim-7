import {loadNavbar} from "./navbar.js"; 
loadNavbar('friendsPageLink'); 

var email = localStorage.getItem("email");
var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

var friends = []
var sentRequests = []
var recievedRequests = []
var currentTableId = null;
$("#resultsTable").hide();

$(document).ready(function(){
	$("#searchBtn").click(searchUsers);
	$("#tableSelector").change(changeTable);
	changeTable();	
});


window.searchUsers = searchUsers;
function searchUsers() {
	var query = $("#searchInput").val();
	if (!query) {
		toastr.info("Enter your query.");
		return;
	}
	
	console.log(query);
	$.ajax({
		url: "/users/searchUsers/" + query,
		method: "GET",
		data: "json",
		success: function(result) {
			if (result.length > 0)
				updateSearchResultsTable(result);
			else
				toastr.info("No results.");
		},	
		error: function(error) {
			toastr.error("Something is wrong with your request.(search)");
		}
	});
}


function getFriends() {
	$.ajax({
		url: "/users/getFriends",
		method: "GET",
		data: "json",
		success: function(result) {
			updateFriendsTable(result);
		},	
		error: function(error) {
			toastr.error("Something is wrong with your request.(get friends)");
		}
	});
}


function getSentRequests() {
	$.ajax({
		url: "/users/getSentFriendRequests",
		method: "GET",
		data: "json",
		success: function(result) {
			updateSentRequestsTable(result);
		},
		error: function(error) {
			toastr.error("Something is wrong with your request.(sent requests)");
		}
	});
}


function getRecievedRequests() {
	$.ajax({
		url: "/users/getRecievedFriendRequests",
		method: "GET",
		data: "json",
		success: function(result) {
			updateRecievedRequestsTable(result);
		},
		error: function(error) {
			toastr.error("Something is wrong with your request.(recieved requests)");
		}
	});
}

window.sendRequest = sendRequest;
function sendRequest(reciever) {
	$.ajax({
		url: "/users/friendRequest/send/" + reciever,
		method: "PUT",
		success: function() {
			getSentRequests();
			toastr.success("Friend request is sent.");
		},
		error: function(error) {
			toastr.error("Something is wrong with your request.(send request)");
		}
	});
}

window.approveRequest = approveRequest;
export function approveRequest(sender) {
	$.ajax({
		url: "/users/friendRequest/approve/" + sender,
		method: "PUT",
		success: function() {
			getRecievedRequests();
		},
		error: function(error) {
			alert(error);
		}
	});
}


window.declineRequest = declineRequest;
function declineRequest(sender) {
	$.ajax({
		url: "/users/friendRequest/decline/" + sender,
		method: "PUT",
		success: function() {
			getRecievedRequests();
			toastr.success("Friend request declined.");
		},
		error: function(error) {
			toastr.error("Something is wrong with your request.(decline request)");
		}
	});
}


window.removeFriend = removeFriend;
function removeFriend(reciever) {
	$.ajax({
		url: "/users/removeFriend/" + reciever,
		method: "PUT",
		success: function() {
			getFriends();
			toastr.success("Friend removed.");
		},	
		error: function(error) {
			toastr.error("Something is wrong with your request.(friend remove)");
		}
	});
}



function updateSearchResultsTable(results) {
	var html = "";
	results.forEach(function(result){
		html += 
		`
		<tr>
			<td> ${result.firstName} </td>
			<td> ${result.lastName} </td>
			<td> ${result.email} </td>
			<td> <button type="button" class="btn btn-success" onclick=sendRequest("${result.email}")>Send request</button> </tr>
		</tr>
		`;
	});
	if (html) {
		$("#resultsTable tbody").html(html);
		$("#resultsTable").show();
	}
}

window.clearResults = clearResults;
function clearResults() {
	$("#resultsTable tbody").html("");
	$("#resultsTable").hide();
}


function updateFriendsTable(friends) {
	var html = "";
	friends.forEach(function(friend){
		html += 
		`
		<tr>
			<td> ${friend.firstName} </td>
			<td> ${friend.lastName} </td>
			<td> ${friend.email} </td>
			<td> <button type="button" class="btn btn-danger" onclick=removeFriend("${friend.email}")>Remove</button> </tr>
		</tr>
		`;
	});
	$(currentTableId + " tbody").html(html);
}


function updateSentRequestsTable(requests) {
	var html = "";
	requests.forEach(function(request){
		html += 
		`
		<tr>
			<td> ${request.reciever.email} </td>
			<td> ${request.dateSent} </td>
			<td> <button type="button" class="btn btn-danger" onclick=declineRequest("${request.reciever.email}")>Cancel</button> </td>
		</tr>
		`;
	});
	$(currentTableId + " tbody").html(html);
}


function updateRecievedRequestsTable(requests) {
	var html = "";
	requests.forEach(function(request){
		html += 
		`
		<tr>
			<td> ${request.sender.email} </td>
			<td> ${request.dateSent} </td>
			<td> <button type="button" class="btn btn-success" onclick=approveRequest("${request.sender.email}")>Approve</button> </td>
			<td> <button type="button" class="btn btn-danger" onclick=declineRequest("${request.sender.email}")>Decline</button> </td>
		</tr>
		`;
	});
	$(currentTableId + " tbody").html(html);
}



function changeTable() {
	var selector = $("#tableSelector").val();

	if (selector == "Friends") {
		currentTableId = "#friendsTable";
		getFriends();
		$("#friendsTable").show();
		$("#sentRequestsTable").hide();
		$("#recievedRequestsTable").hide();
	}
	else if (selector == "Sent requests") {
		currentTableId = "#sentRequestsTable";
		getSentRequests();
		$("#friendsTable").hide();
		$("#sentRequestsTable").show();
		$("#recievedRequestsTable").hide();
	}
	else {
		currentTableId = "#recievedRequestsTable";
		getRecievedRequests();
		$("#friendsTable").hide();
		$("#sentRequestsTable").hide();
		$("#recievedRequestsTable").show();
	}
}