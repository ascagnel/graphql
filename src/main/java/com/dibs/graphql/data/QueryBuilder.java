package com.dibs.graphql.data;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

	private String name;
	private String alias;
	private List<Query> subQueries;
	private Arguments arguments;
	
	public QueryBuilder() {
	}
	
	public QueryBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public QueryBuilder alias(String alias) {
		this.alias = alias;
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
	
	public QueryBuilder arguments(Arguments arguments) {
		this.arguments = arguments;
		return this;
	}
	
	public QueryBuilder argument(String key, Object value) {
		if (arguments == null) {
			arguments = new Arguments();
		}
		
		arguments.put(key, value);
		
		return this;
	}
	
	public Query build() {
		Query query = new Query();
		query.setName(name);
		query.setAlias(alias);
		query.setArguments(arguments);
		query.setSubQueries(subQueries);
		
		return query;
	}
}
