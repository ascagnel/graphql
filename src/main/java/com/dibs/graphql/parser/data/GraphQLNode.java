package com.dibs.graphql.parser.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphQLNode {
	private String value;
	private List<GraphQLNode> children;
	private Map<String, String> attributes;
	
	public GraphQLNode() {
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public List<GraphQLNode> getChildren() {
		return children;
	}
	
	public void setChildren(List<GraphQLNode> children) {
		this.children = children;
	}
	
	public void addChild(GraphQLNode child) {
		if (children == null) {
			children = new ArrayList<>();
		}
		
		children.add(child);
	}
	
	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}
