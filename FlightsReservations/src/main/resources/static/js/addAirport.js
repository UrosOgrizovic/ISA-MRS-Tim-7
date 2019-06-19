var airline = null;

$(document).ready(function(){
	checkAuth();
	airline = JSON.parse(localStorage.getItem("admin")).airlineName;
	$("#addAirportBtn").click(addAirport);
	getAirports();	
});

function getAirports() {
	$.ajax({
		url: "/airlines/airports/" + airline,
		method: "GET",
		dataType: "json",
		success: function (result) {
			populateTable(result);
		},
		error: function(result) {
			toastr.error("Something is wrong with your request.(get airports)");
		}
	});
}

function addAirport() {
	var airport = $("#searchAirportName").val();
	
	if (airport != "") {
		$.ajax({
			url: "/airlines/addAirport/" + airline + "/" + airport,
			method: "PUT",
			async: false,
			success: function (result) {
				insertRow(result);
				$("#closeAddAirportModal").click();
				toastr.success("Airport added.");
			},
			error: function(result) {
				toastr.error("Something is wrong with your request.(add airport)");
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
			<td><button type="button" class="btn btn-primary" onclick="showMap(${airport.latitude}, ${airport.longitude})">Show</button>
		</tr>`;
	$("#airportTable tbody").append(html);	
}
