package com.dibs.graphql.data.schema;

import java.util.List;
import java.util.Set;

public class Object extends Type implements HasFields, HasInterfaces{

	public Set<Field> fields;
	public List<Interface> interfaces;
	
	public Object() {
		super(TypeKind.OBJECT);
	}

	public Set<Field> getFields() {
		return getFields(Boolean.FALSE);
	}

	public void setFields(Set<Field> fields) {
		this.fields = fields;
	}

	public List<Interface> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<Interface> interfaces) {
		this.interfaces = interfaces;
	}

	@Override
	public Set<Field> getFields(Boolean includeDeprecated) {
		// TODO Auto-generated method stub
		return null;
	}
}
