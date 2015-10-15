package com.dibs.graphql.response.type.factory.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.dibs.graphql.response.type.factory.QueryResponseTypeFactory;

public class QueryResponseTypeFactoryMapImpl implements QueryResponseTypeFactory {

	@Override
	public Object initializeType(Class<?> fromClazz, Class<?> sourceType) {
		if (Iterable.class.isAssignableFrom(sourceType) && !Map.class.isAssignableFrom(sourceType)) {
			return new LinkedList<Object>();

		}
		return new HashMap<String, Object>();
	}
}
