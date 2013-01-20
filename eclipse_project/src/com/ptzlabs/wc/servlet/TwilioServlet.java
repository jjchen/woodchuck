package com.ptzlabs.wc.servlet;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ptzlabs.wc.Reading;
import com.ptzlabs.wc.SmsSender;


public class TwilioServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Date currentDate = new Date();
		List<Reading> readingList = ofy().load().type(Reading.class).filter("dueDate >=", currentDate).list();
		for (Reading reading : readingList) {
			if (reading.lastSent.getTime() + reading.frequency * 60000 <= currentDate.getTime()) {
				if(reading.currentChunk + 1 < reading.totalChunks) {
					SmsSender.sendChunk(reading);
					reading.nextChunk();
					reading.lastSent = currentDate;
					ofy().save().entity(reading).now();
				}
			}
		}

    }

}


