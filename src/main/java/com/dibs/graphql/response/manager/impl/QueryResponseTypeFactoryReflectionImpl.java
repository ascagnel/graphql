package com.dibs.graphql.response.manager.impl;

import com.dibs.graphql.response.manager.QueryResponseTypeFactory;

public class QueryResponseTypeFactoryReflectionImpl implements QueryResponseTypeFactory {

	@Override
	public Object initializeType(Class<?> fromClazz, Class<?> sourceType) {
		try {
			return sourceType.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Could not create new instance of class [" + fromClazz + "]", e);
		}
	}

}
