package com.dibs.graphql.serialize.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.dibs.graphql.data.Response;
import com.google.gson.Gson;

public class ResponseSerializerImpl {

	public void serialize(Response response, OutputStream stream) throws IOException {
		Map<String, Object> responseData = response.getData();
		Gson gson = new Gson();
		String json = gson.toJson(responseData);
		stream.write(json.getBytes());
	}
}
