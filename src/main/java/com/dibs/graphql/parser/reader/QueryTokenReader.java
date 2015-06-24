package com.dibs.graphql.deserialize.parser;

import java.io.Closeable;
import java.io.IOException;

import com.dibs.graphql.parser.data.QueryToken;

public interface QueryTokenParser extends Closeable {

	public boolean hasNext();
	public QueryToken next() throws IOException;
}
