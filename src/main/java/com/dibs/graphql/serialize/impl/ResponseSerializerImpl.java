package com.dibs.graphql.serialize.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.dibs.graphql.data.Response;
import com.google.gson.Gson;

public class ResponseSerializerImpl {

	public void serialize(Response response, OutputStream stream) throws IOException {
		OutputStreamWriter streamWriter = new OutputStreamWriter(stream);

		Gson gson = new Gson();
		gson.toJson(response, streamWriter);
	}
}
