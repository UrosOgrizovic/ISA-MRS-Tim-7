var airline = null;

$(document).ready(function(){
	checkAuth();
	airline = JSON.parse(localStorage.getItem("admin")).airlineName;

	$("#dateFrom").datepicker({
		uiLibrary: "bootstrap4",
		format: "dd-MM-yyyy",
	});

	$("#dateTo").datepicker({
		uiLibrary: "bootstrap4",
		format: "dd-MM-yyyy",
	});


	displayDailyChart(1, "daily");
	displayDailyChart(1, "weekly");
	displayDailyChart(1, "monthly");
});


function getDaily() {
	$.ajax({
		url: "http://localhost:8080/airlines/reports/day",
		method: "GET",
		data: "json",
		success: function(result) {
			displayDailyChart(result);
		}
	});
}



function getIncome() {
	var dateFrom = $("#dateFrom").val();
	var dateTo = $("#dateTo").val();

	$.ajax({
		url: "http://localhost:8080/airlines/reports/" + dateFrom + "/" + dateTo,
		method: "GET",
		data: "json",
		success: function(result) {
			displayIncomeChart(result);
		}
	});
}



function displayDailyChart(data, id) {
	var ctx = document.getElementById(id).getContext('2d');
	var myChart = new Chart(ctx, {
	    type: 'line',
	    data: {
	        labels: ["1"],
	        datasets: [{
	            label: '# of Votes',
	            data: [12],
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



function displayIncomeChart(data) {
	var ctx = document.getElementById("income").getContext('2d');
	var myChart = new Chart(ctx, {
	    type: 'line',
	    data: {
	        labels: Object.keys(data),
	        datasets: [{
	            label: 'Income',
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

