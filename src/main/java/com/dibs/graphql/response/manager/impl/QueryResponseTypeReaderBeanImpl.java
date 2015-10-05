package com.dibs.graphql.response.manager.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import com.dibs.graphql.response.manager.QueryResponseTypeReader;

public class QueryResponseTypeReaderBeanImpl implements QueryResponseTypeReader {

	@Override
	public Object readProperty(Object sourceBean, String fieldName) {
		try {
			return PropertyUtils.getProperty(sourceBean, fieldName);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException("Unable to read field [" + fieldName + "] on class [" + sourceBean.getClass() + "]");
		}
	}

	
}
