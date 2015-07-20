package com.dibs.graphql.data.schema;

import java.util.List;
import java.util.Set;

public class Interface extends Type implements HasFields, HasSubtypes {
	private Set<Field> fields;
	private List<Type> possibleTypes;
	
	public Interface() {
		super(TypeKind.INTERFACE);
	}
	
	public Set<Field> getFields() {
		return getFields(Boolean.FALSE);
	}
	
	public void setFields(Set<Field> fields) {
		this.fields = fields;
	}
	
	public List<Type> getPossibleTypes() {
		return possibleTypes;
	}
	
	public void setPossibleTypes(List<Type> possibleTypes) {
		this.possibleTypes = possibleTypes;
	}
	
	@Override
	public Set<Field> getFields(Boolean includeDeprecated) {
		return null;
	}
	
	
}
