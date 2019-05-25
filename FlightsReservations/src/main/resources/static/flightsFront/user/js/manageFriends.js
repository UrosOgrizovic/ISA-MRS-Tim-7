var email = localStorage.getItem("email");
var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

var friends = []
var sentRequests = []
var recievedRequests = []
var currentTableId = null;


$(document).ready(function(){
	$("#searchBtn").click(sendRequest);
	$("#tableSelector").change(changeTable);
	changeTable();	
});


function getFriends() {
	$.ajax({
		url: "http://localhost:8080/users/getFriends/" + email,
		method: "GET",
		data: "json",
		success: function(result) {
			updateFriendsTable(result);
		},	
		error: function(error) {
			alert(error);
		}
	});
}


function getSentRequests() {
	$.ajax({
		url: "http://localhost:8080/users/getSentFriendRequests/" + email,
		method: "GET",
		data: "json",
		success: function(result) {
			updateSentRequestsTable(result);
		},
		error: function(error) {
			alert(error);
		}
	});
}


function getRecievedRequests() {
	$.ajax({
		url: "http://localhost:8080/users/getRecievedFriendRequests/" + email,
		method: "GET",
		data: "json",
		success: function(result) {
			updateRecievedRequestsTable(result);
		},
		error: function(error) {
			alert(error);
		}
	});
}



function sendRequest() {
	var reciever = $("#searchInput").val().trim();
	$.ajax({
		url: "http://localhost:8080/users/friendRequest/send/" + email + "/" + reciever,
		method: "PUT",
		success: function() {
			getSentRequests();
		},
		error: function(error) {
			alert(error);
		}
	});
}


function approveRequest(sender) {
	$.ajax({
		url: "http://localhost:8080/users/friendRequest/approve/" + sender + "/" + email,
		method: "PUT",
		success: function() {
			getRecievedRequests();
		},
		error: function(error) {
			alert(error);
		}
	});
}


function declineRequest(sender) {
	$.ajax({
		url: "http://localhost:8080/users/friendRequest/decline/" + sender + "/" + email,
		method: "PUT",
		success: function() {
			getRecievedRequests();
		},
		error: function(error) {
			alert(error);
		}
	});
}


function removeFriend(reciever) {
	$.ajax({
		url: "http://localhost:8080/users/removeFriend/" + email + "/" + reciever,
		method: "PUT",
		success: function() {
			getFriends();
		},	
		error: function(error) {
			alert(error);
		}
	});
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