package com.ptzlabs.wc.servlet;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.ptzlabs.wc.Reading;

public class ReadingServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		if(req.getParameter("mode").equals("new") && req.getParameter("name") != null &&
				req.getParameter("location") != null && req.getParameter("fbid") != null) {
			
			Reading reading;
			if(req.getParameter("dueDate") != null) {
				reading = new Reading(req.getParameter("name"),
						new Date(Long.parseLong(req.getParameter("dueDate"))),
						Long.parseLong(req.getParameter("fbid"))); 
			} else {
				reading = new Reading(req.getParameter("name"),
						Long.parseLong(req.getParameter("fbid")));
			}
			
			
			ofy().save().entity(reading).now();
			
			resp.setContentType("text/plain");
			resp.getWriter().println("OK");
		}
	}
	
	private static BlobKey readFileAndStore(String location) {
		return null;
	}
}
