package com.ptzlabs.wc;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class SmsSender {

	public static void sendChunk(Reading reading) {

		User user = ofy().load().type(User.class).id(reading.user).get();
		Chunk chunk = reading.getCurrentChunk();
		if (chunk == null) return;

		try {
			URL url = new URL("https://ptzlabs.com/twilio/send.php");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);

			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestMethod("POST");

			String message = URLEncoder.encode("{\"number\":\"" + String.valueOf(user.phone) + "\"," +
					"\"data\":\"" + chunk.data + "\"}", "UTF-8");

			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(message);
			writer.close();

		} catch (MalformedURLException e) {

		} catch (IOException e) {

		}
	}
}
