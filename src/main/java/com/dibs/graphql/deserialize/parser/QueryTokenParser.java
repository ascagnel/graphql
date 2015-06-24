package com.dibs.graphql.parser.reader;

import java.io.Closeable;
import java.io.IOException;

import com.dibs.graphql.data.parse.QueryToken;

public interface QueryTokenReader extends Closeable {

	public boolean hasNext();
	public QueryToken next() throws IOException;
}
