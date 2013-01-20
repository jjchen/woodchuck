function getReadings(loadingBox) {
	if(loadingBox == null) { 
		$("ul#reading_list").prepend(generateLoadingBox());
		$("ul#reading_list .loading").fadeIn(200);
	}
	$.ajax({
		type : "POST",
		url : "/reading",
		data : {
			mode : "getReadings",
			fbid : fbid
		}
	}).done(function(msg) {
		$("ul#reading_list li").fadeOut(200, function() {
			$("ul#reading_list li").remove();
			var readings = JSON.parse(msg)		
			for(var i in readings) {
				var r = readings[i];
				$("ul#reading_list").append(generateReadingBox(r.id, r.name, r.dueDate, r.currentChunk, r.totalChunks, r.currentChunkText));
				$("ul#reading_list li#reading" + r.id).fadeIn(200);
			}
		});
		
	});
}

function generateLoadingBox() {
	return '<li class="loading" style="display: none;"> \
		<h4>Loading...</h4> \
		<div class="clear"></div> \
	</li>';
}


function generateReadingBox(id, name, dueDate, chunkCompleted, chunkTotal, chunkTest) {
	return '<li class="reading" style="display: none;" id="reading' + id + '"> \
		<h4>' + name + '</h4> \
		<div class="reading_due_date">' + getTimeDiff(new Date(dueDate)) + '</div> \
		<div class="reading_chunks"> \
			<span class="completed">' + chunkCompleted + '</span>/<span class="total">' + chunkTotal + '</span> \
		</div> \
		<div class="clear"></div> \
		<p>' + chunkText + '</p> \
	</li>';
}



function getTimeDiff(time) {
 
	var now = new Date();
	var diff = now.getTime() - time.getTime();
 
	var timeDiff = getTimeDiffDescription(diff, 'day', 86400000);
	if (!timeDiff) {
		timeDiff = getTimeDiffDescription(diff, 'hour', 3600000);
		if (!timeDiff) {
			timeDiff = getTimeDiffDescription(diff, 'minute', 60000);
			if (!timeDiff) {
				timeDiff = getTimeDiffDescription(diff, 'second', 1000);
				if (!timeDiff) {
					timeDiff = 'just now';
				}
			}
		}
	}
 
	return timeDiff;
 
}

function getTimeDiffDescription(diff, unit, timeDivisor) {
 
	var unitAmount = (diff / timeDivisor).toFixed(0);
	if (unitAmount > 0) {
		return unitAmount + ' ' + unit + (unitAmount == 1 ? '': 's') + ' ago';
	} else if (unitAmount < 0) {
		return 'in ' + Math.abs(unitAmount) + ' ' + unit + (unitAmount == 1 ? '' : 's');
	} else {
		return null;
	}
 
}
