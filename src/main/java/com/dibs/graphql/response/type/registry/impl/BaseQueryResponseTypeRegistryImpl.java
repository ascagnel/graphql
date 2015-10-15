package com.dibs.graphql.response.type.registry.impl;

import java.util.Map;

import com.dibs.graphql.response.type.registry.QueryResponseTypeRegistry;

public class BaseQueryResponseTypeRegistryImpl<V> implements QueryResponseTypeRegistry<Class<?>, V> {

	private V defaultTypeManager;
	private Map<Class<?>, V> typeManagers;
	
	public void setDefaultTypeManager(V defaultTypeManager) {
		this.defaultTypeManager = defaultTypeManager;
	}
	
	public void setTypeManagers(Map<Class<?>, V> typeManagers) {
		this.typeManagers = typeManagers;
	}

	@Override
	public V getTypeManager(Class<?> key) {
		V typeManger = (V) typeManagers.get(key);
		
		if (typeManger == null) {
			return defaultTypeManager;
		}
		
		return typeManger;
	}
}
