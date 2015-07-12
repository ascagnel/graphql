package com.dibs.graphql.data.deserialize;

import java.util.HashMap;
import java.util.Map;


public enum Punctuator {
	
	OPEN_CURLY_BRACE('{'),
	CLOSE_CURLY_BRACE('}'),
	COMMA(','),
	OPEN_PAREN('('),
	CLOSE_PAREN(')'),
	COLON(':'),
	OPEN_BRACKET('['),
	CLOSE_BRACKET(']');
	
	private Character value;
	private static Map<Character, Punctuator> typesByValue;
	
	static {
		typesByValue = new HashMap<>();
		
		for (Punctuator tokenType : Punctuator.values()) {
			typesByValue.put(tokenType.value, tokenType);
		}
	}
	private Punctuator(Character value) {
		this.value = value;
	}
	
	public Character getValue() {
		return value;
	}
	
	public static Punctuator fromValue(Character value) {
		return typesByValue.get(value);
	}
}

