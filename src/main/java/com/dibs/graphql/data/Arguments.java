package com.dibs.graphql.data;

import java.util.Map;

public class Arguments {

	private Map<String, Object> arguments;

	public <T> T getValue(String key) {
		return (T) arguments.get(key);
	}
	
	public Class<?> getValueType(String key) {
		Object value = arguments.get(key).getClass();
		
		return value == null? null : value.getClass();
	}
}
