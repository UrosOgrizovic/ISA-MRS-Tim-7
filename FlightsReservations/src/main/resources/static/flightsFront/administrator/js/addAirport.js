var airline = null;

$(document).ready(function(){
	checkAuth();
	airline = JSON.parse(localStorage.getItem("admin")).airlineName;
	$("#addAirportBtn").click(addAirport);
	getAirports();	
});

function getAirports() {
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
