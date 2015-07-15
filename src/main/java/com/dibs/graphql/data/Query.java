package com.dibs.graphql.data;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Query {
	private String name;
	private List<Query> subQueries;
	private Arguments arguments;

	public Query() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Query> getSubQueries() {
		return subQueries;
	}

	public void setSubQueries(List<Query> subQueries) {
		this.subQueries = subQueries;
	}

	public Arguments getArguments() {
		return arguments;
	}

	public void setArguments(Arguments arguments) {
		this.arguments = arguments;
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
		
		Query other = (Query) o;

		return new EqualsBuilder()
			.append(getName(), other.getName())
			.append(getArguments(), other.getArguments())
			.append(getSubQueries(), other.getSubQueries())
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 23)
			.append(getName())
			.append(getArguments())
			.append(getSubQueries())
			.hashCode();
	}
	
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}
}
