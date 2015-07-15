package com.dibs.graphql.breakroom.data;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.dibs.graphql.data.QueryTree;

public class VendingMachineProductFilter {

	private List<Integer> ids;
	private QueryTree query;

	public VendingMachineProductFilter() {
	}

	public List<Integer> getIds() {
		return ids;
	}

	public VendingMachineProductFilter setIds(List<Integer> ids) {
		this.ids = ids;
		return this;
	}

	public QueryTree getQuery() {
		return query;
	}

	public VendingMachineProductFilter setQuery(QueryTree query) {
		this.query = query;
		return this;
	}

	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}
}
