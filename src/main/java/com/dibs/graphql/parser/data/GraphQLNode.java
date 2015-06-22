package com.dibs.graphql.parser.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class GraphQLNode {
	private String value;
	private List<GraphQLNode> children;
	private Map<String, String> attributes;
	
	public GraphQLNode() {
	}
	
	public String getValue() {
		return value;
	}
	
	public GraphQLNode value(String value) {
		this.value = value;
		return this;
	}
	
	public List<GraphQLNode> getChildren() {
		return children;
	}
	
	public GraphQLNode children(List<GraphQLNode> children) {
		this.children = children;
		return this;
	}
	
	public GraphQLNode child(GraphQLNode child) {
		if (children == null) {
			children = new ArrayList<>();
		}
		
		children.add(child);
		
		return this;
	}
	
	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	public GraphQLNode attributes(Map<String, String> attributes) {
		this.attributes = attributes;
		return this;
	}
	
	public GraphQLNode attribute(String key, String value) {
		if (attributes == null) {
			attributes = new HashMap<String, String>();
		}
		
		attributes.put(key, value);
		
		return this;
	}
	
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		
		if (o == this) {
			return true;
		}
		
		if (o.getClass() != getClass()) {
			return false;
		}
		
		GraphQLNode other = (GraphQLNode) o;

		return new EqualsBuilder()
			.append(getValue(), other.getValue())
			.append(getAttributes(), other.getAttributes())
			.append(getChildren(), other.getChildren())
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 23)
			.append(getValue())
			.append(getAttributes())
			.append(getChildren())
			.hashCode();
	}
	
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}
}
