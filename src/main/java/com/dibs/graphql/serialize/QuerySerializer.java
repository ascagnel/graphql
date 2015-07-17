package com.dibs.graphql.serialize;

import java.io.OutputStream;

import com.dibs.graphql.data.Query;
import com.dibs.graphql.deserialize.SerializationException;

public interface QuerySerializer {
	public void serialize(OutputStream stream, Query query, boolean isPrettyPrint) throws SerializationException;
}
