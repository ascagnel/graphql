package com.dibs.graphql.data.schema;

import java.util.List;

public class Union extends Type implements HasSubtypes {

	private List<Type> possibleTypes;
	
	public Union() {
		super(TypeKind.UNION);
	}

	public List<Type> getPossibleTypes() {
		return possibleTypes;
	}

	public void setPossibleTypes(List<Type> possibleTypes) {
		this.possibleTypes = possibleTypes;
	}
}
