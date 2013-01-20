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

public class ReadingServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (req.getParameter("mode") == null) { 
			resp.setContentType("text/plain");
			resp.getWriter().println("Mode not specified.");
		} else if (req.getParameter("mode").equals("new") && req.getParameter("name") != null
				&& req.getParameter("location") != null && req.getParameter("fbid") != null
				&& req.getParameter("type") != null) {

			Reading reading;
			if (req.getParameter("dueDate") != null) {
				reading = new Reading(req.getParameter("name"), new Date(Long.parseLong(req.getParameter("dueDate"))),
						Long.parseLong(req.getParameter("fbid")));
			} else {
				reading = new Reading(req.getParameter("name"), Long.parseLong(req.getParameter("fbid")));
			}

			ofy().save().entity(reading).now();
			Key<Reading> readingKey = Key.create(Reading.class, reading.id);

			if (req.getParameter("type").equals("application/pdf")) {
				String text = readPdf(req.getParameter("location"));

				int fromIndex = 0;
				int endSentence = text.indexOf(". ", fromIndex);
				int sentence = 0;
				String data = "";

				int chunkCounter = 1;
				while (endSentence >= 0) {
					endSentence += 2;
					data += text.substring(fromIndex, endSentence);
					sentence++;
					if (sentence == 2) {
						createAndStoreChunk(chunkCounter, readingKey, data);
						chunkCounter++;
						sentence = 0;
						data = "";
					}
					fromIndex = endSentence;
					endSentence = text.indexOf(". ", fromIndex);
				}
				if (sentence != 0) {
					createAndStoreChunk(chunkCounter, readingKey, data);
				}

				updateTotalChunks(readingKey, chunkCounter);
			} else {
				AppEngineFile file = readText(req.getParameter("location"));
				FileService fileService = FileServiceFactory.getFileService();
				FileReadChannel readChannel = fileService.openReadChannel(file, false);
				BufferedReader reader = new BufferedReader(Channels.newReader(readChannel, "UTF8"));

				String line = reader.readLine();
				String data = "";
				int sentence = 0;
				int chunkCounter = 1;
				
				while (line != null) {
					int fromIndex = 0;
					int endSentence = line.indexOf(". ", fromIndex);

					while (endSentence >= 0) {
						endSentence += 2;
						data += line.substring(fromIndex, endSentence);
						sentence++;
						if (sentence == 2) {
							createAndStoreChunk(chunkCounter, readingKey, data);
							sentence = 0;
							data = "";
							chunkCounter++;
						}
						fromIndex = endSentence;
						endSentence = line.indexOf(". ", fromIndex);
					}

					data += line.substring(fromIndex);
					line = reader.readLine();
				}
				
				readChannel.close();

				if (sentence != 0) {
					createAndStoreChunk(chunkCounter, readingKey, data);
				}
				updateTotalChunks(readingKey, chunkCounter);
				
				// remove blob from blobstore
				BlobKey blobKey = fileService.getBlobKey(file);
				BlobstoreService blobStoreService = BlobstoreServiceFactory.getBlobstoreService();

				blobStoreService.delete(blobKey);
			}

			int frequency = getFrequency(reading);
			reading.frequency = frequency;
			ofy().save().entity(reading).now();
			
			resp.setContentType("text/plain");
			resp.getWriter().println("OK");
		
		} else if (req.getParameter("mode").equals("getReadings") && req.getParameter("fbid") != null) {
			Gson gson = new Gson();
			
			resp.setContentType("text/plain");
			resp.getWriter().println(gson.toJson(getReadings(Long.parseLong(req.getParameter("fbid")))));
		} else if (req.getParameter("mode").equals("get") && req.getParameter("id") != null) {
			Gson gson = new Gson();
			resp.setContentType("text/plain");
			resp.getWriter().println(gson.toJson(get(Long.parseLong(req.getParameter("id")))));
		}
	}
	
	private static void createAndStoreChunk(long id, Key<Reading> readingKey, String data) {
		Chunk chunk = new Chunk(id, readingKey, data);
		ofy().save().entity(chunk).now();
	}
	
	private static void updateTotalChunks(Key<Reading> readingKey, int totalChunks) {
		Reading r = ofy().load().key(readingKey).get();
		r.setTotalChunks(totalChunks);
		ofy().save().entity(r).now();
	}
	
	private static String readPdf(String location) throws IOException {
		String text = "";
		PdfReader reader;
		
		reader = new PdfReader(new URL(location));
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		TextExtractionStrategy strategy;
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
			text = text.concat(strategy.getResultantText());
		}
		reader.close();
		return text;
	}

	private static AppEngineFile readText(String location) throws IOException {
		BufferedReader reader;
		FileService fileService = FileServiceFactory.getFileService();
		AppEngineFile file = fileService.createNewBlobFile("text/plain");
		FileWriteChannel writeChannel = fileService.openWriteChannel(file, true);

		reader = new BufferedReader(new InputStreamReader(new URL(location).openStream()));

		String line;
		while ((line = reader.readLine()) != null) {
			writeChannel.write(ByteBuffer.wrap(line.getBytes()));
		}
		reader.close();
		writeChannel.closeFinally();

		return file;
	}

	public static List<Reading> getReadings(long fbid) {
		User user = User.getUser(fbid);
		if(user != null) {
			List<Reading> readingList = ofy().load().type(Reading.class).filter("user", user.id).list();
			for(Reading reading : readingList) {
				reading.currentChunkText = reading.getCurrentChunk().data;
			}
			return readingList;
		} else {
			return null;
		}
	}

	public static Reading get(long id) {
		return ofy().load().type(Reading.class).id(id).get();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}

	public static int getFrequency(Reading reading) {
        int days = 7;
        int hoursInDay = 10;
        int totalHours = days * hoursInDay;
        int totalMinutes = totalHours * 60;
        int frequency = totalMinutes / reading.totalChunks;
        return frequency;
    }
}
