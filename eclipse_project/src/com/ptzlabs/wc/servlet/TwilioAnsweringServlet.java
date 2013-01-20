package com.ptzlabs.wc.servlet;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TwilioAnsweringServlet extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String fromNumber = req.getParameter("from");
		
		List<User> userList = ofy().load().type(User.class).filter("phone", fromNumber).list();
		User user = userList.get(0);

		String text = req.getParameter("body");

		Reading curReading = readingList.get(0);
		List<Reading> readingList = ofy().load().type(Reading.class).filter("user", user.id).list();
		for (Reading reading : readingList) {
			if (reading.id == user.curReading) {
				curReading = reading;
			}
		}

		if (text.equals("list readings")) {
		// list readings
			String data = ""
			for (Reading reading : readingList) {
				data = data + "\n " + reading.name;
			}
			sendText("number="+fromNumber+"&data="+data);
		} else if (text.equals("highlight")) {
		// highlight last chunk
			Chunk chunk = reading.getPreviousChunk();
			chunk.highlight = true;
			ofy().save().entity(chunk).now();
		} else if (text.equals("next")) {
		// get next chunk
			Chunk chunk = reading.getCurrentChunk();
			user.curReading = reading.id;
			ofy().save().entity(user).now();
			String data = URLEncoder.encode(chunk.data, "UTF-8");
			sendText("number="+fromNumber+"&data="+data);
			reading.nextChunk();
			reading.lastSent = currentDate;
			ofy().save().entity(reading).now();
		} else {
		// insert note
			Chunk chunk = reading.getPreviousChunk();
			chunk.note = text;
			ofy().save().entity(chunk).now();
		}
    }

					User user = ofy().load().type(User.class).id(reading.user).get();
					Chunk chunk = reading.getCurrentChunk();
					if (chunk == null) return;
					
					String message = URLEncoder.encode(chunk.data, "UTF-8");

"number=" + user.phone + "&data=" + message

					reading.nextChunk();
					reading.lastSent = currentDate;
					ofy().save().entity(reading).now();


    public void sendText(String params) {

					
					URL url = new URL("http://ptzlabs.com/twilio/send.php");
		            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		            connection.setDoOutput(true);
		            connection.setRequestMethod("POST");
		            connection.setRequestProperty("charset", "utf-8");
		            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		            connection.setRequestProperty("Content-Length", ""+("number="+user.phone+"data=" + message).getBytes().length);

		            DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
		            writer.writeBytes(params);
		            writer.flush();
		            writer.close();
		            connection.disconnect();
		            
		            resp.setContentType("text/plain");
		            resp.getWriter().write("text sent to " + user.phone + ", message: " + message);
		    
		            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

		            } else {
		                // Server returned HTTP error code.
		            }
					
    }

}


