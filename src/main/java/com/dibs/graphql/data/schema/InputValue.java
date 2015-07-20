package com.dibs.graphql.data.schema;

public class InputValue {
	private String name;
	private String description;
	private TypeKind type;
	private String defaultValue;
	
	public InputValue() {
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

	public TypeKind getType() {
		return type;
	}

	public void setType(TypeKind type) {
		this.type = type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}


