package com.ptzlabs.wc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String accessToken = req.getParameter("access_token");
		String userId = req.getParameter("userid");
		
		if (accessToken == null) {
			return;
		}
		
		resp.setContentType("text/plain");
		
		resp.getWriter().println("access_token: " + accessToken);
		resp.getWriter().println("userid: " + userId);
		
		try {
            URL url = new URL("http://graph.facebook.com/" + userId + 
            		"?fields=name,email&access_token=" + accessToken);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                resp.getWriter().println(line);
            }
            reader.close();

        } catch (MalformedURLException e) {
            // ...
        } catch (IOException e) {
            // ...
        }
		
		
		/*
		resp.sendRedirect("https://www.facebook.com/dialog/oauth?client_id=457817960938308" +
			"&redirect_uri=" + URLEncoder.encode("http://wc.ptzlabs.com/login", "ISO-8859-1") +
		    "&state=SOME_ARBITRARY_BUT_UNIQUE_STRING");
		*/
	}
}
