package com.ptzlabs.wc.servlet;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.ptzlabs.wc.Chunk;
import com.ptzlabs.wc.Reading;
import com.ptzlabs.wc.User;

public class ChunkServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (req.getParameter("mode") == null) { 
			resp.setContentType("text/plain");
			resp.getWriter().println("Mode not specified.");
		} else if (req.getParameter("mode").equals("getChunk") && req.getParameter("id") != null && req.getParameter("readingId") != null) {
			Gson gson = new Gson();
			resp.setContentType("text/plain");
			
			Reading r = getReading(Long.parseLong(req.getParameter("readingId")));
			if(r != null) {
				resp.getWriter().println(gson.toJson(r.getChunk(Integer.valueOf(req.getParameter("id")))));
			} else {
				resp.getWriter().println("Reading ID not valid");
			}
		}
	}

	public static Reading getReading(long id) {
		return ofy().load().type(Reading.class).id(id).get();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}
}
