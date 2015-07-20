package com.dibs.graphql.data.schema;

import java.util.List;

public interface HasSubtypes {

	public List<Type> getPossibleTypes();
	public void setPossibleTypes(List<Type> possibleTypes);
}
