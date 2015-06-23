package com.dibs.graphql.reader;

import java.io.Closeable;

import com.dibs.graphql.data.parse.QueryToken;

public interface QueryTokenReader extends Closeable {

	public boolean hasNext();
	public QueryToken next() throws Exception;
}
