package com.dibs.graphql.response.manager.impl;

import java.util.HashMap;

import com.dibs.graphql.response.manager.QueryResponseTypeFactory;

public class QueryResponseTypeFactoryMapImpl implements QueryResponseTypeFactory {

	@Override
	public Object initializeType(Class<?> fromClazz, Class<?> sourceType) {
		return new HashMap<String, Object>();
	}

}
