package com.dibs.graphql.response.type.writer.impl;

import com.dibs.graphql.response.type.writer.QueryResponseTypeWriter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class QueryResponseTypeWriterGsonImpl implements QueryResponseTypeWriter {
	
	@Override
	public void writeProperty(Object response, String fieldName, Object fieldValue) {
		JsonObject responseJson = null;
		
		if (response instanceof JsonObject) {
			responseJson = (JsonObject) response;
		} else if (response instanceof JsonElement) {
			JsonElement sourceJsonElement = (JsonElement) response;

			if (!sourceJsonElement.isJsonObject()) {
				throw new RuntimeException("Cannot write property [" + fieldName + "] of non-JsonObject [" + sourceJsonElement + "]");
			}
			
			responseJson = sourceJsonElement.getAsJsonObject();
		} else {
			throw new RuntimeException("Unsupported type [" + response.getClass() + "]");
		}
		
		responseJson.add(fieldName, getValueAsElement(fieldValue));
	}

	@Override
	public void addPropertyToIterable(Object response, String fieldName, Object fieldValue) {
		if (response instanceof JsonElement) {
			if (((JsonElement) response).isJsonArray()) {
				JsonArray array = (JsonArray) response;
				array.add(getValueAsElement(fieldValue));
				return;
			}			
		}
		
		throw new RuntimeException("Unsupported iterable type [" + response.getClass() + "]");
	}
	
	private JsonElement getValueAsElement(Object fieldValue) {
		JsonElement fieldValueJson = null;
				
		if (fieldValue instanceof JsonElement) {
			fieldValueJson = (JsonElement) fieldValue;
		} else {
			fieldValueJson = new Gson().toJsonTree(fieldValue);
		}
		
		return fieldValueJson;
	}
}
