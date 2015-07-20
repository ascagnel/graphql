package com.dibs.graphql.data.schema;

import java.util.List;

public interface HasEnumValues {

	public List<EnumValue> getEnumValues();
	public void setEnumValues(List<EnumValue> enumValues);
	
	public List<EnumValue> getEnumValues(Boolean includeDeprecated);
	
}
