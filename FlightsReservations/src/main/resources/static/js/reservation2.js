var currentUserEmail = localStorage.getItem("email");
var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

var reservation = null;
var resultIndex = null;
var flightIndex = null;
var selectedSeat = null;
var seatMap = null;
var currentlySelectedData = [];

var reservationReturn = null;
var takenOne = false;
var takenTwo = false;
var totalPrice = 0;

$(document).ready(function(){
	$("#createPassenger").click(showCreatePassengerModal);
	$("#addPassengerType").change(updatePassengerModal);
	$("#seatMapSelector").change(prepareSeatMap);
	$("#addPassenger").click(addPassenger);
	$("#makeReservation").click(sendReservationRequest);
	$("#continueReservationBtn").click(continueReservation);
	$("#passengerTable").hide();
	$("#inviteTable").hide();
});


function showReservationModal(resultIndx) {
	resultIndex = resultIndx;
	prepareReservationObject();
	prepareTable();
	prepareSeatMapSelector();
	updatePassengerModal();
	refreshPassengerTable();
	refreshInviteTable();
	$("#reservationModal").modal("toggle");
}


function prepareReservationObject() {
	reservation = new Object();
	reservation.ownerEmail = currentUserEmail;
	reservation.flights = [];
	searchResults[resultIndex].forEach(function(flight){
		var reservationDetail = new Object();
		reservationDetail.flightId = flight.id;
		reservationDetail.passengers = [];
		reservationDetail.invites = [];
		reservation.flights.push(reservationDetail);
	});
}


function prepareTable() {
	if (searchResults[resultIndex].length == 1) {
		$("#flightTwoHeader").hide();
		$("#flightTwoHeaderInv").hide();
	}
	else {
		$("#flightTwoHeader").show();
		$("#flightTwoHeaderInv").show();
	}
}


function prepareSeatMapSelector() {
	$("#seatMapSelector").html("");
	var i = 0;
	searchResults[resultIndex].forEach(function(flight){
		$("#seatMapSelector").append(`<option data-flightNum="${i}">${flight.startAirport}-${flight.endAirport}</option>`);
		i++;
	});
}


function showCreatePassengerModal() {
	currentlySelectedData = [];
	takenOne = false;
	takenTwo = false;
	prepareSeatMap();
	$("#addPassengerModal").modal("toggle");
}


function prepareSeatMap() {
	clearSeatMap();
	flightIndex = $("#seatMapSelector").find(':selected').attr('data-flightNum');
	seats = searchResults[resultIndex][flightIndex].seats;
	seatStruct = createSeatStructure(seats);
	renderSeats(seats, seatStruct);
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
	$("#passengerName").val("");
	$("#passengerSurname").val("");
	$("#passengerPassport").val("");
	$("#friendEmail").val("");

	currentlySelectedData = [];
	takenOne = false;
	takenTwo = false;
	prepareSeatMap();
}


