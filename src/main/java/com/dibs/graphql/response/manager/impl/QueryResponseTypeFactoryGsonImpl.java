package com.dibs.graphql.response.manager.impl;

import com.dibs.graphql.response.manager.QueryResponseTypeFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class QueryResponseTypeFactoryGsonImpl implements QueryResponseTypeFactory {

	@Override
	public Object initializeType(Class<?> fromClazz, Class<?> sourceType) {
		if (Iterable.class.isAssignableFrom(sourceType)) {
			return new JsonArray();
		}
		
		return new JsonObject();
	}

}
