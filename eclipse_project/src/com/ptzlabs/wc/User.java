package com.ptzlabs.wc;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

public class User {
	@Id Long id;
	@Index Long fbid;
	String name;
	String email;
	int phone;
}
