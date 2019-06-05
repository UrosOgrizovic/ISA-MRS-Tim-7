$(document).ready(function(){
	$("#dayPicker").datepicker({
		uiLibrary: "bootstrap4",
		format: "dd-MM-yyyy",
		startView: "days"
	});

	$("#monthPicker").datepicker({
		uiLibrary: "bootstrap4",
		format: "MM-yyyy",
		startView: "month"
	});

	$("#yearPicker").datepicker({
		uiLibrary: "bootstrap4",
		format: "yyyy",
		minViewMode: "years",
		startView: "years"
	});
});

