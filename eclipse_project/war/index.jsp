<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript"
	src="//api.filepicker.io/v1/filepicker.js"></script>
<script type="text/javascript" src="js/wc.js"></script>
<script type="text/javascript" src="js/facebook_login.js"></script>
<script type="text/javascript" src="js/backstretch.js"></script>
<link
	href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900|Oswald:400,300,700'
	rel='stylesheet' type='text/css'>
<link href='assets/style.css' rel='stylesheet'>
<script type="text/javascript">
	function sendLink(url, filename, type) {
		$("ul#reading_list").prepend(generateLoadingBox());
		$("ul#reading_list .loading").fadeIn(200);
		
		$.ajax({
			type : "POST",
			url : "/reading",
			data : {
				mode : "new",
				name : filename,
				location : url,
				fbid : fbid,
				type : type,
			}
		}).done(function(msg) {
			console.log(msg);
			getReadings(false);
		});
	}
	$("document").ready(function() {
		$.backstretch([ "assets/books.jpg" ]);
	});

	function filePickerResp(event) {
		for ( var i = 0; i < event.fpfiles.length; i++) {
			var fpfile = { url : event.fpfiles[i].url }
			filepicker.stat(fpfile, {
				filename : true,
				mimetype : true
			}, function(metadata) {
				sendLink(fpfile.url, metadata.filename, metadata.mimetype);
			});
		}
	}
</script>
<title>Woodchuck</title>
</head>
<body>
	<div id="fb-root"></div>

	<div id="container">
		<h1>Woodchuck</h1>
		<div id="username">
			<a href="javascript:void(0)">not logged in.</a>
		</div>
		<div class="clear"></div>

		<div id="wrapper">
			<div class="filepicker_form right">
				<input type="filepicker" class="filepicker_button"
					data-fp-apikey="ArDRHUpYoQQ6Yg5Qsmoxbz"
					data-fp-mimetypes="text/*,application/pdf"
					data-fp-container="modal" data-fp-multiple="true"
					data-fp-services="COMPUTER,DROPBOX,GOOGLE_DRIVE,URL"
					onchange="filePickerResp(event)" />
					We support PDF and text documents, as well as any web page on the Internet.
			</div>
			<h2>Start Reading.</h2>
            <p>
                Who wants to read academic papers? For those of you who are slightly lazy (no worries, I'm definitely in your boat), here's a sweet app to help guide you in the right direction.  Woodchuck breaks down the text into smaller, more manageable bites and sends it to you via text or Android app. Feel free to login and try it out!
            </p>
					
			<div class="clear"></div>

			<div id="content">
				<div id="left">
					<ul id="reading_list">
					</ul>
				</div>
				<div id="right">
					<div id="reading_details" style="display: none;">
						<h4></h4>
						<div id="reading_details_list"></div>
						<div id="reading_action"></div>
					</div>
				</div>
				
				<div class="clear"></div>
			</div>

		</div>
	</div>

</body>
</html>
