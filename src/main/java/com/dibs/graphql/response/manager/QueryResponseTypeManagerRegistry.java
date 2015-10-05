package com.dibs.graphql.response.manager;

import java.util.Map;

public class QueryResponseTypeManagerRegistry {	
	// Thread safe
	private static final QueryResponseTypeManagerRegistry instance = new QueryResponseTypeManagerRegistry();
	
	private QueryResponseTypeReader defaultQueryResponseTypeReader;
	private Map<Class<?>, QueryResponseTypeReader> typeReaders;
	
	private QueryResponseTypeWriter defaultQueryResponseTypeWriter;
	private Map<Class<?>, QueryResponseTypeWriter> typeWriters;
	
	private QueryResponseTypeFactory defaultQueryResponseTypeFactory;
	private Map<Class<?>, QueryResponseTypeFactory> typeFactories;
	
	public void setDefaultQueryResponseTypeReader(QueryResponseTypeReader defaultQueryResponseTypeReader) {
		this.defaultQueryResponseTypeReader = defaultQueryResponseTypeReader;
	}

	public void setTypeReaders(Map<Class<?>, QueryResponseTypeReader> typeReaders) {
		this.typeReaders = typeReaders;
	}

	public void setDefaultQueryResponseTypeWriter(QueryResponseTypeWriter defaultQueryResponseTypeWriter) {
		this.defaultQueryResponseTypeWriter = defaultQueryResponseTypeWriter;
	}

	public void setTypeWriters(Map<Class<?>, QueryResponseTypeWriter> typeWriters) {
		this.typeWriters = typeWriters;
	}

	public void setDefaultQueryResponseTypeFactory(QueryResponseTypeFactory defaultQueryResponseTypeFactory) {
		this.defaultQueryResponseTypeFactory = defaultQueryResponseTypeFactory;
	}

	public void setTypeFactories(Map<Class<?>, QueryResponseTypeFactory> typeFactories) {
		this.typeFactories = typeFactories;
	}

	public QueryResponseTypeFactory getTypeFactory(Class<?> responseType) {
		QueryResponseTypeFactory factory = typeFactories.get(responseType);
		
		if (factory == null) {
			return defaultQueryResponseTypeFactory;
		}
		
		return factory;
	}
	
	public QueryResponseTypeReader getTypeReader(Class<?> responseType) {
		QueryResponseTypeReader reader = typeReaders.get(responseType);
		
		if (reader == null) {
			return defaultQueryResponseTypeReader;
		}
		
		return reader;
	}
	
	public QueryResponseTypeWriter getTypeWriter(Class<?> responseType) {
		QueryResponseTypeWriter writer = typeWriters.get(responseType);
		
		if (writer == null) {
			return defaultQueryResponseTypeWriter;
		}
		
		return writer;
	}
	
	private QueryResponseTypeManagerRegistry() {
	}
	
	public static QueryResponseTypeManagerRegistry getInstance() {
		return instance;
	}
}
