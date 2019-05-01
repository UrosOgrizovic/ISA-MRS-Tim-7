function showResults() {
	$("#search-results").html("");
	showPaginationButtons();

	for (var i = 0; i < searchResults.length; i++) {
		showResult(searchResults[i], i);
	}
}

function showResult(result, i) {
	var rows = "";
	var rowspan = result.length;
	var buttonAdded = false;

	var totalPrice = 0;

	result.forEach(function(flight){
		var time = Math.floor(flight.flightTime/60) + "hrs " + flight.flightTime%60 + "mins";
		rows += 
		`
		<tr>
			<td class="align-middle xl-text">
				<b>${flight.takeoffTime.split(" ")[1]}-${flight.landingTime.split(" ")[1]}</b>
			</td>
			<td class="align-middle">
				<b>${flight.startAirport}-${flight.endAirport}</b>
			</td>
			<td class="align-middle">
				<b>${flight.airlineName}</b>
			</td>
			<td class="align-middle">
				<b>Stops: ${flight.stops.length}</b>
			</td>
			<td class="align-middle">
				<b>Rating: ${flight.averageScore}</b>
			</td>
			<td class="align-middle xl-text">
				<b>${time}</b>
			</td>
			<td class="align-middle xl-text">
				<b>${flight.price}</b>
			</td>
		`; 	

		if (!buttonAdded) {
			var total = getTotalPrice(result);
			rows += `<td class = "align-center align-bottom" rowspan="${rowspan}">
						<b class="xxl-text"> ${total} </b> <br>
						<button-widthon id="searchBtn" type="submit" class="btn btn-primary button-width" onclick="showReservationModal(${i})">Reserve</button>
					</td>`;
			buttonAdded = true;
		}
		rows += '</tr>';
	});

	var html = 
	`<table class="table table-sm table-secondary">
		<tbody>` + 
			rows +
	`	</tbody>
	</table>`; 

	$("#search-results").append(html);
}


function getTotalPrice(result) {
	var total = 0;
	result.forEach(function(flight){
		total += flight.price;
	})
	return total;
}
