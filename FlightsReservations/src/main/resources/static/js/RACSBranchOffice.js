import {loadNavbar} from "./navbar.js"; 
var carsOfBranchOffice = [];
var branchOfficeName = document.getElementById("branch_office_name");
var branchOfficeLongitude = document.getElementById("branch_office_longitude");
var branchOfficeLatitude = document.getElementById("branch_office_latitude");
var branchOfficeCompanyName = document.getElementById("branch_office_company_name");

var carSelect = $("#car_select");
var carIdSelect = $("#car_id_select");
var localStorageBranchOfficeName = localStorage.getItem("branchOfficeName")
var getRACSBranchOfficeLink = "/racssBranchOffices/findByName/" + localStorageBranchOfficeName;

$(document).ready(function(){
    
	$.ajax({
		url: getRACSBranchOfficeLink,
		method: "GET",
        dataType: "json",
        contentType: "application/json",
        crossDomain: true, 
		success: function (result) {
            
            if (result != null) {
                
                branchOfficeName.innerHTML = result.name;
                carsOfBranchOffice = result.cars;
                carsOfBranchOffice.sort(compareCars);
                branchOfficeName.innerHTML = result.name;
                branchOfficeLongitude.innerHTML = result.longitude;
                branchOfficeLatitude.innerHTML = result.latitude;
                branchOfficeCompanyName.innerHTML = result.racscompanyName;

                setInputs();
            }

			
        },
        error: function(err) {
            console.log(err);
        }
    });	
    
    loadNavbar('RACSHomepageNavItem');
});

function setInputs(){
    
	

    carSelect.empty();
    carIdSelect.empty();

    var idx = 0;
    
    for (var car of carsOfBranchOffice) {
        idx++;
        carIdSelect.append("<option>"+car.id+"</option>");
        carSelect.append("<option>"+ "#" + idx + " " + car.manufacturer + " " + car.name + " " + car.color + " " + car.yearOfManufacture + " " + car.pricePerHour +"</option>");
    }
}

// used for sorting cars array
function compareCars(a, b) {
    if (a.id < b.id) return -1;
    else if (a.id > b.id) return 1;
    else return 0;
}