package com.ptzlabs.wc.servlet;

import com.googlecode.objectify.ObjectifyService;
import com.ptzlabs.wc.Reading;
import com.ptzlabs.wc.User;

/**
 * Used to register entities with objectify.
 */
public class StartUpServlet {
	static {
		ObjectifyService.register(User.class);
		ObjectifyService.register(Reading.class);
	}
}
