package com.dibs.graphql.parser.data;

import java.util.Map;

public class GraphQLToken {
	
	private String fullTokenValue;
	private String value;
	private Map<String, String> attributes;
	private GraphQLTokenType tokenType;
	
	public GraphQLToken() {
	}

	public String getFullTokenValue() {
		return fullTokenValue;
	}

	public GraphQLToken setFullTokenValue(String fullTokenValue) {
		this.fullTokenValue = fullTokenValue;
		return this;
	}

	public String getValue() {
		return value;
	}

	public GraphQLToken setValue(String value) {
		this.value = value;
		return this;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public GraphQLToken setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
		return this;
	}

	public GraphQLTokenType getTokenType() {
		return tokenType;
	}

	public GraphQLToken setTokenType(GraphQLTokenType tokenType) {
		this.tokenType = tokenType;
		return this;
	}
}
