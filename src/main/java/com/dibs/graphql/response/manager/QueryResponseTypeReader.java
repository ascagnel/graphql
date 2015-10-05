package com.dibs.graphql.response.manager;

public interface QueryResponseTypeReader {

	public Object readProperty(Object sourceBean, String fieldName);

}
