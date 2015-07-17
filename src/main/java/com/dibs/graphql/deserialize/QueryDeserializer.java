package com.dibs.graphql.deserialize;

import java.io.InputStream;

import com.dibs.graphql.data.Query;

public interface QueryDeserializer {

	public Query deserialize(InputStream inputStream) throws SerializationException;
	
}
