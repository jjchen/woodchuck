package com.ptzlabs.wc.servlet;

import javax.servlet.http.HttpServlet;

import com.googlecode.objectify.ObjectifyService;
import com.ptzlabs.wc.Chunk;
import com.ptzlabs.wc.Reading;
import com.ptzlabs.wc.User;

/**
 * Used to register entities with objectify.
 */
public class StartUpServlet extends HttpServlet{
	static {
		ObjectifyService.register(User.class);
		ObjectifyService.register(Reading.class);
		ObjectifyService.register(Chunk.class);
	}
}
