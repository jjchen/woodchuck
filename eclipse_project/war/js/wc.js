function getReadings() {	
	$.ajax({
		type : "POST",
		url : "/reading",
		data : {
			mode : "getReadings",
			fbid : fbid
		}
	}).done(function(msg) {
		var readings = JSON.parse(msg);
		for(var i in readings) {
			var r = readings[i];
			$("ul#reading_list").append(generateReadingBox(r.id, r.name, r.dueDate, r.currentChunk, r.totalChunks));
			$("ul#reading_list li#reading" + r.id).fadeIn(200);
		}
	});
}

function generateReadingBox(id, name, dueDate, chunkCompleted, chunkTotal) {
	return '<li class="reading" style="display: none;" id="reading' + id + '> \
		<h4>' + name + '</h4> \
		<div class="reading_due_date">' + dueDate + '</div> \
		<div class="reading_chunks"> \
			<span class="completed">' + chunkCompleted + '</span>/<span class="total">' + chunkTotal + '</span> \
		</div> \
		<div class="clear"></div> \
		<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam a nulla risus.</p> \
	</li>';
}
