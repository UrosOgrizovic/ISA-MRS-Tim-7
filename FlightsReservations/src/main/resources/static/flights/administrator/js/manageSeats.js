var selectedSeat = null;
var seatObjects = [];

function showSeatModal(flightId) {
	refreshSeatModal();
	var flight = getFlight(flightId);
	var seatStructure = createSeatStructure(flight.seats);
	renderSeats(flight.seats, seatStructure, flight.price);
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
		if (seats[i].typeClass == "ECONOMY")
			row = row + "e";
		else if (seats[i].typeClass == "BUSINESS")
			row = row + "b";
		else 
			row = row + "f";
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


function getUnavailableSeats(seats) {
	var unavailable = [];
	var rowCounter = 0;
	var elCounter = 0;
	for(var i = 0; i < seats.length; i++) {
		console.log(seats[i].available);
		if (!seats[i].available) {
			
			unavailable.push((rowCounter+1) + "_" + (elCounter +1))
		}
		elCounter += 1;
		if (elCounter == 7) {
			elCounter = 0;
			rowCounter += 1;
		}
	}
	return unavailable;
}



function renderSeats(seats, seatStructure, price) {
	// clear last seat chart
	
	
	// seat indexer
	var seatIndex = 0;
	
	var sc = $('#seat-map').seatCharts({
		map: seatStructure,
		seats: {
			f: {
				price   : 100,
				classes : 'first-class', //your custom CSS class
				category: 'First Class',
			},
			e: {
				price   : 40,
				classes : 'economy-class', //your custom CSS class
				category: 'Economy Class',
				},
			b: {
				price   : 70,
				classes : 'business-class', //your custom CSS class
				category: 'Business Class',
				},
		
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
				[ 'f', 'unavailable', 'Already Booked']
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
	
	//let's pretend some seats have already been booked
	
	
	sc.get(getUnavailableSeats(seats)).status('unavailable');
}


function updateSeatTable(seat) {
	var html = `
		<tr>
			<td>${seat.settings.label}</td>
			<td>
				<select class="form-control input-sm" id="newSeatType">
      				<option id="first">First Class</option>
      				<option id="business">Business Class</option>
      				<option id="economy">Economy Class</option>
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