package com.dibs.graphql.serialize.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.data.response.Response;
import com.dibs.graphql.serialize.Serializer;
import com.google.gson.Gson;

public class ResponseSerializerGsonImpl implements Serializer<Response>{
	private static final Log LOG = LogFactory.getLog(ResponseSerializerGsonImpl.class);
	
	private Gson gson;
	
	public ResponseSerializerGsonImpl() {
		init();
	}
	
	private void init() {
		LOG.info("Initializing Gson...");
		gson = new Gson();
	}
	
	public void serialize(OutputStream stream, Response response) throws IOException {
		OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
		gson.toJson(response, streamWriter);
		streamWriter.flush();
	}
}
