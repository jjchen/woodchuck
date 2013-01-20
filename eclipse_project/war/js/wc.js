funciton getReadings() {	
	$.ajax({
		type : "POST",
		url : "/reading",
		data : {
			mode : "getReadings",
			fbid : fbid
		}
	}).done(function(msg) {
		var readings = JSON.parse(msg);
		console.log(readings);
	});

}