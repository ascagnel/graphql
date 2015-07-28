package com.dibs.graphql.serialize;

import java.io.IOException;
import java.io.OutputStream;

public interface Serializer<D> {

	public void serialize(OutputStream outputStream, D data) throws IOException;
}
