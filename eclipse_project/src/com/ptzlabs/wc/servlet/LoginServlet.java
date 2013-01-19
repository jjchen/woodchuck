package com.ptzlabs.wc.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String redirectURI = req.getParameter("r");
		
		resp.sendRedirect("https://www.facebook.com/dialog/oauth?client_id=457817960938308" +
			"&redirect_uri=" + URLEncoder.encode("http://wc.ptzlabs.com/login", "ISO-8859-1") +
		    "&state=SOME_ARBITRARY_BUT_UNIQUE_STRING");
		
		/*
		User user = null;
        try {
            OAuthService oauth = OAuthServiceFactory.getOAuthService();
            user = oauth.getCurrentUser();

        } catch (OAuthRequestException e) {
            // The consumer made an invalid OAuth request, used an access token that was
            // revoked, or did not provide OAuth information.
            // ...
        }
        */
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		if(req.getParameter("code") == null) {
			resp.setContentType("text/plain");
			resp.getWriter().println("Login fail");
		} else {
			resp.getWriter().println("Login success");
		}
		resp.sendRedirect("https://www.facebook.com/dialog/oauth?client_id=457817960938308" +
			"&redirect_uri=http://wc.ptzlabs.com/login" +
		    "&state=SOME_ARBITRARY_BUT_UNIQUE_STRING");
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
