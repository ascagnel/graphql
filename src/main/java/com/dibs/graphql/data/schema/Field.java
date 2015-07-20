package com.dibs.graphql.data.schema;

import java.util.List;

public class Field implements Deprecatable {
	private String name;
	private String description;
	private List<InputValue> args;
	private TypeKind type;
	private Boolean isDeprecated;
	private String deprecationReason;
	
	public Field() {
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

	public List<InputValue> getArgs() {
		return args;
	}

	public void setArgs(List<InputValue> args) {
		this.args = args;
	}

	public TypeKind getType() {
		return type;
	}

	public void setType(TypeKind type) {
		this.type = type;
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
