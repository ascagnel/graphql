package com.dibs.graphql.response.manager.impl;

import java.util.Collection;
import java.util.Map;

import com.dibs.graphql.response.manager.QueryResponseTypeWriter;

public class QueryResponseTypeWriterMapImpl implements QueryResponseTypeWriter {

	@SuppressWarnings("unchecked")
	@Override
	public void writeProperty(Object response, String fieldName, Object fieldValue) {
		Map<Object, Object> responseMap = (Map<Object, Object>) response;
		responseMap.put(fieldName, fieldValue);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addPropertyToIterable(Object response, String fieldName, Object fieldValue) {
		if (Collection.class.isAssignableFrom(response.getClass())) {
			((Collection) response).add(fieldValue);
		} else {
			throw new RuntimeException("No handler for iterable type [" + response.getClass() +"]");
		}
	}
}
