package com.ptzlabs.wc;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SmsSender {

	public static void sendChunk(Reading reading) {

		User user = ofy().load().type(User.class).id(reading.user).get();
		Chunk chunk = reading.getCurrentChunk();
		if (chunk == null) return;

		try {
			URL url = new URL("https://ptzlabs.com/twilio/send.php");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			String urlParams = "number=" + String.valueOf(user.phone) + "&data=" + chunk.data;
			String urlParams = "number=3234919737&data=hhii";			
			connection.setDoOutput(true);

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Length", ""+Integer.toString(urlParams.getBytes().length));

	DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
wr.writeBytes(urlParams);
wr.flush();
wr.close();
connection.disconnect();
	
		} catch (MalformedURLException e) {

		} catch (IOException e) {

		}
	}
}
