package com.dibs.graphql.breakroom.data;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.dibs.graphql.data.QueryTree;

public class VendingMachineFilter {

	private Long id;
	private String servicedBy;
	private QueryTree query;
	
	public VendingMachineFilter() {
	}

	public Long getId() {
		return id;
	}

	public VendingMachineFilter setId(Long id) {
		this.id = id;
		return this;
	}

	public String getServicedBy() {
		return servicedBy;
	}

	public VendingMachineFilter setServicedBy(String servicedBy) {
		this.servicedBy = servicedBy;
		return this;
	}
	
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}

	public QueryTree getQuery() {
		return query;
	}

	public VendingMachineFilter setQuery(QueryTree query) {
		this.query = query;
		return this;
	}

	
}
