package com.dibs.graphql.response.type.reader;

import java.util.HashMap;
import java.util.Map;

import com.dibs.graphql.response.type.reader.impl.QueryResponseTypeReaderBeanImpl;
import com.dibs.graphql.response.type.reader.impl.QueryResponseTypeReaderGsonImpl;
import com.dibs.graphql.response.type.reader.impl.QueryResponseTypeReaderMapImpl;
import com.dibs.graphql.response.type.registry.impl.BaseQueryResponseTypeRegistryImpl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class QueryResponseTypeReaderRegistry extends BaseQueryResponseTypeRegistryImpl<QueryResponseTypeReader>{

	private static final QueryResponseTypeReaderRegistry instance = new QueryResponseTypeReaderRegistry();
	
	private QueryResponseTypeReaderRegistry() {
		super();
		
		initDefaults();
	}
	
	private void initDefaults() {
		Map<Class<?>, QueryResponseTypeReader> readers = new HashMap<>();
		readers.put(Map.class, new QueryResponseTypeReaderMapImpl());
		readers.put(JsonElement.class, new QueryResponseTypeReaderGsonImpl());
		readers.put(JsonObject.class, new QueryResponseTypeReaderGsonImpl());
		
		setDefaultTypeManager(new QueryResponseTypeReaderBeanImpl());
		setTypeManagers(readers);
	}
	
	public static QueryResponseTypeReaderRegistry getInstance() {
		return instance;
	}
	
}
