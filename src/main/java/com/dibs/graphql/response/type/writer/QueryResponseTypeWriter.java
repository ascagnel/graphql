package com.dibs.graphql.response.type.writer;

public interface QueryResponseTypeWriter {

	public void writeProperty(Object response, String fieldName, Object fieldValue);
	public void addPropertyToIterable(Object response, String fieldName, Object fieldValue);


}
