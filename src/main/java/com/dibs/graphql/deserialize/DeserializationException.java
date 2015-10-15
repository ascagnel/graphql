package com.dibs.graphql.deserialize;

public class DeserializationException extends Exception {
	private static final long serialVersionUID = 1L;

	private DeserializationExceptionPayload payload;
	
	public DeserializationException(DeserializationExceptionPayload payload) {
		super();
		this.payload = payload;
	}
	
	public DeserializationException(DeserializationExceptionPayload payload, Throwable t) {
		super(t);
	}
	
	public DeserializationExceptionPayload getPayload() {
		return payload;
	}
	
	@Override
	public String getMessage() {
		return payload.buildMessage();
	}
}
