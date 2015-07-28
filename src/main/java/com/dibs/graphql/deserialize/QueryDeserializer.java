package com.dibs.graphql.deserialize;

import com.dibs.graphql.data.request.Query;

public interface QueryDeserializer {

	public Query deserialize() throws SerializationException;
	
}
