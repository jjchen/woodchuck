var readings = new Array();
function getReadings(loadingBox) {
	if(loadingBox == null) { 
		$("ul#reading_list").prepend(generateLoadingBox());
		$("ul#reading_list .loading").fadeIn(200);
	}
	$("#reading_details").fadeOut(300);
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
			var readings_tmp = JSON.parse(msg)
			for(var i in readings_tmp) {
				var r = readings_tmp[i];
				readings[r.id] = r;
				$("ul#reading_list").append(generateReadingBox(r.id, r.name, r.dueDate, r.currentChunk, r.totalChunks, r.currentChunkText));
				$("ul#reading_list li#reading" + r.id).fadeIn(200);
			}
			
			$("ul#reading_list li").click(function() {
				var id = parseInt($(this).attr("id").substring(7));
				var r = readings[id];
				
				clearAction();
				
				$("ul#reading_list li").removeClass("active");
				$(this).addClass("active");
				
				$("#reading_details h4").html(r.name);
				$("#reading_details div").html("");
				$("#reading_details div#reading_details_list").append(generateReadingMeta("Created on", r.createdDate));
				$("#reading_details div#reading_details_list").append(generateReadingMeta("Due date", r.dueDate));
				$("#reading_details div#reading_details_list").append(generateReadingMeta("Notif. frequency", r.frequency + " minutes"));
				
				prepareChangeDueDate(r.id, r.dueDate);
			});
		});
	});
	
	
}

function generateLoadingBox() {
	return '<li class="loading" style="display: none;"> \
		<h4>Loading...</h4> \
		<div class="clear"></div> \
	</li>';
}


function generateReadingBox(id, name, dueDate, chunkCompleted, chunkTotal, chunkText) {
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

function generateReadingMeta(key, value) {
	return '<div class="reading_meta"><b>' + key + '</b><span>' + value + '</span></div>';
}

function generateOptionLink(id, value) {
	return '<div class="reading_option"><b></b><span><a href="javascript:void(0)" id="' + id + '">' + value + '</a></span></div>';
}

function prepareChangeDueDate(id, dueDate) {
	$("#reading_details div#reading_details_list").append(generateOptionLink("changeDueDate", "Edit due date"));
	$("#changeDueDate").click(function() {
		clearAction(function() {
			$("#reading_action").html("<h5>Edit Due Date</h5>");
			$("#reading_action").append("<form id=\"editDueDateForm\"><input type='text' id='editDueDateInput' value='" + dueDate +"' /></form>");
			$("#reading_action").fadeIn(150);
			
			$("form#editDueDateForm").submit(function(e) {
				var newDueDate = $(this).find("input").val();
				$.post("/reading", {"mode" : "editDueDate", "id" : id, "dueDate" : newDueDate}, function(event) {
					console.log(event);
					getReadings();
				})
				
				e.preventDefault();
				return false;
			});
		});
	});
	
	$("#reading_details").fadeIn(300);
}

function clearAction(callback) {
	$("#reading_action").fadeOut(150, function() {
		$("#reading_action").html("");
		if(callback != null && callback != undefined) { callback.call(document.body); }
	});
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
