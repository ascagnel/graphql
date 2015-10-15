package com.dibs.graphql.response.type.reader;

public interface QueryResponseTypeReader {

	public Object readProperty(Object sourceBean, String fieldName);

}
