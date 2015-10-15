package com.dibs.graphql.response.type.writer;

import java.util.HashMap;
import java.util.Map;

import com.dibs.graphql.response.type.registry.impl.BaseQueryResponseTypeRegistryImpl;
import com.dibs.graphql.response.type.writer.impl.QueryResponseTypeWriterBeanImpl;
import com.dibs.graphql.response.type.writer.impl.QueryResponseTypeWriterGsonImpl;
import com.dibs.graphql.response.type.writer.impl.QueryResponseTypeWriterMapImpl;
import com.google.gson.JsonElement;

public class QueryResponseTypeWriterRegistry extends BaseQueryResponseTypeRegistryImpl<QueryResponseTypeWriter>{

	private static final QueryResponseTypeWriterRegistry instance = new QueryResponseTypeWriterRegistry();
	
	private QueryResponseTypeWriterRegistry() {
		super();
		
		initDefaults();
	}
	
	private void initDefaults() {
		Map<Class<?>, QueryResponseTypeWriter> writers = new HashMap<>();
		writers.put(Map.class, new QueryResponseTypeWriterMapImpl());
		writers.put(JsonElement.class, new QueryResponseTypeWriterGsonImpl());
		
		setDefaultTypeManager(new QueryResponseTypeWriterBeanImpl());
		setTypeManagers(writers);
	}
	
	public static QueryResponseTypeWriterRegistry getInstance() {
		return instance;
	}
	
}
