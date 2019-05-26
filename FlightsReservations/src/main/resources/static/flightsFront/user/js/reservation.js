var reservationState = null;
var resultIndex = null;
var flightIndex = 0;
var selectedSeat = null;
var counter = 0;
var sc = null;
var pricelist = null;
var totalPrice = 0;

var ownerEmail = localstorage.getItem("email");

$(document).ready(function(){
	$("#flightSelector").change(changeFlight);
	$("#addPassenger").click(addPassengerAndUpdateTable);
	$("#makeReservation").click(makeReservation);
	$("#add-passenger-type").change(updatePassengerModal);
	$("#autoPassenger").click(autoPassenger);

	pricelist = new Object();
	pricelist.first = 400;
	pricelist.business = 300;
	pricelist.economic = 200;
});


function changeFlight() {
	flightIndex = $("#flightSelector").val();
	showDataInModal(resultIndex, $("#flightSelector").val());
}


function showReservationModal(index) {
	totalPrice = 0;
	$("#price").text(totalPrice);
 
	resultIndex = index;	
	flightIndex = $("#flightSelector").val();
	populateFlightSelector(index);
	prepareReservationData(index);
	updatePassengerTable();
	showDataInModal(index, $("#flightSelector").val());
	$("#reservationModal").modal("show");
}


function showDataInModal(resultIndex, flightIndex) {
	refreshReservationModal();
	var seats = searchResults[resultIndex][flightIndex].seats;
	var seatStructure = createSeatStructure(seats);
	renderSeats(seats, seatStructure, flightIndex);
	updatePassengerTable();
}


function prepareReservationData(index) {
	reservationState = new Object();
	reservationState.passengers = [];
	reservationState.flights = [];
	reservationState.reservedSeats = [];
	reservationState.invites = [];

	for (var i = 0; i < searchResults[index].length; i++) { 
		reservationState.flights.push(searchResults[index][i].id);
		reservationState.passengers.push([]);
		reservationState.reservedSeats.push([]);
		reservationState.invites.push([]);
	}
}


function createSeatStructure(seats) {
	var seatStructure = [];
	var rowCounter = 0;
	var row = "";
	for(var i = 0; i < seats.length; i++) {
		if (seats[i].available) {
			if (seats[i].typeClass == "ECONOMY")
				row = row + "e";
			else if (seats[i].typeClass == "BUSINESS")
				row = row + "b";
			else 
				row = row + "f";
		} else {
			row = row + "u";
		}
		rowCounter += 1;
		if (rowCounter == 7) {
			rowCounter = 0;
			seatStructure.push(row);
			row = "";
		}
	}
	
	if (rowCounter != 7 && rowCounter != 0)
		seatStructure.push(row);
	return seatStructure;
}


function renderSeats(seats, seatStructure, price) {
	// seat indexer
	var seatIndex = 0;
	
	sc = $('#seat-map').seatCharts({
		map: seatStructure,
		seats: {
			f: {
				classes : 'first-class', //your custom CSS class
				category: 'FIRST',
			},
			e: {
				classes : 'economy-class', //your custom CSS class
				category: 'ECONOMY',
				},
			b: {
				classes : 'business-class', //your custom CSS class
				category: 'BUSINESS',
				},
			u: {
		        classes: 'unavailable',
		        category: 'Unavailable'
			    }
		},
		naming : {
			top : false,
			getLabel : function (character, row, column) {
				return seats[seatIndex++].seatNum;
			},
		},
		legend : {
			node : $('#legend'),
		items : [
				[ 'f', 'available',   'First Class' ],
				[ 'e', 'available',   'Economy Class'],
				[ 'b', 'available',   'Business Class'],
				[ 'u', 'unavailable', 'Already Booked']
							]
		},
		click: function () {
			selectedSeat = this;
			if (this.status() == 'available') {
				addPassengerModal();
				return 'available';
			} 
			else if (this.status() == 'selected') {
				removePassenger()
				//updateSeatButtons(true);
				//selectedSeat = null;
				return 'available';
			} 
			else if (this.status() == 'unavailable') {
				return 'unavailable';
			} 
			else {
				return this.style();
			}
		}
	});	

	var reserved = reservationState.reservedSeats[flightIndex];
	sc.status(reserved, "selected");
}


function refreshReservationModal() {
	$('.seatCharts-row').remove();
	$('.seatCharts-legendItem').remove();
	$('#seat-map,#seat-map *').unbind().removeData();
}


function populateFlightSelector(index) {
	$("#flightSelector").html("");
	for (var i = 0; i < searchResults[index].length; i++)
		$("#flightSelector").append(`<option> ${i} </option>`);
}


function addPassengerModal() {
	$("#passengerName").val("");
	$("#passengerSurname").val("");
	$("#passengerPassport").val("");	
	$("#friendEmail").val("");
	updatePassengerModal();
	$("#addPassengerModal").modal("toggle");
}


function updatePassengerModal() {
	var type = $("#addPassengerType").val();
	if (type == "Invite registered user") {
		$("#registered-user").show();
		$("#unregistered-user").hide();
		$("#autoPassenger").prop("disabled", true);
	} else {
		$("#registered-user").hide();
		$("#unregistered-user").show();
		$("#autoPassenger").prop("disabled", false);
	}
}


