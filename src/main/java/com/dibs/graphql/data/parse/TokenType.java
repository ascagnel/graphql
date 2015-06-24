package com.dibs.graphql.parser.data;

import java.util.HashMap;
import java.util.Map;


public enum TokenType {
	
	OBJECT_START('{'),
	OBJECT_END('}'),
	OBJECT_DELIM(','),
	ATTRIBUTE_START('('),
	ATTRIBUTE_END(')'),
	ATTRIBUTE_DELIM(':');

	private Character value;
	private static Map<Character, TokenType> typesByValue;
	
	static {
		typesByValue = new HashMap<>();
		
		for (TokenType tokenType : TokenType.values()) {
			typesByValue.put(tokenType.value, tokenType);
		}
	}
	private TokenType(Character value) {
		this.value = value;
	}
	
	public Character getValue() {
		return value;
	}
	
	public static TokenType fromValue(Character value) {
		return typesByValue.get(value);
	}
}