function createSeatStructure(seats) {
	seats.sort(function(a,b){
		return a.seatNum - b.seatNum;
	});
	
	var seatStructure = [];
	var rowCounter = 0;
	var row = "";
	for(var i = 0; i < seats.length; i++) {
		if (seats[i].available) {
			
			if (seatTaken(seats[i].seatNum))
				row = row + "t";
			else if (seats[i].typeClass == "ECONOMY")
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


function renderSeats(seats, seatStructure) {
	// seat indexer
	var seatIndex = 0;
	
	seatMap = $('#seatMap').seatCharts({
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
			t: {
				classes: 'taken',
				categody: "Taken"
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
				[ 't', 'available',   'Already selected'],
				[ 'u', 'unavailable', 'Already Booked']
			]
		},
		click: function () {
			if (($("#passengerName").val().trim() != "" && $("#passengerSurname").val().trim() != "" && $("#passengerPassport").val().trim() != "") || $("#friendEmail").val().trim() != "") {
				if (this.status() == 'available') {
					if (flightIndex == 0 && !takenOne) {
						selectedSeat = this;
						addSelectedData();
						takenOne = true;
						return 'selected';
					}
					else if (flightIndex == 1 && !takenTwo) {
						selectedSeat = this;
						addSelectedData();
						takenTwo = true;
						return 'selected';	
					}
					else {
						return 'available';
					}
				}
				return "available"; 
			} 
			else if (this.status() == 'selected') {
				if (flightIndex == 0)
					takenOne = false;
				else
					takenTwo = false;
				selectedSeat = this;
				removeSelectedData();
				selectedSeat = null;
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

	//var reserved = reservationState.reservedSeats[flightIndex];
	seatMap.status(getSelected(), "selected");
}


function clearSeatMap() {
	seatMap = null;
	selectedSeat = null;
	$("#seatMap").html("");
	$('.seatCharts-row').remove();
	$('.seatCharts-legendItem').remove();
	$('#seatMap,#seatMap *').unbind().removeData();
}


function seatTaken(seatNum) {
	var taken = false;
	reservation.flights[flightIndex].passengers.forEach(function(passenger){
		if (passenger.seatNumber == seatNum) 
			taken = true;
	});

	reservation.flights[flightIndex].invites.forEach(function(invite) {
		if (invite.seatNumber == seatNum)
			taken = true;
	});
	return taken;
}


function getSelected() {
	var selected = [];
	currentlySelectedData.forEach(function(data){
		if (data.flightIndex == flightIndex)
			selected.push(data.seatId);
	});
	return selected;
}


function addSelectedData() {
	var type = $("#addPassengerType").val();
	if (type == "Add unregistered user") {
		var passenger = new Object();
		passenger.name = $("#passengerName").val().trim();
		passenger.surname = $("#passengerSurname").val().trim();
		passenger.passport = $("#passengerPassport").val().trim();
		passenger.seatNumber = selectedSeat.settings.label;
		passenger.seatId = selectedSeat.settings.id;
		passenger.flightIndex = flightIndex;
		currentlySelectedData.push(passenger);
	} else {
		var invite = new Object();
		invite.friendEmail = $("#friendEmail").val().trim();
		invite.seatNumber = selectedSeat.settings.label;
		invite.seatId = selectedSeat.settings.id;
		invite.flightIndex = flightIndex;
		currentlySelectedData.push(invite);
	}
}


function removeSelectedData() {
	for (var i = 0; i < currentlySelectedData.length; i++)
		if (currentlySelectedData[i].seatNumber == selectedSeat.settings.label && currentlySelectedData[i].flightIndex == flightIndex) {
			currentlySelectedData.splice(i, 1);
			break;
		}
}


function addPassenger() {
	var c = searchResults[resultIndex].length;
	if ((takenOne == false && c == 1) || takenOne == false && takenTwo == false && c == 2) {
		toastr.info("You must select your seat.");
		return;
	}

	var type = $("#addPassengerType").val();
	if (type == "Add unregistered user") {
		currentlySelectedData.forEach(function(passenger){
			reservation.flights[passenger.flightIndex].passengers.push(passenger);
		});
		refreshPassengerTable();
	} else {
		currentlySelectedData.forEach(function(invite){
			reservation.flights[invite.flightIndex].invites.push(invite);
		});
		refreshInviteTable();
	}
	$("#addPassengerModal").modal("toggle");
}


function refreshPassengerTable() {
	var table = $("#passengerTable tbody");
	table.html("");
	
	var passengers = reservation.flights[0].passengers;
	if (passengers.length > 0) {
		$("#passengerTable").show();
		for (var i = 0; i < passengers.length; i++) {
			var row = 
			`
			<tr>
				<td> ${passengers[i].name} </td>
				<td> ${passengers[i].surname} </td>
				<td> ${passengers[i].passport} </td>
				<td> ${passengers[i].seatNumber} </td>`;
			if (searchResults[resultIndex].length == 2)
				row += `<td> ${reservation.flights[1].passengers[i].seatNumber} </td>`
			
			row += `<td> <button type="button" class="btn btn-danger" onclick="removePassenger(${i})"> Remove </button> </td>
			</tr>
			`;
			table.append(row);
		}
	} else {
		$("#passengerTable").hide();
	}
}


function refreshInviteTable() {
	var table = $("#inviteTable tbody");
	table.html("");
	var invites = reservation.flights[0].invites;
	
	if (invites.length > 0) {
		$("#inviteTable").show();
		for (var i = 0; i < invites.length; i++) {
			var row = 
			`
			<tr>
				<td> ${invites[i].friendEmail} </td>
				<td> ${invites[i].seatNumber} </td>`;
	
			if(searchResults[resultIndex].length == 2)	
				row += `<td> ${reservation.flights[1].invites[i].seatNumber} </td>`;
			row += `<td> <button type="button" class="btn btn-danger" onclick="removeInvite(${i})"> Cancel </button> </td>
			</tr>
			`;
			table.append(row);
		}
	} else {
		$("#inviteTable").hide();
	}
}


function removePassenger(passengerIndex) {
	reservation.flights[0].passengers.splice(passengerIndex,1);
	if (searchResults[resultIndex].length == 2)
		reservation.flights[1].passengers.splice(passengerIndex,1);
	refreshPassengerTable();
}


function removeInvite(inviteIndex) {
	reservation.flights[0].invites.splice(inviteIndex,1);
	if (searchResults[resultIndex].length == 2)
		reservation.flights[1].invites.splice(inviteIndex,1);
	refreshInviteTable();
}


function auto() {
	$.ajax({
		url: "/users/myDetails",
		method: "GET",
		dataType: "json",
		success: function (result) {
			$("#passengerName").val(result.firstName);
			$("#passengerSurname").val(result.lastName);
			toastr.info("You still need to enter your passport.");
		},
		error: function(result) {
			toastr.error("Something is wrong with your request.(get details)");
		}
    });	
}



function sendReservationRequest() {
	if (reservation.flights[0].passengers.length == 0) {
		toastr.info("You must enter some passengers.");
		return;
	}
	
	$.ajax({
		url: "/flightReservations",
		method: "POST",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(reservation),
		success: function(result) {
			console.log(result);
			reservationReturn = result;
			$("#reservationModal").modal("toggle");
			$("#continueReservationModal").modal("toggle");
			toastr.success("Reservation is made.")
		},
		error: function(error) {
			toastr.error("Something is wrong with your request.(make reservation)");
		}
	});
}


function continueReservation() {
	var flight = null;
	var endDate = null;
	if ($("#type-search").val() == "OneWay") {
		flight = reservationReturn.flights[0];
		endDate = moment(flight.landingTime, "DD-MM-YYYY HH:mm");
		endDate.add(30, 'days');
		endDate = endDate.format("DD-MM-YYYY HH:mm");
	}
	else if ($("#type-search").val() == "RoundTrip") {
		flight = reservationReturn.flights[0];
		retFlight = reservationReturn.flights[1];
		endDate = retFlight.takeoffTime;
	}
	else {
		flight = reservationReturn.flights[1];
		endDate = moment(flight.landingTime, "DD-MM-YYYY HH:mm");
		endDate.add(30, 'days');
		endDate = endDate.format("DD-MM-YYYY HH:mm");
	}
	
	$.ajax({
		url: `/airports/${flight.endAirport}`,
		method: "GET",
		dataType: "json",
		success: function(airport) {
			
			localStorage.setItem("city",airport.city);
			localStorage.setItem("state", airport.state);
			localStorage.setItem("flightReservationId", reservationReturn.id);
			localStorage.setItem("reservationPeriodStart", flight.landingTime);
			localStorage.setItem("reservationPeriodEnd", endDate);
			
			if ($("#reserveHotel").is(":checked")) {	
				//window.location.replace("/html/hotelReservation.html");
				toastr.info("To be implemented...");
			} else if ($("#reserveCars").is(":checked")) {
				window.location.replace("/html/discountCars.html");
			} else {
				$("#continueReservationModal").modal("toggle");
			}
		}
	});
}


