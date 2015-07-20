package com.dibs.graphql.data.schema;

public interface Deprecatable {

	public Boolean isDeprecated();
	public void setIsDeprecated(Boolean deprecated);
	
	public String getDeprecationReason();
	public void setDeprecationReason(String deprecationReason);
}
