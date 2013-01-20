package com.ptzlabs.wc;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

/**
 * Uses @Parent in Chunk solely to keep track of data 
 */
@Entity
public class Reading {
	@Id
	public Long id;
	public String name;
	@Index public long user;
	public Date createdDate;
	@Index public Date dueDate; // defaults to 7 days from now
	public Date lastSent;
	public int frequency;

	public int currentChunk = 1;
	public int chunkSize = 2; // # of sentences in each chunk
	public int totalChunks = 0; // 0 = file not ready
	
	@Ignore public String currentChunkText;
	
	private Reading() { }
	
	public Reading(String name, long fbid) {
		this.name = name;
		this.user = User.getUser(fbid).id;
		
		createdDate = new Date();
		lastSent = createdDate;
		// now + 7 days
		dueDate = new Date(createdDate.getTime() + 604800000);
	}
	
	public Reading(String name, Date dueDate, long fbid) {
		this.name = name;
		this.user = User.getUser(fbid).id;
		
		createdDate = new Date();
		lastSent = createdDate;
		this.dueDate = dueDate;
	} 
	
	public void nextChunk() {
		if(currentChunk + 1 < totalChunks) {
			currentChunk++;
		}
	}
	
	public void prevChunk() {
		if(currentChunk != 0) {
			currentChunk--;
		}
	}
	
	public boolean isReady() {
		return totalChunks > 0;
	}
	
	public Chunk getChunk(int chunkId) {
		return ofy().load().key(Key.create(Key.create(Reading.class, id), Chunk.class, chunkId)).get();
	}
	
	public Chunk getCurrentChunk() {
		return getChunk(currentChunk);
	}
	
	public void setTotalChunks(int newTotal) {
		totalChunks = newTotal; 
	}
	
}
