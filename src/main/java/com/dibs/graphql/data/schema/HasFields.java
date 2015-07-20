package com.dibs.graphql.data.schema;

import java.util.Set;

public interface HasFields {

	public Set<Field> getFields();
	public void setFields(Set<Field> fields);
	
	public Set<Field> getFields(Boolean includeDeprecated);
}
