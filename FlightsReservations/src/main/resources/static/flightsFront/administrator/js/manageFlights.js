var airline = "airserbia";
var stops = [];
var availableStops = [];
var flights = null;

$(document).ready(function(){
	configureDatepickers();
	getStops();
	getFlights();
	
	// get flights for airline
	// display flights in table
	
	// add listener for createModal button
	// add listener for createFlight button in createModal
	// add listener for addStop button
	// add listener for removeStop button
	
	// when createModal fired 
	//     clear stopNames
	
	// when createFlight fired
	//    if inputs valid
	//        ajax
	//        update flight table
	
	// when addStop fired
	//    if input valid and not in stops
	//        ajax
	//        add to stops
	//        update stop table
    
	// when removeStop fired
	//    if input valid and in stops
    //        remove from stop names
	//        remove from stop table
	
	$("#createFlightBtn").click(createFlight);
	$("#addStopBtn").click(addStop);
	$("#removeStopBtn").click(removeStop);
	$("#createModalBtn").click(function(){
		stops = [];
		refreshStopTable();
	});
});

function createFlight() {
	var flight = new Object();
	flight.startAirportName = $("#start").val().trim();
	flight.endAirportName = $("#end").val().trim();
	flight.takeOffTime = $("#takeoff").val().trim() 
	flight.takeOff_time = $("#takeoffTime").val().trim();
	flight.landingTime = $("#landing").val().trim();
	flight.landing_time = $("#landingTime").val().trim();
	flight.price = $("#price").val().trim();
	flight.numberOfSeats = $("#seats").val().trim();
	flight.firstClassNum = $("#first").val().trim();
	flight.businessClassNum = $("#business").val().trim();
	flight.economicClassNum = $("#economic").val().trim();
	flight.airlineName = airline;
	flight.stopNames = getStopNames(stops);
	flight.averageScore = 1;
	flight.numberOfVotes = 0;
	
	console.log('CREATING');
	console.log(validate(flight))
	if (validate(flight)) {
		flight.takeOffTime = flight.takeOffTime + " " + flight.takeOff_time;
		flight.landingTime = flight.landingTime + " " + flight.landing_time;
		
		$.ajax({
			url: "http://localhost:8080/flights",
			method: "POST",
			contentType: "application/json",
			data: JSON.stringify(flight),
			dataType: "json",
			success: function(result) {
				flights.push(result);
				
				refreshStopTable();
				updateFligthTable(flights);
				
				$("#closeCreateModal").click();
			},
			error: function(e) {
				alert("error");
			}
		});
	} else {
		alert("Check inputs!");
	}
}

function validate(flight) {
	for (var property in flight) {
		if (flight.hasOwnProperty(property)) {
			console.log(property + " " + flight[property]);
			if (flight[property] === "") {
				console.log("here we go");
				return false;
			}
		}
	}
	return true;
}

function getFlights() {
	$.ajax({
		url: "http://localhost:8080/airlines/flights/" + airline,
		method: "GET",
		dataType: "json",
		success: function(result) {
			flights = result;
			updateFligthTable(result);
		},
		error: function(e) {
			alert("ERROR");
		}
	});
}

function updateFligthTable(flights) {
	refreshFlightTable();
	flights.forEach(function(flight){
		insertFlightRow(flight);
	});
}

function insertFlightRow(flight) {
	var html = `
		<tr>
			<td>${flight.id}</td>
			<td>${flight.startAirport}</td>
			<td>${flight.endAirport}</td>
			<td>${flight.takeoffTime}</td>
			<td>${flight.landingTime}</td>
			<td><button type="button" class="btn" onclick="showSeatModal(${flight.id})">Show</button></td>
			<td>${flight.flightDistance.toFixed(1)}</td>
			<td>${flight.flightTime}</td>
			<td>${flight.price}</td>
			<td><button type="button" class="btn" onclick="showStopsOnMap(${flight.id})">Show</button></td>
			<td>${flight.averageScore}</td>
			<td>${flight.numberOfVotes}</td>
		</tr>`;
	
	
	       
	$("#flightsTable tbody").append(html);
}


function refreshFlightTable() {
	$("#flightsTable tbody").remove();
	$("#flightsTable").append("<tbody></tbody>")
}


function getStopNames(stops) {
	var names = []
	for (var i = 0; i < stops.length; i++)
		names.push(stops[i].name);
	return names;
}


function getStops() {
	$.ajax({
		url: "http://localhost:8080/airlines/airports/" + airline,
		method: "GET",
		dataType: "json",
		success: function(result) {
			availableStops = result;
		},
		error: function(e) {
			alert("Couldn't get airports for airline, refresh page!");
		}
	});
}


function addStop() {
	var stopName = $("#stop").val().trim();
	var stop = airportIsAvailable(stopName);
	
	if (!inStops(stopName) && stop != null && stopName != "") {
		stops.push(stop);
		updateStopTable(stops);
	} else {
		alert("Bad stop name!");
	}
}


function removeStop() {
	var stopName = $("#stop").val().trim();
	for (var i = 0; i < stops.length; i++) {
		if (stops[i].name.toLowerCase() == stopName.toLowerCase()) {
			stops.splice(i, 1);
			break;	
		}
	}
	updateStopTable(stops);
}


function inStops(stopName) {
	var flag = false;
	stops.forEach(function(stop){
		if (stop.name.toLowerCase() == stopName.toLowerCase()) 
			flag = true;
	});
	return flag;
}


function airportIsAvailable(airportName) {
	var a = null;
	availableStops.forEach(function(stop){
		if (stop.name.toLowerCase() == airportName.toLowerCase()) 
			a = stop;
	});
	return a;
}


function refreshStopTable() {
	$("#stopsTable tbody").remove();
	$("#stopsTable").append("<tbody></tbody>")
}


function updateStopTable(stops) {
	refreshStopTable();
	stops.forEach(function(stop){
		insertStopRow(stop);
	});
}


function insertStopRow(airport) {
	var html = `
		<tr>
			<td>${airport.name}</td>
			<td>${airport.city}</td>
			<td>${airport.state}</td>
		</tr>`;
	$("#stopsTable tbody").append(html);	
}


function configureDatepickers() {
	$('#takeoff').datepicker({
        uiLibrary: 'bootstrap4',
        format: 'dd-mm-yyyy' 
    });
	
	$('#landing').datepicker({
        uiLibrary: 'bootstrap4',
        format: 'dd-mm-yyyy' 
    });
}
