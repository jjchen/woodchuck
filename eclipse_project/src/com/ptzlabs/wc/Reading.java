package com.ptzlabs.wc;

import java.util.Date;

import com.googlecode.objectify.annotation.Id;

public class Reading {
	@Id Long id;
	String name;
	User user;
	Date createdDate;
	Date dueDate; // defaults to 7 days from now
	String location;
	
	int currentChunk = 0;
	int chunkSize = 2; // # of sentences in each chunk
	
	private Reading() { }
	
	public Reading(String name, String location) {
		this.name = name;
		this.location = location;
	} 
	
}
