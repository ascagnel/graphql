package com.dibs.graphql.response.processor.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import com.dibs.graphql.data.request.Query;
import com.dibs.graphql.response.processor.QueryResponseDataProcessor;

public abstract class BaseQueryResponseDataProcessor<T> implements QueryResponseDataProcessor<T> {

	protected Query query;

	public BaseQueryResponseDataProcessor(Query query) {
		this.query = query;
	}
	
	protected Object getProperty(Object bean, String fieldName) {
		try {
			return PropertyUtils.getProperty(bean, fieldName);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException("Unable to read field [" + fieldName + "] on class [" + bean.getClass() + "]");
		}
	}
	
}
