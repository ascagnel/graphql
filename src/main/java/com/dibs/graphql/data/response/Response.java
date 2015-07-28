package com.dibs.graphql.data.response;

import java.util.List;

import com.dibs.graphql.data.GraphQLData;

public class Response implements GraphQLData {
	private static final long serialVersionUID = 5620028644346664264L;
	
	private List<Error> errors;
	private Object data;
	private Throwable exception;
	
	public Response() {
	}
	
	public List<Error> getErrors() {
		return errors;
	}
	
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}
}
