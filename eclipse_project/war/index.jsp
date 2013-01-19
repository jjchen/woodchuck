<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript" src="//api.filepicker.io/v1/filepicker.js"></script>
	<script type="text/javascript" src="js/facebook_login.js"></script>
	<script type="text/javascript" src="js/backstretch.js"></script>
	<link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900|Oswald:400,300,700' rel='stylesheet' type='text/css'>
	<link href='assets/style.css' rel='stylesheet'>
	<script type="text/javascript">
        function sendLink(url, filename, type) {
        	$.ajax({
                type: "POST",
                url: "/reading",
                data: {
                    mode: "new",
                    name: filename,
                    location: url,
                    fbid: fbid,
                    type: type,
                }
            }).done(function (msg) {
                if(msg != "OK") {
                	console.log(msg);
                }
            });
        }
        $("document").ready(function() {
        	$.backstretch(["assets/books.jpg"]);
        });
    </script>
	<title>Insert title here</title>
</head>
<body>
	<div id="fb-root"></div>
	
	<div id="container">
	<h1>Hello Peter and Junjun! Welcome to woodchuck land! Hi</h1>
	
	<input type="filepicker" data-fp-apikey="ArDRHUpYoQQ6Yg5Qsmoxbz"
		data-fp-mimetypes="text/*,application/pdf" data-fp-container="modal"
		data-fp-multiple="true"
		data-fp-services="COMPUTER,DROPBOX,GOOGLE_DRIVE"
		onchange="out='';
for(var i=0;i<event.fpfiles.length; i++)
{
	var fpfile={url: event.fpfiles[i].url}
	filepicker.stat(fpfile, {filename: true, mimetype: true}, function(metadata) {
		var obj = JSON.parse(metadata);
		sendLink(event.fpfiles[i].url, obj.filename, obj.mimetype);
	});
	out+=event.fpfiles[i].url;
	out+=' '};alert(out)">
	
	</div>

</body>
</html>
