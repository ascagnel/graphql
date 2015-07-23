package com.dibs.graphql.deserialize.parser;

import java.io.IOException;

public interface TokenParser<D> {

	public D next() throws IOException;
}
