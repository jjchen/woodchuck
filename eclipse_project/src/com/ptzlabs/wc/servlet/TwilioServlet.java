package com.ptzlabs.wc.servlet;
import java.io.IOException;
import javax.servlet.http.*;

public class TwilioServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Date currentDate = new Date();
		List<Reading> readingList = ofy().load().type(Reading.class).filter("dueDate <=", currentDate).list();
		for (Reading reading : readingList) {
			if (reading.lastSent.getTime() + reading.frequency >= currentDate.getTime()) {
				sendChunk(reading);
				reading.lastSent = currentDate;
			}
		}

    }
}


