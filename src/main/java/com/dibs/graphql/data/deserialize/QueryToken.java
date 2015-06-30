package com.dibs.graphql.data.deserialize;

import java.util.Map;

public class QueryToken {
	
	private String fullTokenValue;
	private String value;
	private Map<String, String> attributes;
	private TokenType tokenType;
	
	public QueryToken() {
	}

	public String getFullTokenValue() {
		return fullTokenValue;
	}

	public QueryToken setFullTokenValue(String fullTokenValue) {
		this.fullTokenValue = fullTokenValue;
		return this;
	}

	public String getValue() {
		return value;
	}

	public QueryToken setValue(String value) {
		this.value = value;
		return this;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public QueryToken setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
		return this;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public QueryToken setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
		return this;
	}
}
