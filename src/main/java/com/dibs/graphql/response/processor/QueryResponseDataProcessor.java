package com.dibs.graphql.response.processor;


public interface QueryResponseDataProcessor<T> {

	public T process(Object bean);
}
