package com.dibs.graphql.data.deserialize;

import com.dibs.graphql.data.Arguments;

public class QueryToken {
	
	private String fullTokenValue;
	private String alias;
	private String value;
	private Arguments arguments;
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
	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getValue() {
		return value;
	}

	public QueryToken setValue(String value) {
		this.value = value;
		return this;
	}

	public Arguments getArguments() {
		return arguments;
	}

	public QueryToken setArguments(Arguments arguments) {
		this.arguments = arguments;
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
