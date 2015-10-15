package com.dibs.graphql.response.type.factory.impl;

import com.dibs.graphql.response.type.factory.QueryResponseTypeFactory;
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
