package com.dibs.graphql.response.processor;


public interface QueryResponseDataProcessor {

	public <T> T process(Object bean, Class<T> responseType);
}
