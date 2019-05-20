var selectedSeat = null;
var currentFlight = null;

$(document).ready(function(){
	$("#createSeatBtn").click(createSeat);
	$("#updateSeatBtn").click(updateSeat);
	$("#deleteSeatBtn").click(deleteSeat);
});

function createSeat() {
	var flightId = currentFlight.id;
	$.ajax({
		url: "http://localhost:8080/seats/" + flightId + "/ECONOMY",
		method: "POST",
		dataType: "json",
		success: function(result) {
			currentFlight.seats.push(result);
			showSeatModal(flightId);
		},
		error: function() {
			alert("Error.");
		}
	});
}

function updateSeat() {
	var type = $("#newSeatType").val();
	var flightId = currentFlight.id;
	var seatNum = selectedSeat.settings.label;
	
	$.ajax({
		url: "http://localhost:8080/seats/" + flightId + "/" + seatNum + "/" + type,
		method: "PUT",
		success: function() {
			for (var i = 0; i < currentFlight.seats.length; i++)
				if (currentFlight.seats[i].seatNum == seatNum) {
					currentFlight.seats[i].typeClass = type;
					showSeatModal(flightId);
				}
		},
		error: function() {
			alert("Bad request!");
		}
	});
}

function deleteSeat() {
	var flightId = currentFlight.id;
	var seatNum = selectedSeat.settings.label;
	
	$.ajax({
		url: "http://localhost:8080/seats/" + flightId + "/" + seatNum,
		method: "DELETE",
		success: function() {
			for (var i = 0; i < currentFlight.seats.length; i++)
				if (currentFlight.seats[i].seatNum == seatNum) {
					currentFlight.seats.splice(i,1);
					showSeatModal(flightId);
				}
		},
		error: function() {
			alert("Bad request!");
		}
	});
}


function showSeatModal(flightId) {
	refreshSeatModal();
	currentFlight = getFlight(flightId);
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
	// clear last seat chart
	
	
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
	$("#updateSeatBtn").attr("disabled", state);
	$("#deleteSeatBtn").attr("disabled", state);
}