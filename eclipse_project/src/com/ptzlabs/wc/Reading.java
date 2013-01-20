package com.ptzlabs.wc;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Uses @Parent in Chunk solely to keep track of data 
 */
@Entity
public class Reading {
	@Id
	public Long id;
	public String name;
	public long user;
	public Date createdDate;
	public Date dueDate; // defaults to 7 days from now
	public Date lastSent = createdDate;
	public int frequency;

	int currentChunk = 0;
	int chunkSize = 2; // # of sentences in each chunk
	int totalChunks = 0; // 0 = file not ready
	
	private Reading() { }
	
	public Reading(String name, long fbid) {
		this.name = name;
		this.user = User.getUser(fbid).id;
		
		createdDate = new Date();
		// now + 7 days
		dueDate = new Date(createdDate.getTime() + 604800000);
	}
	
	public Reading(String name, Date dueDate, long fbid) {
		this.name = name;
		this.user = User.getUser(fbid).id;
		
		createdDate = new Date();
		this.dueDate = dueDate;
	} 
	
	public void nextChunk() {
		currentChunk++;
	}
	
	public void prevChunk() {
		if(currentChunk != 0) {
			currentChunk--;
		}
	}
	
	public boolean isReady() {
		return totalChunks > 0;
	}
	
	public Chunk getCurrentChunk() {
		return ofy().load().key(Key.create(Key.create(Reading.class, id), Chunk.class, currentChunk)).get();
	}
	
	public void setTotalChunks(int newTotal) {
		totalChunks = newTotal; 
	}
	
}
