package com.dibs.graphql.data.schema;

import java.util.List;

public class InputObject extends Type implements HasInputFields {

	public List<InputValue> inputFields;
	
	public InputObject() {
		super(TypeKind.INPUT_OBJECT);
	}

	public List<InputValue> getInputFields() {
		return inputFields;
	}

	public void setInputFields(List<InputValue> inputFields) {
		this.inputFields = inputFields;
	}
}
