package com.dibs.graphql.response.processor.impl;

import java.lang.reflect.Array;

import com.dibs.graphql.data.request.Query;
import com.dibs.graphql.response.manager.QueryResponseTypeFactory;
import com.dibs.graphql.response.manager.QueryResponseTypeManagerRegistry;
import com.dibs.graphql.response.manager.QueryResponseTypeReader;
import com.dibs.graphql.response.manager.QueryResponseTypeWriter;
import com.dibs.graphql.response.processor.QueryResponseDataProcessor;

public class QueryResponseProcessor implements QueryResponseDataProcessor {
	
	private Query query;
	
	public QueryResponseProcessor(Query query) {
		this.query = query;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T process(Object bean, Class<T> responseType) {	
		return (T) process(query, bean, responseType);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T process(Query query, Object bean, Class<?> responseType) {
		QueryResponseTypeFactory typeFactory = QueryResponseTypeManagerRegistry.getInstance().getTypeFactory(responseType);
		QueryResponseTypeReader typeReader = QueryResponseTypeManagerRegistry.getInstance().getTypeReader(bean.getClass());
		QueryResponseTypeWriter typeWriter = QueryResponseTypeManagerRegistry.getInstance().getTypeWriter(responseType);

		T response = (T) typeFactory.initializeType(responseType, bean.getClass());
		
		if (query.getSubQueries() != null) {
			for (Query subQuery : query.getSubQueries()) {
				String subQueryName = subQuery.getName();
				
				Object subQueryBean = typeReader.readProperty(bean, subQueryName);
				
				if (subQueryBean == null || subQuery.getSubQueries() == null) {
					typeWriter.writeProperty(response, subQueryName, subQueryBean);
					continue;
				}
				
				if (subQueryBean instanceof Array) {
					Object[] newBeanArray = (Object[]) typeFactory.initializeType(responseType, subQueryBean.getClass());
					Object[] subQueryBeanArray = (Object[]) subQueryBean;
					
					for (int i = 0; i < subQueryBeanArray.length; i++) {
						newBeanArray[i] = (T) process(subQuery, subQueryBeanArray[i], responseType);
					}
					
					typeWriter.writeProperty(response, subQueryName, newBeanArray);
				} else if (Iterable.class.isAssignableFrom(subQueryBean.getClass())) {
					Object iterableHolder = typeFactory.initializeType(responseType, subQueryBean.getClass());

					for (Object entry : (Iterable<?>) subQueryBean) {
						typeWriter.addPropertyToIterable(iterableHolder, subQueryName, (T) process(subQuery, entry, responseType));
					}

					typeWriter.writeProperty(response, subQueryName, iterableHolder);
				} else {
					T subQueryValues = process(subQuery, subQueryBean, responseType);
					typeWriter.writeProperty(response, subQueryName, subQueryValues);
				}
			}
		} else {
			Object value = typeReader.readProperty(response, query.getName());
			typeWriter.writeProperty(response, query.getName(), value);
		}
		
		return response;
	}
}
