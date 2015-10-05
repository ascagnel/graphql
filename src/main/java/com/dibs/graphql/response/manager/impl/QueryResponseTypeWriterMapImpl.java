package com.dibs.graphql.response.manager.impl;

import java.util.Map;

import com.dibs.graphql.response.manager.QueryResponseTypeWriter;

public class QueryResponseTypeWriterMapImpl implements QueryResponseTypeWriter {

	@SuppressWarnings("unchecked")
	@Override
	public void writeProperty(Object response, String fieldName, Object fieldValue) {
		Map<Object, Object> responseMap = (Map<Object, Object>) response;
		responseMap.put(fieldName, fieldValue);
	}

}
