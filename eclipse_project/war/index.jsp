<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript" src="//api.filepicker.io/v1/filepicker.js"></script>
	<script type="text/javascript" src="js/facebook_login.js"></script>
	<script type="text/javascript">
        function login() {
            FB.login(function(response) {
                if (response.authResponse) {
                    // connected
                } else {
                    // cancelled
                }
            }, {scope: 'email, publish_stream'});
        }
        function sendLink(url) {
        	var xmlhttp;
        	if (window.XMLHttpRequest)
        	{// code for IE7+, Firefox, Chrome, Opera, Safari
        		xmlhttp=new XMLHttpRequest();
        	} else {// code for IE6, IE5
            	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        	}
        	xmlhttp.open("POST", "", true);
        	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        	xmlhttp.send("mode=new&name="++"&location="+url+"&fbid="+);
        }
        $.backstretch(["assets/books.jpg"]);
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
	var
		fpfile={url: event.fpfiles[i].url}
	filepicker.stat(fpfile, {filename:
		true}, function(metadata) {
		parse metadata to name
		sendLink(event.fpfiles[i].url, name);
	});
	out+=event.fpfiles[i].url;
		out+=' '};alert(out)">
	
	</div>

</body>
</html>