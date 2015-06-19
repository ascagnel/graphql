package com.dibs.graphql.reader;

import java.io.Closeable;

import com.dibs.graphql.parser.data.GraphQLToken;

public interface GraphQLTokenReader extends Closeable {

	public boolean hasNext();
	public GraphQLToken next() throws Exception;
}
