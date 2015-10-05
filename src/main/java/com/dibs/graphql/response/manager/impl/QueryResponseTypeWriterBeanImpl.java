package com.dibs.graphql.response.manager.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import com.dibs.graphql.response.manager.QueryResponseTypeWriter;

public class QueryResponseTypeWriterBeanImpl implements QueryResponseTypeWriter {
	
	@Override
	public void writeProperty(Object response, String fieldName, Object fieldValue) {
		try {
			PropertyUtils.setProperty(response, fieldName, fieldValue);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException("Unable to write field [" + fieldName + "] on class [" + response.getClass() + "]");
		}
	}
}
