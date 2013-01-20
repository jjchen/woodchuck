<?php
 
	$url = '';

    $from = $_REQUEST['From'];
 
	$data = $_REQUEST['Body'];

	$ch = cur_init();
	$fields = "from="+$from+"&body="+$data;

	//set the url, number of POST vars, POST data
	curl_setopt($ch,CURLOPT_URL, $url);
	curl_setopt($ch,CURLOPT_POST, 2);
	curl_setopt($ch,CURLOPT_POSTFIELDS, $fields);

	$result = cur_exec($ch);

	curl_close($ch);

    // now greet the sender
    header("content-type: text/xml");
    echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
?>
<Response>
    <Sms>Thanks for the message! <?php echo $data ?></Sms>
</Response>
