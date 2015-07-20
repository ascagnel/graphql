package com.dibs.graphql.data.schema;

public class EnumValue implements Deprecatable {

	private String name;
	private String description;
	private Boolean isDeprecated;
	private String deprecationReason;
	
	public EnumValue() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean isDeprecated() {
		return isDeprecated;
	}

	public void setIsDeprecated(Boolean isDeprecated) {
		this.isDeprecated = isDeprecated;
	}

	public String getDeprecationReason() {
		return deprecationReason;
	}

	public void setDeprecationReason(String deprecationReason) {
		this.deprecationReason = deprecationReason;
	}
}
