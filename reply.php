<?php
     // now greet the sender
    header("content-type: text/xml");
    echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

	$url = "http://wc.ptzlabs.com/twilioAnswer";

    $from = $_REQUEST['From'];
 
	$data = $_REQUEST['Body'];

	$ch = curl_init();
	$fields = "from="+$from+"&body="+$data;

	//set the url, number of POST vars, POST data
	curl_setopt($ch,CURLOPT_URL, $url);
	curl_setopt($ch,CURLOPT_POST, 1);
	curl_setopt($ch,CURLOPT_POSTFIELDS, $fields);

	$result = curl_exec($ch);

	curl_close($ch);

?>
<Response>
    <Sms>Thanks for the message! <?php echo $data ?></Sms>
</Response>
