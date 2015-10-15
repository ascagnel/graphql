package com.dibs.graphql.breakroom.data;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.dibs.graphql.data.request.QueryTree;

public class VendingMachineFilter {

	private Integer id;
	private String servicedBy;
	private QueryTree query;
	
	public VendingMachineFilter() {
	}

	public Integer getId() {
		return id;
	}

	public VendingMachineFilter setId(Integer id) {
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
