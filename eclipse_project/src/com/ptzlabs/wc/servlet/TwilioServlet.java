package com.ptzlabs.wc.servlet;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ptzlabs.wc.Chunk;
import com.ptzlabs.wc.Reading;
import com.ptzlabs.wc.User;


public class TwilioServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Date currentDate = new Date();
		List<Reading> readingList = ofy().load().type(Reading.class).filter("dueDate >=", currentDate).list();
		for (Reading reading : readingList) {
			if (reading.lastSent.getTime() + reading.frequency * 60000 <= currentDate.getTime()) {
				if(reading.currentChunk + 1 < reading.totalChunks) {
					
					User user = ofy().load().type(User.class).id(reading.user).get();
					user.curReading = reading.id;
					ofy().save().entity(chunk).now();
					Chunk chunk = reading.getCurrentChunk();
					if (chunk == null || user == null) continue;
					
					String message = URLEncoder.encode(chunk.data, "UTF-8");
					
					URL url = new URL("http://ptzlabs.com/twilio/send.php");
		            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		            connection.setDoOutput(true);
		            connection.setRequestMethod("POST");
		            connection.setRequestProperty("charset", "utf-8");
		            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		            connection.setRequestProperty("Content-Length", ""+("number="+user.phone+"&data=" + message).getBytes().length);

		            DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
		            writer.writeBytes("number=" + user.phone + "&data=" + message);
		            writer.flush();
		            writer.close();
		            
		            connection.disconnect();
		            
		            resp.setContentType("text/plain");
		            resp.getWriter().write("text sent to " + user.phone + ", message: " + message);
		    
		            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

		            } else {
		                // Server returned HTTP error code.
		            }
					
					reading.nextChunk();
					reading.lastSent = currentDate;
					ofy().save().entity(reading).now();
				}
			}
		}

    }

}


