package com.dibs.graphql.response.type.factory;

public interface QueryResponseTypeFactory {

	public Object initializeType(Class<?> responseType, Class<?> sourceType);
}
