function checkAuth() {
	localStorage.setItem("lastPage", window.location.href);
	
	
	if (localStorage.getItem("token") != null) {
		
		if (localStorage.getItem("admin") == null) {
			$.ajax({
				url: "http://localhost:8080/airlineAdmin/details",
				method: "GET",
				dataType: "json",
				async: false,
				success: function(result) {
					localStorage.setItem("admin", JSON.stringify(result));
				} 
			});
		} 

	} else {
		window.location = "http://localhost:8080/html/login.html";
	}


};