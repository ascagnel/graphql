package com.dibs.graphql.data.deserialize;


public class TokenData {
	private char[] value;
	private TokenType type;
	
	public TokenData() {
	}
	
	public TokenData(char[] value, TokenType type) {
		this.value = value;
		this.type = type;
	}

	public char[] getValue() {
		return value;
	}

	public void setValue(char[] value) {
		this.value = value;
	}

	public TokenType getType() {
		return type;
	}

	public void setType(TokenType type) {
		this.type = type;
	}
}