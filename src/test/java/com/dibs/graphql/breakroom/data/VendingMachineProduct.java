package com.dibs.graphql.breakroom.data;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class VendingMachineProduct {

	public static final String ID_PROPERTY = "id";
	private Integer id;
	private String name;
	private Long calories;
	private String description;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public VendingMachineProduct() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCalories() {
		return calories;
	}

	public void setCalories(Long calories) {
		this.calories = calories;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}
}
