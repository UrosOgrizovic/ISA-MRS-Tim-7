var airline = "airline1";
var quickTickets = [];
var ownerEmail = "markomarkovic@gmail.com";
var selectedTicketId = null;

$(document).ready(function(){
	$.ajax({
		url: "http://localhost:8080/airlines/" + airline,
		method: "GET",
		dataType: "json",
		success: function (result) {
			setInputs(result);
			initMap(result);
		}
	});	

	getQuickReservations();
	$("#makeQuickRes").click(finishReservation);

});


function getQuickReservations() {
	$.ajax({
		url: `http://localhost:8080/flightReservations/quickReservations/${airline}`,
		method: "GET",
		dataType: "json",
		success: function(result) {
			quickTickets = result;
			updateTable();
		}, 
		error: function() {
			alert("Getting quick tickets failed.")
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
	quickTickets.forEach(function(ticket){
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
		table.append(row);
	});
}


function showReserveModal(ticketId) {
	selectedTicketId = ticketId;
	$("#finishReservationModal").modal("toggle");
}


function finishReservation() {
	$("#finishReservationModal").modal("toggle");
	passport = $("#passport").val().trim();

	$.ajax({
		url: `http://localhost:8080/flightReservations/quickReservation/${selectedTicketId}/${ownerEmail}/${passport}`,
		method: "PUT",
		dataType: "json",
		success: function(result) {
			getQuickReservations();
		},
		error: function() {
			alert("Making quick reservation failed!");
		}
	});
}