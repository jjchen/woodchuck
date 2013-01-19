package com.ptzlabs.wc;

/**
 * Use only for gson conversion
 */
public class UserData {
	public String name;
	public String id; // should be parsed to a long
	public String email;
	public GraphError error;
	UserData() { };
	
	public static class GraphError {
		public String message;
		public String type;
		public int code;
		GraphError() { };
	}
}