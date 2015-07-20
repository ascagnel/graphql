package com.dibs.graphql.data.schema;

import java.util.List;

public class Enum extends Type implements HasEnumValues {
	
	private List<EnumValue> enumValues;

	public Enum() {
		super(TypeKind.ENUM);
	}

	public List<EnumValue> getEnumValues() {
		return getEnumValues(Boolean.FALSE);
	}

	public void setEnumValues(List<EnumValue> enumValues) {
		this.enumValues = enumValues;
	}

	@Override
	public List<EnumValue> getEnumValues(Boolean includeDeprecated) {
		return null;
	}
}
