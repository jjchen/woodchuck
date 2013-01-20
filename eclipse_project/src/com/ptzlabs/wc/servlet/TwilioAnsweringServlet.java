package com.ptzlabs.wc.servlet;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TwilioAnsweringServlet extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String fromNumber = req.getParameter("From");
		String text = req.getParameter("Body");		

		
    }

}


