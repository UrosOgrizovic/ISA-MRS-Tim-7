var selectedSeat = null;
var currentFlight = null;

$(document).ready(function(){
	$("#createSeatBtn").click(createSeat);
	$("#updateSeatBtn").click(updateSeat);
	$("#deleteSeatBtn").click(deleteSeat);
	$("#quickSeatBtn").click(toggleQuickSeatModal);
	$("#makeQuickRes").click(makeQuickReservation);
});

function createSeat() {
	var flightId = currentFlight.id;
	$.ajax({
		url: "/seats/" + flightId + "/ECONOMY",
		method: "POST",
		dataType: "json",
		success: function(result) {
			refreshFlight(flightId);
			toastr.success("Seat created.");
		},
		error: function() {
			toastr.error("Something is wrong with your request.(create seat)");
		}
	});
}

function updateSeat() {
	var type = $("#newSeatType").val();
	var flightId = currentFlight.id;
	var seatNum = selectedSeat.settings.label;
	
	$.ajax({
		url: "/seats/" + flightId + "/" + seatNum + "/" + type,
		method: "PUT",
		success: function() {
			refreshFlight(flightId);
			toastr.success("Update successfull.");
		},
		error: function() {
			toastr.error("Something is wrong with your request.(update seat)");
		}
	});
}

function deleteSeat() {
	var flightId = currentFlight.id;
	var seatNum = selectedSeat.settings.label;
	
	$.ajax({
		url: "/seats/" + flightId + "/" + seatNum,
		method: "DELETE",
		success: function() {
			refreshFlight(flightId);
			toastr.success("Seat deleted.");
		},
		error: function() {
			toastr.error("Something is wrong with your request.(delete seat)");
		}
	});
}


function showSeatModal(flightId) {
	refreshSeatModal();
	currentFlight = getFlight(flightId);
	currentFlight.seats.sort(function(a,b){
		return a.seatNum - b.seatNum;
	})
	var seatStructure = createSeatStructure(currentFlight.seats);
	renderSeats(currentFlight.seats, seatStructure, currentFlight.price);
	updateSeatButtons(true);
	$("#seatModal").modal("show");
}


function refreshSeatModal() {
	$('.seatCharts-row').remove();
	$('.seatCharts-legendItem').remove();
	$('#seat-map,#seat-map *').unbind().removeData();
	refreshSeatTable();
	updateSeatButtons(true);
}

function getFlight(flightId) {
	for(var i = 0; i < flights.length; i++)
		if(flights[i].id == flightId)
			return flights[i];
	return null;
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
			if (seats[i].quickAvailable)
				row = row + "q";
			else
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
	console.log(seatStructure);
	return seatStructure;
}

function renderSeats(seats, seatStructure, price) {
	
	// seat indexer
	var seatIndex = 0;
	
	var sc = $('#seat-map').seatCharts({
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
				category: 'Unavailable',
				},
			q: {
				classes: 'quickAvailable',
				category: 'quickAvailable',
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
				[ 'u', 'unavailable', 'Already Booked'],
				[ 'q', 'quickAvailable',   'Available on quick reservation']
			]
		},
		click: function () {
			if (this.status() == 'available') {
				if (selectedSeat != null)
					selectedSeat.click();

				selectedSeat = this;
				refreshSeatTable();
				updateSeatTable(selectedSeat);
				updateSeatButtons(false);
				return 'selected';
			} 
			else if (this.status() == 'selected') {
				refreshSeatTable();
				updateSeatButtons(true);
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
}

function updateSeatTable(seat) {
	var html = ` 
		<tr>
			<td>${seat.settings.label}</td>
			<td>
				<select class="form-control input-sm" id="newSeatType">
					<option id="first">FIRST</option>
					<option id="business">BUSINESS</option>
					<option id="economy">ECONOMY</option>
				</select>
			</td>
		</tr>`;
	$("#seatTable tbody").append(html);
	$("#newSeatType").val(seat.settings.data.category);
}

function refreshSeatTable() {
	$("#seatTable tbody").html("");
}

function updateSeatButtons(state) {
	$("#quickSeatBtn").attr("disabled", state);
	$("#updateSeatBtn").attr("disabled", state);
	$("#deleteSeatBtn").attr("disabled", state);
	$("#quickSeat").attr("disabled", state);
}

function toggleQuickSeatModal() {
	$("#quickReservaitonModal").modal("toggle");
};


function makeQuickReservation() {
	var id = currentFlight.id;
	var seatNum = selectedSeat.settings.label;
	var discount = $("#discount").val().trim();

	$.ajax({
		url: `/flightReservations/quickReservation/${id}/${seatNum}/${discount}`,
		method: "POST",
		success: function() {
			toggleQuickSeatModal();
			refreshFlight(id);
			toastr.success("Quick reservation is now available for seat " + seatNum);
		}, 
		error: function() {
			toastr.error("Something is wrong with your request.(create quick reservation)");
		}
	});
}


function refreshFlight(id) {
	$.ajax({
		url: `/flights/${id}`,
		method: "GET",
		dataType: "json",
		success: function(flight) {
			for(var i = 0; i < flights.length; i++)
				if(flights[i].id == flight.id)
					flights[i] = flight;
			showSeatModal(id);
		},
		error: function() {
			toastr.error("Something is wrong with your request.(refresh flight)");
		}
	});
}