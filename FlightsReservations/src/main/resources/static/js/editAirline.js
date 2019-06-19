var airline = null;

$(document).ready(function(){
	checkAuth();
	airline = JSON.parse(localStorage.getItem("admin")).airlineName;

	$.ajax({
		url: "/airlines/" + airline,
		method: "GET",
		dataType: "json",
		success: function (result) {
			setInputs(result);
			initMap(result);
		}
	});	
});

function updateAirline() {
	var promo = $("#promoDesc").val();
	if (promo != "") {
		$.ajax({
			url: "/airlines/update/" + airline + "/" + promo,
			method: "PUT",
			success: function(result) {
				toastr.success("Update successfull.");
			},
			error: function(result) {
				toastr.error("Something is wrong with your request.(update details)");
			}
		});
	} else {
		toastr.info("Enter all input fields.");
	}
}

function setInputs(airline){
	$("#name").val(airline.name);
	$("#promoDesc").val(airline.promoDescription);
	$("#numOfVotes").val(airline.numberOfVotes);
	$("#avgScore").val(airline.averageScore);
}

function initMap(result) {
    var myLatLng = {lat: result.latitude, lng: result.longitude};

    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 4,
      center: myLatLng
    });

    var marker = new google.maps.Marker({
      position: myLatLng,
      map: map,
      title: 'Hello World!'
    });
  }

