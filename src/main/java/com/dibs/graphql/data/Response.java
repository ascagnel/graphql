package com.dibs.graphql.data;

import java.util.List;
import java.util.Map;

public class Response {

	private List<String> errors;
	private Map<String, Object> data;
	
	public Response() {
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	public Map<String, Object> getData() {
		return data;
	}
	
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