function addPassengerAndUpdateTable() {
	if ($("#add-passenger-type").val() == "Invite registered user")
		inviteFriend();
	else
		createPassenger();
	updatePassengerTable();
	//updateTotalPrice();
	$("#addPassengerModal").modal("toggle");
}


function createPassenger() {
	var passenger = new Object();
	passenger.name = $("#passengerName").val().trim();
	passenger.surname = $("#passengerSurname").val().trim();
	passenger.passport = $("#passengerPassport").val().trim();
	passenger.seatNumber = selectedSeat.settings.label;

	var seatIndex = selectedSeat.settings.id;
	var flightIndex = $("#flightSelector").val();
	if (validatePassenger(passenger)) {
		reservationState.passengers[flightIndex].push(passenger);	
		reservationState.reservedSeats[flightIndex].push(seatIndex);
		sc.status(selectedSeat.settings.id, 'selected');
		updatePrice("+");
	}
}


function inviteFriend() {
	// if are friends
	// 	add to request
	var invite = new Object();
	invite.friendEmail = $("#friendEmail").val().trim();
	invite.passport = $("#passengerPassport").val().trim();
	invite.seatNumber = selectedSeat.settings.label;

	var flightIndex = $("#flightSelector").val();
	$.ajax({
		url: "http://localhost:8080/users/getFriends/" + ownerEmail,
		method: "GET",
		dataType: "json",
		success: function(friends) {
			var found = false;
			friends.forEach(function(friend){
				if (friend.email == invite.friendEmail) {
					console.log(friend.email, invite.friendEmail);
					reservationState.invites[flightIndex].push(invite);
					reservationState.reservedSeats[flightIndex].push(invite.seatNumber);
					sc.status(selectedSeat.settings.id, 'selected');
					updatePrice("+");
					found = true;
					return;
				}
			});

			if (!found)
				alert("NOT FRIENDS!");

		},
		error: function(message) {
			alert("GET FRIENDS BAD REQUEST!")
		}
	});



}


function autoPassenger() {
	alert("To implement when security is done.");
}


function validatePassenger(passenger) {
	for (var property in passenger) {
	    if (passenger.hasOwnProperty(property)) {
	        if (passenger[property] == "")
	        	return false;
	    }
	}
	return true;
}


function updatePassengerTable() {
	$("#passengerTable tbody").html("");
	flightIndex = $("#flightSelector").val();

	var html = "";
	for (var i = 0; i < reservationState.passengers[flightIndex].length; i++) {

		var passenger = reservationState.passengers[flightIndex][i];

		html += `
		<tr>
			<td>${passenger.seatNumber}</td>
			<td>${passenger.name}</td>
			<td>${passenger.surname}</td>
			<td>${passenger.passport}</td>
		</tr>
		`;
	}
	$("#passengerTable tbody").append(html);
}


function removePassenger() {
	var seatId = selectedSeat.settings.id;
	var seatNumber = selectedSeat.settings.label;

	var indexOfId = reservationState.reservedSeats[flightIndex].indexOf(seatId);
	reservationState.reservedSeats[flightIndex].splice(indexOfId, 1);

	for (var i = 0; i < reservationState.passengers[flightIndex].length; i++) {
		console.log(reservationState.passengers[flightIndex][i].seatNumber, seatNumber);
		if (reservationState.passengers[flightIndex][i].seatNumber == seatNumber) {
			reservationState.passengers[flightIndex].splice(i, 1);
			break;
		}
	}
	updatePrice("-");
	updatePassengerTable();
}


function updatePrice(operation) {
	seatType = selectedSeat.settings.data.category;
	flightPrice = searchResults[resultIndex][flightIndex].price;

	if (operation == "+") {
		totalPrice += flightPrice;
		if (seatType == "FIRST")
			totalPrice += pricelist.first;
		else if (seatType == "BUSINESS")
			totalPrice += pricelist.business;
		else 
			totalPrice += pricelist.economic;
	} else {
		totalPrice -= flightPrice;
		if (seatType == "FIRST")
			totalPrice -= pricelist.first;
		else if (seatType == "BUSINESS")
			totalPrice -= pricelist.business;
		else 
			totalPrice -= pricelist.economic;
	}
	$("#price").text(totalPrice);
}



function makeReservation() {
	var request = new Object();
	request.ownerEmail = "markomarkovic@gmail.com";
	request.flights = [];

	for (var i = 0; i < searchResults[resultIndex].length; i++) {
		var f = new Object();
		f.flightId = searchResults[resultIndex][i].id;
		f.passengers = reservationState.passengers[i];
		f.invites = reservationState.invites[i];
		request.flights.push(f);
	}
		

	console.log(request);

	$.ajax({
		url: "http://localhost:8080/flightReservations",
		method: "POST",
		contentType: "application/json",
		data: JSON.stringify(request),
		dataType: "json",
		success: function(result) {
			$("#reservationModal").modal("toggle");
			search();
		}, 	
		error: function(error) {
			console.log(error);
		}
	});
}