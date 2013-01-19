package com.ptzlabs.wc.servlet;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.googlecode.objectify.Key;
import com.ptzlabs.wc.Reading;

public class ReadingServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		if (req.getParameter("mode").equals("new")
				&& req.getParameter("name") != null
				&& req.getParameter("location") != null
				&& req.getParameter("fbid") != null) {

			Reading reading;
			if (req.getParameter("dueDate") != null) {
				reading = new Reading(req.getParameter("name"), new Date(
						Long.parseLong(req.getParameter("dueDate"))),
						Long.parseLong(req.getParameter("fbid")));
			} else {
				reading = new Reading(req.getParameter("name"),
						Long.parseLong(req.getParameter("fbid")));
			}

			ofy().save().entity(reading).now();
			
			BlobKey bk = readFileAndStore(req.getParameter("location"));
			Key<Reading> readingKey = Key.create(Reading.class, reading.id);
			
			
			/*
			// Later, read from the file using the file API
			lock = false; // Let other people read at the same time
			FileReadChannel readChannel = fileService.openReadChannel(file, false);

			// Again, different standard Java ways of reading from the channel.
			BufferedReader reader = new BufferedReader(Channels.newReader(
					readChannel, "UTF8"));
			String line = reader.readLine();
			// line = "The woods are lovely dark and deep."

			readChannel.close();
			*/
			
			// INSERT MATH STUFF HERE
			// THEN GO new Chunk(readingKey, data);
			// ofy().save().entity(chunk).now();

			// TODO: remove from blobstore
			

			resp.setContentType("text/plain");
			resp.getWriter().println("OK");
		}
	}

	private static BlobKey readFileAndStore(String location) {
		try {
			// Get a file service
			FileService fileService = FileServiceFactory.getFileService();

			// Create a new Blob file with mime-type "text/plain"
			AppEngineFile file = fileService.createNewBlobFile("text/plain");

			// Open a channel to write to it
			boolean lock = false;
			FileWriteChannel writeChannel = fileService.openWriteChannel(file, lock);

			InputStream in = new BufferedInputStream(new URL(location).openStream());

			byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				writeChannel.write(ByteBuffer.wrap(data));
			}
			if (in != null) in.close();
			writeChannel.closeFinally();
			
			return fileService.getBlobKey(file);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
}
