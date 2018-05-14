package org.bcia.javachain.ca.szca.publicweb.api;

import com.google.gson.JsonObject;

public class ApiMessage {
	private com.google.gson.JsonObject json = new JsonObject(); 
	
	public JsonObject toJsonObject() {
		json.addProperty("code", code);
		json.addProperty("message",message);
		return json;
	}
	
	private int code=0;
	
	private String message="";
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	  
	   
}
