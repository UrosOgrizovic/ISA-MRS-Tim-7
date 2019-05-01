var searchResults = [];
var pageNumber = 0;
var pageSize = 1;
var lastRequest = null;



$(document).ready(function() {
	$("#type-search").change(updateSearchForm);
	$("#searchBtn").click(search);
	$("#prevPageBtn").click(prevPage);
	$("#nextPageBtn").click(nextPage);

	hidePaginationButtons();
	configureDatepickers();
	updateSearchForm();
});



function search() {
	pageNumber = 0;
	var tripType = $("#type-search").val();
	var passengerNum = $("#passengerNum-search").val();
	
	var seatType = $("#class-search").val();
	if (seatType == "ANY")
		seatType = null;

	if (tripType == "OneWay") 
		oneWaySearch(tripType, seatType, passengerNum);
	else if (tripType == "RoundTrip")
		roundTripSearch(tripType, seatType, passengerNum);
	else if (tripType)
		multiTripSearch(tripType, seatType, passengerNum);
}



function oneWaySearch(tripType, seatType, passengerNum) {
	var fromCity = $("#from-search-one").val();
	var toCity = $("#to-search-one").val();
	var takeoffDate = $("#from-date-search-one").val(); 

	var request = new Object();
	request.seatType = seatType;
	request.tripType = tripType;
	request.numOfPassengers = passengerNum;
	request.pageNumber = pageNumber;
	request.resultCount = pageSize;
	
	request.queries = [];
	query = new Object();
	query.startCity = fromCity;
	query.endCity = toCity;
	query.takeoffDate = takeoffDate;
	request.queries.push(query);

	sendRequest(request);
}



function roundTripSearch(tripType, seatType, passengerNum) {
	var fromCity = $("#from-search-one").val();
	var toCity = $("#to-search-one").val();
	var takeoffDate = $("#from-date-search-one").val();
	var returnDate = $("#to-date-search-one").val(); 

	var request = new Object();
	request.seatType = seatType;
	request.tripType = tripType;
	request.numOfPassengers = passengerNum;
	request.pageNumber = pageNumber;
	request.resultCount = pageSize;
	
	request.queries = [];
	queryOne = new Object();
	queryOne.startCity = fromCity;
	queryOne.endCity = toCity;
	queryOne.takeoffDate = takeoffDate;
	request.queries.push(queryOne);

	queryTwo = new Object();
	queryTwo.startCity = toCity;
	queryTwo.endCity = fromCity;
	queryTwo.takeoffDate = returnDate;
	request.queries.push(queryTwo);

	sendRequest(request);
}



function multiTripSearch(tripType, seatType, passengerNum) {
	var request = new Object();
	request.seatType = seatType;
	request.tripType = tripType;
	request.numOfPassengers = passengerNum;
	request.pageNumber = pageNumber;
	request.resultCount = pageSize;
	
	request.queries = [];
	queryOne = new Object();
	queryOne.startCity = $("#from-search-one").val();
	queryOne.endCity = $("#to-search-one").val();
	queryOne.takeoffDate = $("#from-date-search-one").val();
	request.queries.push(queryOne);

	queryTwo = new Object();
	queryTwo.startCity = $("#from-search-two").val();
	queryTwo.endCity = $("#to-search-two").val();
	queryTwo.takeoffDate = $("#from-date-search-two").val();
	request.queries.push(queryTwo);

	sendRequest(request);
}



function sendRequest(request) {
	$.ajax({
		url: "http://localhost:8080/flights/search",
		method: "POST",
		contentType: "application/json",
		dataType: "json",
		data: JSON.stringify(request),
		success: function(result) {
			if (result.length > 0) {
				searchResults = result;
				lastRequest = request;
				showResults();
			} else {
				if (pageNumber > 0)
					pageNumber--;
				alert("No results!");
			}
		},
		error: function(error) {
			alert("Bad request says server!");
		}
	});
}



function updateSearchForm() {
	var type = $("#type-search").val();
	
	if (type == "OneWay") {
		$("#search-row-two").hide();
		$("#to-date-search-one-div").hide();
	}
	else if (type == "RoundTrip") {
		$("#search-row-two").hide();
		$("#to-date-search-one-div").show();
	} 
	else {
		$("#search-row-two").show();
		$("#to-date-search-one-div").hide();
		$("#to-date-search-two-div").hide();
	}
}



function configureDatepickers() {
	var ids = ["#from-date-search-one", "#from-date-search-two", "#to-date-search-one", "#to-date-search-two"];
	
	ids.forEach(function(id){
		$(id).datepicker({
			 uiLibrary: 'bootstrap4',
			 format: 'dd-mm-yyyy' 
		});
	});
}

function hidePaginationButtons() {
	$("#prevPageBtn").hide();
	$("#nextPageBtn").hide();
}

function showPaginationButtons() {
	$("#prevPageBtn").show();
	$("#nextPageBtn").show();
}

function prevPage() {
	if (pageNumber == 0) {
		alert("Already on first page!");
		return;
	}

	pageNumber--;
	lastRequest.pageNumber = pageNumber;
	sendRequest(lastRequest);
}

function nextPage() {
	pageNumber++;
	lastRequest.pageNumber = pageNumber;
	sendRequest(lastRequest);	
}