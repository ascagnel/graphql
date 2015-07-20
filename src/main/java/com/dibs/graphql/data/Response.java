package com.dibs.graphql.data;

import java.util.List;
import java.util.Map;

public class Response {

	private List<Error> errors;
	private Map<String, Object> data;
	private Throwable exception;
	
	public Response() {
	}
	
	public List<Error> getErrors() {
		return errors;
	}
	
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public Map<String, Object> getData() {
		return data;
	}
	
	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}
}
