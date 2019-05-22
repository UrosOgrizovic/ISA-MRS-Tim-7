// get airline that current admin manages (mock but should be from storage)
var airline = "airline1";

$(document).ready(function(){
	$("#addAirportBtn").click(addAirport);
	
	// ajax call for all airports of that airline
	// if success
	// 		update table with results
	getAirports(airline);	
});

function getAirports(airline) {
	$.ajax({
		url: "http://localhost:8080/airlines/airports/" + airline,
		method: "GET",
		dataType: "json",
		success: function (result) {
			populateTable(result);
		},
		statusCodes: {
			400: function() { alert("400 Bad request.")},
			500: function() { alert("500 Server error.")}
		}
	});
}

function addAirport() {
	// get name from input
	// if valid
	// 		make ajax put request
	// 		update table
	var airport = $("#searchAirportName").val();
	
	if (airport != "") {
		$.ajax({
			url: "http://localhost:8080/airlines/addAirport/" + airline + "/" + airport,
			method: "PUT",
			async: false,
			success: function (result) {
				insertRow(result);
				$("#closeAddAirportModal").click();
			},
			statusCodes: {
				400: function() { alert("400 Bad request.")},
				500: function() { alert("500 Server error.")}
			}
		});
	}
}

function populateTable(airports) {
	airports.forEach( function(airport){
		insertRow(airport);
	});
}

function insertRow(airport) {
	var html = `
		<tr>
			<td>${airport.name}</td>
			<td>${airport.city}</td>
			<td>${airport.state}</td>
		</tr>`;
	$("#airportTable tbody").append(html);	
}
