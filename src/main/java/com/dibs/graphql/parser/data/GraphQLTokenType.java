package com.dibs.graphql.parser.data;

import java.util.HashMap;
import java.util.Map;


public enum GraphQLTokenType {
	
	OBJECT_START('{'),
	OBJECT_END('}'),
	OBJECT_DELIM(','),
	ATTRIBUTE_START('('),
	ATTRIBUTE_END(')'),
	ATTRIBUTE_DELIM(':');

	private Character value;
	private static Map<Character, GraphQLTokenType> typesByValue;
	
	static {
		typesByValue = new HashMap<>();
		
		for (GraphQLTokenType tokenType : GraphQLTokenType.values()) {
			typesByValue.put(tokenType.value, tokenType);
		}
	}
	private GraphQLTokenType(Character value) {
		this.value = value;
	}
	
	public static GraphQLTokenType fromValue(Character value) {
		return typesByValue.get(value);
	}
}

