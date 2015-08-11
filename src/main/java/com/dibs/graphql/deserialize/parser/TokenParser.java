package com.dibs.graphql.deserialize.parser;

import java.io.IOException;

import com.dibs.graphql.deserialize.DeserializationException;

public interface TokenParser<D> {

	public StreamReader getStreamReader();
	public D next() throws IOException, DeserializationException;
}
