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
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.verbs.TwiMLResponse;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.Sms;

public class TwilioAnsweringServlet extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String fromNumber = request.getParameter("From");
		String text = request.getParameter("Body);		

		
    }

}


