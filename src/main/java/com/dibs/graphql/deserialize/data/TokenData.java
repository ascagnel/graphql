package com.dibs.graphql.deserialize.data;


public class TokenData {
	private char[] value;
	private Punctuator type;
	
	public TokenData() {
	}
	
	public TokenData(char[] value, Punctuator type) {
		this.value = value;
		this.type = type;
	}

	public char[] getValue() {
		return value;
	}

	public void setValue(char[] value) {
		this.value = value;
	}

	public Punctuator getType() {
		return type;
	}

	public void setType(Punctuator type) {
		this.type = type;
	}
}