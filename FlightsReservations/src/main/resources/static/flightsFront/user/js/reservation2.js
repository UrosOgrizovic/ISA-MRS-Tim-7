var reservation = null;
var resultIndex = null;
var flightIndex = null;
var selectedSeat = null;
var seatMap = null;
var currentUserEmail = "markomarkovic@gmail.com"; 


$(document).ready(function(){
	$("#createPassenger").click(showCreatePassengerModal);
	$("#addPassengerType").change(updatePassengerModal);
	$("#seatMapSelector").change(prepareSeatMap)
});


function showReservationModal(resultIndx) {
	resultIndex = resultIndx;
	prepareTable();
	prepareSeatMapSelector();
	prepareSeatMap();
	$("#reservationModal").modal("toggle");
}


function prepareTable() {
	if (searchResults[resultIndex].length == 1)
		$("#flightTwoHeader").hide();
	else
		$("#flightTwoHeader").show();
}


function prepareSeatMapSelector() {
	$("#seatMapSelector").html("");
	var i = 0;
	searchResults[resultIndex].forEach(function(flight){
		$("#seatMapSelector").append(`<option data-flightNum="${i}">${flight.startAirport}-${flight.endAirport}</option>`);
		i++;
	});
}


function prepareSeatMap() {
	clearSeatMap();
	flightIndex = $("#seatMapSelector").find(':selected').attr('data-flightNum');
	seats = searchResults[resultIndex][flightIndex].seats;
	seatStruct = createSeatStructure(seats);
	renderSeats(seats, seatStruct);
}


function showCreatePassengerModal() {
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


function clearSeatMap() {
	seatMap = null;
	selectedSeat = null;
	$("#seatMap").html("");
	$('.seatCharts-row').remove();
	$('.seatCharts-legendItem').remove();
	$('#seatMap,#seatMap *').unbind().removeData();
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
				//addPassengerModal();
				return 'selected';
			} 
			else if (this.status() == 'selected') {
				//removePassenger()
				//updateSeatButtons(true);
				//selectedSeat = null;
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
	//sc.status(reserved, "selected");
}