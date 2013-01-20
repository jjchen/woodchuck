package com.ptzlabs.wc;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Chunk {
	@Id long id;
	@Parent Key<Reading> reading;
	boolean isRead = false;
	boolean highlight = false;
	String note = "";
	public String data;
	
	private Chunk() { }

	public Chunk(long id, Key<Reading> key, String data) {
		this.id = id;
		this.reading = key;
		this.data = data;
		this.isRead = false;
	}
	
	public void readChunk() {
		this.isRead = true;
	}
	
	public void unReadChunk() {
		this.isRead = false;
	}
	
}
