package com.dibs.graphql.response.type.factory;

import java.util.HashMap;
import java.util.Map;

import com.dibs.graphql.response.type.factory.impl.QueryResponseTypeFactoryGsonImpl;
import com.dibs.graphql.response.type.factory.impl.QueryResponseTypeFactoryMapImpl;
import com.dibs.graphql.response.type.factory.impl.QueryResponseTypeFactoryReflectionImpl;
import com.dibs.graphql.response.type.registry.impl.BaseQueryResponseTypeRegistryImpl;
import com.google.gson.JsonElement;

public class QueryResponseTypeFactoryRegistry extends BaseQueryResponseTypeRegistryImpl<QueryResponseTypeFactory>{

	private static final QueryResponseTypeFactoryRegistry instance = new QueryResponseTypeFactoryRegistry();
	
	private QueryResponseTypeFactoryRegistry() {
		super();
		
		initDefaults();
	}
	
	private void initDefaults() {
		Map<Class<?>, QueryResponseTypeFactory> factories = new HashMap<>();
		factories.put(Map.class, new QueryResponseTypeFactoryMapImpl());
		factories.put(JsonElement.class, new QueryResponseTypeFactoryGsonImpl());
		
		setDefaultTypeManager(new QueryResponseTypeFactoryReflectionImpl());
		setTypeManagers(factories);
	}
	
	public static QueryResponseTypeFactoryRegistry getInstance() {
		return instance;
	}
	
}