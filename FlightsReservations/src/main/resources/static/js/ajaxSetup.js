var token = localStorage.getItem("token");
if (token != null) {
    $.ajaxSetup({
        headers: {
            "Authorization": "Bearer " + token
        }
    });
}
