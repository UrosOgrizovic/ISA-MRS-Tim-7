import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken, parseJwt } from "./securityStuff.js";

var getRACSAverageRating = "/racsAdmin/getAverageRACSRating";
var getRevenueForPeriod = "/racss/getRevenueForPeriod";
var getNumberOfCarReservationsOfRacsLink = "/racss/getNumberOfCarReservationsOfRacs";
var getAverageRatingForEachCarOfRacs = "/racss/getAverageRatingForEachCarOfRacs";

var token = localStorage.getItem("token");
if (token == null) location.replace("/html/login.html");

var email = parseJwt(token).sub;

// if user isn't admin
if (!checkRoleFromToken(token, "ROLE_ADMIN")) {
    history.go(-1);
}


var today = new moment();
var twoMonthsAgo = new moment();
twoMonthsAgo.subtract(8, "weeks");

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
                $("#showRACSAverageRating").css("display", "inline");
                $(".rate").css("display", "inline");
                displayRACSRating(averageRating);
            }, error: function(error) {
                toastr.error("Could not get average rating of rent-a-car service");
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
                if (carNameAverageRating != null && Object.keys(carNameAverageRating).length > 0) {
                    displayCars(carNameAverageRating);
                } else {
                    toastr.info("No average ratings to display");
                }
                
                
            }, error: function(error) {
                toastr.error("Could not show average rating for each car");
                console.log(error);
            }
        });
    });

    $("#viewCarReservationChartsDaily").on('click', function(e) {
        $("#showCarReservationChartsDaily").css("display", "none");
        $("#showCarReservationChartsWeekly").css("display", "none");
        $("#showCarReservationChartsMonthly").css("display", "none");
        e.preventDefault();

        $("#error").remove();

        
        $("#showCarReservationChartsDaily").css("display", "block");

        $.ajax({
            url: getNumberOfCarReservationsOfRacsLink + "/" + email + "/" + moment(twoMonthsAgo).format("DD-MM-YYYY HH:mm") + "/" + moment(today).format("DD-MM-YYYY HH:mm") 
            + "/" + "day",
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            headers: { "Authorization": "Bearer " + token}, 
            success: function(dateNumberOfReservations) {
                if (dateNumberOfReservations != null && Object.keys(dateNumberOfReservations).length > 0) {
                    makeChart(dateNumberOfReservations, "Number of reservations", "showCarReservationChartsDaily", 1, 1, 2, 5, "day");
                } else {
                    toastr.info("No car reservations to display graph for");
                }
                
            }, error: function(error) {
                toastr.error("Could not display number of car reservations daily graph");
                console.log(error);
            }
        });
    });

    $("#viewCarReservationChartsWeekly").on('click', function(e) {
        $("#showCarReservationChartsDaily").css("display", "none");
        $("#showCarReservationChartsWeekly").css("display", "none");
        $("#showCarReservationChartsMonthly").css("display", "none");
        e.preventDefault();

        $("#error").remove();

       $("#showCarReservationChartsWeekly").css("display", "block");
       $.ajax({
            url: getNumberOfCarReservationsOfRacsLink + "/" + email + "/" + moment(twoMonthsAgo).format("DD-MM-YYYY HH:mm") + "/" + moment(today).format("DD-MM-YYYY HH:mm") 
            + "/" + "week",
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            headers: { "Authorization": "Bearer " + token}, 
            success: function(dateNumberOfReservations) {
                if (dateNumberOfReservations != null && Object.keys(dateNumberOfReservations).length > 0) {
                    makeChart(dateNumberOfReservations, "Number of reservations", "showCarReservationChartsWeekly", 1, 1, 2, 5, "week");
                } else {
                    toastr.info("No car reservations to display graph for");
                }
                
            }, error: function(error) {
                toastr.error("Could not display number of car reservations weekly graph");
                console.log(error);
            }
        });
    });

    $("#viewCarReservationChartsMonthly").on('click', function(e) {
        $("#showCarReservationChartsDaily").css("display", "none");
        $("#showCarReservationChartsWeekly").css("display", "none");
        $("#showCarReservationChartsMonthly").css("display", "none");
        e.preventDefault();

        $("#error").remove();

       $("#showCarReservationChartsMonthly").css("display", "block");
       $.ajax({
            url: getNumberOfCarReservationsOfRacsLink + "/" + email + "/" + moment(twoMonthsAgo).format("DD-MM-YYYY HH:mm") + "/" + moment(today).format("DD-MM-YYYY HH:mm") 
            + "/" + "month",
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            headers: { "Authorization": "Bearer " + token}, 
            success: function(dateNumberOfReservations) {
                if (dateNumberOfReservations != null && Object.keys(dateNumberOfReservations).length > 0) {
                    makeChart(dateNumberOfReservations, "Number of reservations", "showCarReservationChartsMonthly", 1, 1, 2, 5, "month");
                } else {
                    toastr.info("No car reservations to display graph for");
                }
                
            }, error: function(error) {
                toastr.error("Could not display number of car reservations monthly graph");
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
        
        $.ajax({
            url: getRevenueForPeriod + "/" + email + "/" + moment(racsRevenuePeriodStartDate).format("DD-MM-YYYY HH:mm") + "/"
             + moment(racsRevenuePeriodEndDate).format("DD-MM-YYYY HH:mm"),
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            headers: { "Authorization": "Bearer " + token}, 
            success: function(dayRevenue) {
                if (dayRevenue != null && Object.keys(dayRevenue).length > 0) {
                    $("#showRACSRevenueForPeriod").css("display", "block");
                    makeChart(dayRevenue, "Revenue", "showRACSRevenueForPeriod", 1, 500, 500, 1000, "day");
                } else {
                    toastr.info("No revenue to display graph for");
                }
                
            }, error: function(error) {
                toastr.error("Could not display revenue graph");                
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
 * @param {*} unit - day, month or week
 */
function makeChart(data, yAxisLabel, idOfParentElement, xAxisStep, yAxisStep, yAxisPadding, ceilToNearest, unit) {
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
    var startDate = moment(moment(getMinMaxDate(labels, "min"), timeFormat)).format(timeFormat);
    var endDate = moment(moment(getMinMaxDate(labels, "max"), timeFormat)).format(timeFormat);
    
    
    
    

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
                        unit: unit,
                        stepSize: xAxisStep,
                        minUnit: unit,
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

// dates have to be changed from DD-MM-YYYY to MM-DD-YYYY, because js reads dates like Americans do
function getMinMaxDate(dateStrings, minOrMax) {
    var dates = [];
    var maxDate = new Date();
    var minDate = new Date();
    
    maxDate.setFullYear(maxDate.getFullYear() - 100);
    minDate.setFullYear(minDate.getFullYear() + 100);

    for (var d of dateStrings) {
        dates.push(moment(d, "DD-MM-YYYY").toDate());
    }

    for (var d of dates) {
        if (d < minDate) {
            minDate = d;
        }
        if (d > maxDate) {
            maxDate = d;
        }
    }

    if (minOrMax == "min") {
        return minDate;
    } else {
        return maxDate;
    }
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

function displayRACSRating(rating) {
    if (rating >= 4.5) {
        document.getElementById("avgstar5").checked = true;
    } else if (rating >= 3.5) {
        document.getElementById("avgstar4").checked = true;
    } else if (rating >= 2.5) {
        document.getElementById("avgstar3").checked = true;
    } else if (rating >= 1.5) {
        document.getElementById("avgstar2").checked = true;
    } else {
        document.getElementById("avgstar1").checked = true;
    }
    document.getElementById("avgscore").innerHTML = "("+rating.toString().match(/^-?\d+(?:\.\d{0,2})?/)[0]+")";
}
