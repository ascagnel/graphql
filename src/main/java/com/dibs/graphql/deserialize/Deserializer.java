package com.dibs.graphql.deserialize;

import java.io.IOException;
import java.io.InputStream;

public interface Deserializer<D> {

	public D deserialize(InputStream inputStream) throws IOException, DeserializationException;
}
