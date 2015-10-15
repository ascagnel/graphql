package com.dibs.graphql.response.type.reader.impl;

import com.dibs.graphql.response.type.reader.QueryResponseTypeReader;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class QueryResponseTypeReaderGsonImpl implements QueryResponseTypeReader {

	@Override
	public Object readProperty(Object sourceBean, String fieldName) {
		JsonObject property = null;
		
		if (sourceBean instanceof JsonObject) {
			property = (JsonObject) sourceBean;
		} else if (sourceBean instanceof JsonElement) {
			JsonElement sourceJsonElement = (JsonElement) sourceBean;
		
			if (!sourceJsonElement.isJsonObject()) {
				throw new RuntimeException("Cannot read property [" + fieldName + "] of non-JsonObject [" + sourceJsonElement + "]");
			}
			
			property = sourceJsonElement.getAsJsonObject();
		} else {
			throw new RuntimeException("Unsupported type [" + sourceBean.getClass() + "]");
		}
		
		return property.get(fieldName);
	}
}
