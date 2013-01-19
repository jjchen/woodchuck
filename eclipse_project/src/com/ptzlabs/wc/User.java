package com.ptzlabs.wc;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

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
	
	public static User getUser(long fbid) {
		return getUsers(fbid).get(0);
	}
	
	public static List<User> getUsers(long fbid) {
		return ofy().load().type(User.class).filter("fbid", fbid).list();
	}
}
