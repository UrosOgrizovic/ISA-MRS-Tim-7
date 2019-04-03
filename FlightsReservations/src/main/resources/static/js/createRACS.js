var addRACSLink = "/racss/add";

$(document).ready(function(){
    $("#createRACSForm").on('submit', function(e) {
        e.preventDefault();
        
        racs = {};
        racs.name = document.getElementById("name").value;
        racs.address = document.getElementById("address").value;
        racs.description = document.getElementById("description").value;
        racs.pricelist = [];
        racs.cars = [];
        racs.branchOffices = [];
        
        $("#addedSuccessfully").remove();
        $("#error").remove();
        console.clear();
        console.log("AAAAAAAAA");
        $.ajax({
            url: addRACSLink,
            method: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(racs),
            success: function(result) {
                console.log(result);
                $(document.documentElement).append("<h3 id=\"addedSuccessfully\">Rent-a-car service addedd successfully</h3>");
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
                console.log(error);
            }
        });

    });
});