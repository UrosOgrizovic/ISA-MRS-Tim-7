function showMap(lat, lng) {
    var myLatLng = {lat: lat, lng: lng};
    console.log(myLatLng);
    
    var map = new google.maps.Map(document.getElementById('map2'), {
      zoom: 4,
      center: myLatLng
    });

    var marker = new google.maps.Marker({
      position: myLatLng,
      map: map,
      title: 'Hello World!'
    });
    
    $("#viewAirportLoc").modal("toggle");
}
