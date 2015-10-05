package com.dibs.graphql.response.manager;

public interface QueryResponseTypeFactory {

	public Object initializeType(Class<?> responseType, Class<?> sourceType);
}
