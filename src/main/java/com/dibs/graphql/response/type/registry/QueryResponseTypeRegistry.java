package com.dibs.graphql.response.type.registry;

public interface QueryResponseTypeRegistry<K, V> {

	public V getTypeManager(K k);
}
