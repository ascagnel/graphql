package com.dibs.graphql.response.processor.impl;

import java.util.HashSet;
import java.util.Set;

import com.dibs.graphql.data.request.Query;
import com.dibs.graphql.prune.FieldTrimmer;

public class QueryResponseBeanDataProcessor<T> extends BaseQueryResponseDataProcessor<T> {

	public QueryResponseBeanDataProcessor(Query query) {
		super(query);
	}
	
	public T process(Object bean) {
		return process(query, bean);
	}
	
	@SuppressWarnings("unchecked")
	private T process(Query query, Object bean) {
		Set<String> subQueryFields = new HashSet<>();

		if (query.getSubQueries() != null) {			
			for (Query subQuery : query.getSubQueries()) {
				String subQueryName = subQuery.getName();

				subQueryFields.add(subQuery.getName());
				
				Object subQueryBean = getProperty(bean, subQueryName);

				if (subQueryBean == null || subQuery.getSubQueries() == null) {
					continue;
				}
				
				if (Iterable.class.isAssignableFrom(subQueryBean.getClass())) {
					for (Object entry : (Iterable<?>) subQueryBean) {
						process(subQuery, entry);
					}
				} else {
					process(subQuery, subQueryBean);
				}					
			}
		} 
		
		FieldTrimmer.trim(bean, subQueryFields);
		
		return (T) bean;
	}

}