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
			String urlParams = "number=" + String.valueOf(user.phone) + "&data=" + chunk.data;
			
			connection.setDoOutput(true);

			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestMethod("POST");
		
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(urlParams);
			writer.flush();
			writer.close();
			connection.disconnect();

		} catch (MalformedURLException e) {

		} catch (IOException e) {

		}
	}
}
