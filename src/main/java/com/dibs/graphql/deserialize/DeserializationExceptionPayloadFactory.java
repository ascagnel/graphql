package com.dibs.graphql.deserialize;


public class DeserializationExceptionPayloadFactory {

	public static DeserializationExceptionPayload fromInputMessageLocationDataProvider(String message, InputMessageLocationDataProvider provider) {
		DeserializationExceptionPayload payload = new DeserializationExceptionPayload();
		payload.setMessage(message);
		payload.setLineNumber(provider.getLineNumber());
		payload.setColumnNumber(provider.getColumnNumber());
		payload.setLastToken(provider.getLastReadToken());
		
		return payload;
	}
}
