import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken, parseJwt } from "./securityStuff.js";

var getRACSAverageRating = "/racsAdmin/getAverageRACSRating";
var getRevenueForPeriod = "/racss/getRevenueForPeriod";
var getNumberOfCarReservationsOfRacsDaily = "/racss/getNumberOfCarReservationsOfRacsDaily";
var getAverageRatingForEachCarOfRacs = "/racss/getAverageRatingForEachCarOfRacs";

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

var email = parseJwt(token).sub;

// if user isn't admin
if (!checkRoleFromToken(token, "ROLE_ADMIN")) {
    history.go(-1);
}


var today = new Date();
var twoMonthsAgo = new Date();
twoMonthsAgo.setMonth(today.getMonth() - 2);

var racsRevenuePeriodStartDate = twoMonthsAgo;
var racsRevenuePeriodEndDate = today;


$(document).ready(function(){
    loadNavbar('RACSHomepageNavItem');

    $("#viewRACSAverageRating").on('click', function(e) {
        
        e.preventDefault();
        
        $("#showRACSAverageRating").css("display", "none");    


        $("#error").remove();
        
        $.ajax({
            url: getRACSAverageRating + "/" + email,
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            headers: { "Authorization": "Bearer " + token}, 
            success: function(averageRating) {
                $("#showRACSAverageRating").css("display", "inline-block");
                $("#showRACSAverageRating").html("<h3>"+averageRating+"</h3>");
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
                console.log(error);
            }
        });
    });

    $("#viewAverageRatingForEachCar").on('click', function(e) {
        $("#showAverageRatingForEachCar").css("display", "none");
        e.preventDefault();

        $("#error").remove();
        
        $.ajax({
            url: getAverageRatingForEachCarOfRacs + "/" + email,
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            headers: { "Authorization": "Bearer " + token}, 
            success: function(carNameAverageRating) {
                $("#showAverageRatingForEachCar").css("display", "block");
                displayCars(carNameAverageRating);
                
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
                console.log(error);
            }
        });
    });

    $("#viewCarReservationCharts").on('click', function(e) {
        $("#showCarReservationCharts").css("display", "none");
        e.preventDefault();

        $("#error").remove();

        $.ajax({
            url: getNumberOfCarReservationsOfRacsDaily + "/" + email + "/" + moment(twoMonthsAgo).format("DD-MM-YYYY HH:mm") + "/" + moment(today).format("DD-MM-YYYY HH:mm"),
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            headers: { "Authorization": "Bearer " + token}, 
            success: function(dateNumberOfReservations) {
                $("#showCarReservationCharts").css("display", "block");
                makeDailyChart(dateNumberOfReservations, "Number of reservations", "showCarReservationCharts", 1, 1, 2, 5);
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
                console.log(error);
            }
        });
    });

    $("#viewRACSRevenueForPeriod").on('click', function(e) {
        $("#showRACSRevenueForPeriod").css("display", "none");
        e.preventDefault();

        $("#error").remove();

        if ($("#startDate").val() != null && $("#startDate").val() != "") 
            racsRevenuePeriodStartDate = $("#startDate").val();
        
        if ($("#endDate").val() != null && $("#endDate").val() != "")
            racsRevenuePeriodEndDate = $("#endDate").val();
        
        console.log(moment(racsRevenuePeriodStartDate).format("DD-MM-YYYY HH:mm"));
        console.log(moment(racsRevenuePeriodEndDate).format("DD-MM-YYYY HH:mm"));

        $.ajax({
            url: getRevenueForPeriod + "/" + email + "/" + moment(racsRevenuePeriodStartDate).format("DD-MM-YYYY HH:mm") + "/" + moment(racsRevenuePeriodEndDate).format("DD-MM-YYYY HH:mm"),
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            headers: { "Authorization": "Bearer " + token}, 
            success: function(dayRevenue) {
                $("#showRACSRevenueForPeriod").css("display", "block");
                makeDailyChart(dayRevenue, "Revenue", "showRACSRevenueForPeriod", 1, 500, 500, 1000);
                console.log(dayRevenue);
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
                console.log(error);
            }
        });
    });
    
    

    $("#startDate").datepicker({
        uiLibrary: 'bootstrap'
    });
    $("#endDate").datepicker({
        uiLibrary: 'bootstrap'
    });

});

/**
 * 
 * @param {*} data - values that will be plotted on the y axis
 * @param {*} yAxisLabel - semantics of the data param
 * @param {*} idOfParentElement - canvas on which to draw. All required canvases are defined in RACSReports.html.
 * @param {*} xAxisStep - stepSize for x axis (see chart.js docs for stepSize explanation)
 * @param {*} yAxisStep - stepSize for y axis
 * @param {*} yAxisPadding - distance between max value and top of chart, because it's prettier if there's some space left above the max value
 * @param {*} ceilToNearest - number to ceil to, because it's prettier to have the top of the chart at e.g. 10000 than to have it at 9232
 */
function makeDailyChart(data, yAxisLabel, idOfParentElement, xAxisStep, yAxisStep, yAxisPadding, ceilToNearest) {
    
    var labels = Object.keys(data);
    var newLabel;
    var maxYAxisValue = 0;
    for (var label of labels) {
        newLabel = label.replace(new RegExp("-", 'g'), "/");
        data[newLabel] = data[label];
        delete data[label];
    }
    
    labels = Object.keys(data);

    var values = [];
    for (var label of labels) {
        if (data[label] > maxYAxisValue) maxYAxisValue = data[label];
        values.push(data[label]);
    }
    
    var timeFormat = "DD/MM/YYYY";
    var startDate = moment(moment(labels[0], timeFormat).subtract(6, 'days')).format(timeFormat);
    var endDate = moment(moment(labels[labels.length - 1], timeFormat).add(6, 'days')).format(timeFormat);
    
    

    var ctx = document.getElementById(idOfParentElement).getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: yAxisLabel,
                data: values,
                backgroundColor: 'rgba(255, 99, 132)',
                borderColor: 'rgba(255, 99, 132)',
                borderWidth: 1
            }]
        },
        options: {
            responsive:true,
            scales: {
                xAxes: [{
                    type: "time",
                    time: {
                        format: timeFormat,
                        unit: 'day',
                        stepSize: xAxisStep,
                        minUnit: 'day',
                        min: startDate,
                        max: endDate,
                        tooltipFormat: 'll'
                    },
                    scaleLabel: {
                        display:     true,
                        labelString: 'Date'
                    },
                    ticks: {
                    	//autoSkip: true,
                        maxTicksLimit: 30
                    }

                }], 
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        stepSize: yAxisStep,
                        max: Math.ceil((maxYAxisValue + yAxisPadding)/ceilToNearest)*ceilToNearest 
                    },
                }]
            },
            pan: {
	            enabled: true,
	            mode: 'x',
	        },
	        zoom: {
	        	enabled: true,
	        	mode: 'x'
	        }
        }
    });
}

function displayCars(cars) {
    cars = sortCars(cars);
    var text = "<table style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\"><thead><th>Car</th><th>Average rating</th></thead>";
    var carsKeys = Object.keys(cars);
    text += "<tbody>";
    for (var car of carsKeys) {
        text += "<tr><td>" + car + "</td><td>" + cars[car] + "</td>" + "</tr>";
    }
    text += "</tbody>";
    $("#showAverageRatingForEachCar").html(text);
}

function sortCars(cars) {
    var newCars = {};
    var carsKeys = Object.keys(cars);
    carsKeys.sort();
    for (var car of carsKeys) {
        newCars[car] = cars[car];
    }
    return newCars;
}