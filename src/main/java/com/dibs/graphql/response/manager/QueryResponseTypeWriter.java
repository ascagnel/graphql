package com.dibs.graphql.response.manager;

public interface QueryResponseTypeWriter {

	public void writeProperty(Object response, String fieldName, Object fieldValue);

}
