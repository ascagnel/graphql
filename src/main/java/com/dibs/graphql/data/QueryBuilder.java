package com.dibs.graphql.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryBuilder {

	private String name;
	private List<Query> subQueries;
	private Map<String, String> params;
	
	public QueryBuilder() {
	}
	
	public QueryBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public QueryBuilder subQueries(List<Query> subQueries) {
		this.subQueries = subQueries;
		return this;
	}
	
	public QueryBuilder subQuery(Query subQuery) {
		if (subQueries == null) {
			subQueries = new ArrayList<>();
		}
		
		subQueries.add(subQuery);
		
		return this;
	}
	
	public QueryBuilder params(Map<String, String> params) {
		this.params = params;
		return this;
	}
	
	public QueryBuilder param(String key, String value) {
		if (params == null) {
			params = new HashMap<>();
		}
		
		params.put(key, value);
		
		return this;
	}
	
	public Query build() {
		Query query = new Query();
		query.setName(name);
		query.setParams(params);
		query.setSubQueries(subQueries);
		
		return query;
	}
}
