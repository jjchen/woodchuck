package com.ptzlabs.wc;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class User {
	@Id Long id;
	@Index long fbid;
	String name;
	String email;
	int phone;
	
	private User() { }
	
	public User(long fbid, String name, String email) {
		this.fbid = fbid;
		this.name = name;
		this.email = email;
	}
}
