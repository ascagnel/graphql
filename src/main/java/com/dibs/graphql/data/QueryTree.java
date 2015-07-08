package com.dibs.graphql.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class QueryTree {
	private static final Log LOG = LogFactory.getLog(QueryTree.class);
	
	private Query rootNode;
	private Map<String, String> params;
	private Map<String, Query> childMap;
	private Set<String> fields;
	
	private QueryTree(Query rootNode) {
		this.rootNode = rootNode;
	}
	
	private void init() {
		params = new HashMap<>();
		
		if (rootNode.getParams() != null) {
			params.putAll(rootNode.getParams());
		}
		
		childMap = new HashMap<>();
		
		if (rootNode.getSubQueries() != null) {
			for (Query child : rootNode.getSubQueries()) {
				childMap.put(child.getName(), child);
			}
		}
	}
	
	public Set<String> getChildFields() {
		if (fields == null) {
			fields = new HashSet<>();
			
			if (rootNode.getSubQueries() != null) {
				for (Query node : rootNode.getSubQueries()) {
					fields.add(node.getName());
				}
			}
		}
		
		return fields;
	}

	public boolean containsChild(String childName) {
		return getChildFields().contains(childName);
	}
	
	public void addParamValue(String key, String value) {
		params.put(key, value);
	}
	
	public String getParamValue(String paramKey) {
		return params.get(paramKey);
	}
	
	public QueryTree getChildTree(String childName) {
		Query child = childMap.get(childName);
		
		if (child == null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("No child information available for [" + childName + "]");
			}
			
			return null;
		}
		
		return QueryTree.fromQuery(child);
	}
	
	public static QueryTree fromQuery(Query rootNode) {
		QueryTree provider = new QueryTree(rootNode);
		provider.init();
		
		return provider;
	}
}
