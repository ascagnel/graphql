package com.dibs.graphql.data.schema;

import java.util.List;

public interface HasInputFields {

	public List<InputValue> getInputFields();
	public void setInputFields(List<InputValue> inputFields);
}
