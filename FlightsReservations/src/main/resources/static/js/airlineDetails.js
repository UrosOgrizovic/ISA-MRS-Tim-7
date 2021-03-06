var quickTickets = [];
var ownerEmail = localStorage.getItem("email");
var selectedTicketId = null;
var lastClicked = null;

$(document).ready(function(){
	$("#airline").hide();
	$("#makeQuickRes").click(finishReservation);
});


function show(name) {
	$.ajax({
		url: "/airlines/" + name,
		method: "GET",
		dataType: "json",
		success: function (result) {
			setInputs(result);
			initMap(result);
		}
	});	
	lastClicked = name;
	$("#airline").show();
	getQuickReservations(name);
}


function getQuickReservations(name) {
	$.ajax({
		url: `/flightReservations/quickReservations/${name}`,
		method: "GET",
		dataType: "json",
		success: function(result) {
			quickTickets = result;
			updateTable();
		}, 
		error: function() {
			toastr.error("Something is wrong with your request.(get quick tickets)");
		}
	}); 
}


function setInputs(airline){
	$("#name").val(airline.name);
	$("#promoDesc").val(airline.promoDescription);
	$("#numOfVotes").val(airline.numberOfVotes);
	$("#avgScore").val(airline.averageScore);
}


function initMap(result) {
    var myLatLng = {lat: result.latitude, lng: result.longitude};

    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 4,
      center: myLatLng
    });

    var marker = new google.maps.Marker({
      position: myLatLng,
      map: map,
      title: 'Hello World!'
    });
  }


function updateTable() {
	var table = $("#QRTable tbody");
	table.html("");
	var html = "";
	console.log(quickTickets.length);
	quickTickets.forEach(function(ticket){
		console.log(ticket);
		row = 
		`
		<tr>
			<td> ${ticket.startAirport} </td>
			<td> ${ticket.endAirport} </td>
			<td> ${ticket.takeoffTime} </td>
			<td> ${ticket.landingTime} </td>
			<td> ${ticket.totalPrice} </td>
			<td> ${ticket.discount}% </td>
			<td> ${ticket.seatNumber} </td>
			<td> <button type="button" class="btn btn-success" onclick="showReserveModal(${ticket.id})">Reserve</button></td>
		</tr>
		`;
		html+=row;
	});
	table.html(html);
}


function showReserveModal(ticketId) {
	selectedTicketId = ticketId;
	$("#finishReservationModal").modal("toggle");
}


function finishReservation() {
	$("#finishReservationModal").modal("toggle");
	var passport = $("#passport").val().trim();

	$.ajax({
		url: `/flightReservations/quickReservation/${selectedTicketId}/${passport}`,
		method: "PUT",
		dataType: "json",
		success: function(result) {
			toastr.success("Reservation made successfully.");
			getQuickReservations(lastClicked);
		},
		error: function() {
			toastr.error("Something is wrong with your request.(make quick reservation)"); 
		}
	});
}