package com.ptzlabs.wc;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Chunk {
	@Id Long id;
	@Parent Key<Reading> reading;
	boolean isRead = false;
	String data;
	
	private Chunk() { }

	public Chunk(Key<Reading> key, String data) {
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
