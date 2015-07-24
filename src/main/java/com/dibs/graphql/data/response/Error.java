package com.dibs.graphql.data.response;

import java.util.List;

public class Error {

	private String message;
	private List<ErrorLocation> locations;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<ErrorLocation> getLocations() {
		return locations;
	}
	
	public void setLocations(List<ErrorLocation> locations) {
		this.locations = locations;
	}
}
