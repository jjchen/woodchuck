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

public class TwilioAnsweringServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String fromNumberBefore = req.getParameter("from");
		String fromNumber = "";
		if (fromNumberBefore == null) return;
		fromNumber = fromNumberBefore.substring(2);
		
		List<User> userList = ofy().load().type(User.class).filter("phone", fromNumber).list();
		User user = userList.get(0);

		String text = req.getParameter("body");

		List<Reading> readingList = ofy().load().type(Reading.class).filter("user", user.id).list();
		Reading curReading = readingList.get(0);
		for (Reading reading : readingList) {
			if (reading.id == user.curReading) {
				curReading = reading;
			}
		}
		
		resp.setContentType("text/plain");

		if (text.equals("list readings")) {
		// list readings
			String data = "";
			for (Reading reading : readingList) {
				data = data + "\n " + reading.name;
				resp.getWriter().write(reading.name);
			}
			try {
				String dataEncoded = URLEncoder.encode(data, "UTF-8");
				sendText(fromNumber, "number="+fromNumber+"&data="+dataEncoded);
				resp.getWriter().write("sent text");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if (text.equals("highlight")) {
		// highlight last chunk
			curReading.prevChunk();
			Chunk chunk = curReading.getCurrentChunk();
			curReading.nextChunk();
			chunk.highlight = true;
			ofy().save().entity(chunk).now();
		} else if (text.equals("next")) {
		// get next chunk
			Chunk chunk = curReading.getCurrentChunk();
			user.curReading = curReading.id;
			ofy().save().entity(user).now();
			String data = URLEncoder.encode(chunk.data, "UTF-8");
			try {
				sendText(fromNumber, "number="+fromNumber+"&data="+data);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			curReading.nextChunk();
			Date currentDate = new Date();
			curReading.lastSent = currentDate;
			ofy().save().entity(curReading).now();
		} else {
		// insert note
			curReading.prevChunk();
			Chunk chunk = curReading.getCurrentChunk();
			curReading.nextChunk();
			chunk.note = text;
			ofy().save().entity(chunk).now();
		}
    }
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	doPost(req, resp);
    }


    public void sendText(String number, String params) throws Exception {

    	
		URL url = new URL("http://ptzlabs.com/twilio/send.php");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", ""+(params).getBytes().length);

        DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
        writer.writeBytes(params);
        writer.flush();
        writer.close();
        
        connection.disconnect();
					
					
    }

}


