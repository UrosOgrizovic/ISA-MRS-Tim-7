var mapa = new Map();
var name_select = $("#nameSelect");
var id_select = $("#idSelect");


$(document).ready(function(){
	$.ajax({
		url: "http://localhost:8080/hotel/getAll",
		method: "GET",
		dataType: "json",
		crossDomain: true,
		success: function (result) {
			for (var i = 0; i < result.length; i++) {
                mapa[result[i].id] = result[i];
                console.log("name_select:"+ name_select);
                name_select.append("<option>"+result[i].name+"</option>");
                id_select.append("<option>" + result[i].id + "</option>");
                
			}
			
			/*
			console.log("name_select:"+ name_select);
			for(var i in name_select)
			{
				console.log("name_select[i]:"+ name_select[i]);
			}
			console.log("id_select: " + id_select);*/
			set_inputs();
		}
	});	
});

function update_hotel() {
	myForm = document.getElementById("editHotelForm");
	if (!validate_inputs()) {
		alert("Inputs are invalid!");
		return;
	}
	
    var key = idSelect.val();

	mapa[key].name = myForm.name.value;
	mapa[key].address = myForm.address.value;
	mapa[key].promoDescription = myForm.promoDescription.value;
		
	$.ajax({
		url: "http://localhost:8080/hotel/update",
		method: "PUT",
		contentType: "application/json",
		dataType: "json",	
		data: JSON.stringify(mapa[key]),
		success: function(result) {
		}
	});
}

function validate_inputs()
{
	var ids = ["name", "address", "promoDescription"];
	var flag = true;
	
	for (var i = 0; i < ids.length; i++)
		if ($("#"+ids[i]).val().trim() == "") {
			flag = false;
			break;
		}
	return flag;
}

function set_inputs(){
	console.log("id_select.val(): ");
    var key = id_select.val();
	console.log(key);
	$("#name").val(mapa[key].name);
	$("#address").val(mapa[key].address);
	$("#promoDescription").val(mapa[key].promoDescription);
}

/*  if the 2nd item in one selectbox is selected,
then the 2nd item is selected in all other
selectboxes as well
*/
$(function(){
    $('#name_select').change(function(){ // when one changes
        var idx = $('#name_select').prop('selectedIndex');
        document.getElementById('id_select').selectedIndex = idx;
        
        setInputs();
    })
    $('#id_select').change(function(){ // when one changes
        var idx = $('#id_select').prop('selectedIndex');
        document.getElementById('name_select').selectedIndex = idx;
        
        setInputs();
    })
})
