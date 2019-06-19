var airline = null;

$(document).ready(function(){
	checkAuth();
	airline = JSON.parse(localStorage.getItem("admin")).airlineName;

	$("#dateFrom").datepicker({
		uiLibrary: "bootstrap4",
		format: "dd-mm-yyyy",
	});

	$("#dateTo").datepicker({
		uiLibrary: "bootstrap4",
		format: "dd-mm-yyyy",
	});

	getDaily();
	getWeekly();
	getMonthly();
});


function getDaily() {
	$.ajax({
		url: "/airlines/reports/day",
		method: "GET",
		data: "json",
		success: function(result) {
			displayChart("daily", result, "Daily");
		}
	});
}


function getWeekly() {
	$.ajax({
		url: "/airlines/reports/week",
		method: "GET",
		data: "json",
		success: function(result) {
			displayChart("weekly", result, "Weekly");
		}
	});
}

function getMonthly() {
	$.ajax({
		url: "/airlines/reports/month",
		method: "GET",
		data: "json",
		success: function(result) {
			displayChart("monthly", result, "Monthly");
		}
	});
}




function getIncome() {
	var dateFrom = $("#dateFrom").val();
	var dateTo = $("#dateTo").val();

	$.ajax({
		url: "/airlines/reports/" + dateFrom + "/" + dateTo,
		method: "GET",
		data: "json",
		success: function(result) {
			displayChart("income", result, "Income");
		}
	});
}


function displayChart(id, data, desc) {
	var ctx = document.getElementById(id).getContext('2d');
	var myChart = new Chart(ctx, {
	    type: 'line',
	    data: {
	        labels: Object.keys(data),
	        datasets: [{
	            label: desc,
	            data: Object.values(data),
	            fill: true
	        }]
	    },
	    options: {
	    	responsive: true,
	        scales: {
	            yAxes: [{
	                ticks: {
	                    beginAtZero: true
	                }
	            }]
	        }
	    }
	});
}	

