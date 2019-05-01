function showResults() {
	$("#search-results").html("");
	showPaginationButtons();
	searchResults.forEach(function(result) {
		showResult(result);
	});
}

function showResult(result) {
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
		`; 	

		if (!buttonAdded) {
			rows += `<td class = "align-center align-bottom" rowspan="${rowspan}">
						<b class="xxl-text"> ${flight.price}e </b> <br>
						<button-widthon id="searchBtn" type="submit" class="btn btn-primary button-width">Buy</button>
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