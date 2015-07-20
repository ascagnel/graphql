package com.dibs.graphql.data.schema;

public abstract class Type {
	
	private TypeKind kind;
	private String name;
	private String description;
	
	public Type(TypeKind kind) {
		setKind(kind);
	}

	public TypeKind getKind() {
		return kind;
	}

	public void setKind(TypeKind kind) {
		this.kind = kind;
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
}
