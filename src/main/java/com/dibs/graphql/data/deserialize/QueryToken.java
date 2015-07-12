package com.dibs.graphql.data.deserialize;

import java.util.Map;

public class QueryToken {
	
	private String fullTokenValue;
	private String value;
	private Map<String, Object> attributes;
	private Punctuator tokenType;
	
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

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public QueryToken setArguments(Map<String, Object> attributes) {
		this.attributes = attributes;
		return this;
	}

	public Punctuator getTokenType() {
		return tokenType;
	}

	public QueryToken setTokenType(Punctuator tokenType) {
		this.tokenType = tokenType;
		return this;
	}
}
