package com.dibs.graphql.response.type.writer.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;

import com.dibs.graphql.response.type.writer.QueryResponseTypeWriter;

public class QueryResponseTypeWriterBeanImpl implements QueryResponseTypeWriter {
	
	@Override
	public void writeProperty(Object response, String fieldName, Object fieldValue) {
		try {
			PropertyUtils.setProperty(response, fieldName, fieldValue);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException("Unable to write field [" + fieldName + "] on class [" + response.getClass() + "]");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addPropertyToIterable(Object response, String fieldName, Object fieldValue) {
		if (Collection.class.isAssignableFrom(response.getClass())) {
			((Collection) response).add(fieldValue);
		} else {
			throw new RuntimeException("No handler for iterable type [" + response.getClass() +"]");
		}
	}
}
