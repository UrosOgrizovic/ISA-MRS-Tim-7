function checkAuth() {
	localStorage.setItem("lastPage", window.location.href);
	
	
	if (localStorage.getItem("token") != null) {
		
		if (localStorage.getItem("admin") == null) {
			$.ajax({
				url: "/airlineAdmin/details",
				method: "GET",
				dataType: "json",
				async: false,
				success: function(result) {
					localStorage.setItem("admin", JSON.stringify(result));
				} 
			});
		} 

	} else {
		window.location = "/html/login.html";
	}
};


function logout() {
	localStorage.clear();
	localStorage.setItem("lastPage", window.location.href);
	window.location = "/html/login.html";
}