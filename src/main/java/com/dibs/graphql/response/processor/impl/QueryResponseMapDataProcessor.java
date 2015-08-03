package com.dibs.graphql.response.processor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dibs.graphql.data.request.Query;

public class QueryResponseMapDataProcessor extends BaseQueryResponseDataProcessor<Map<String, Object>> {
	
	public QueryResponseMapDataProcessor(Query query) {
		super(query);
	}
	
	@Override
	public Map<String, Object> process(Object bean) {		
		return process(query, bean);
	}
	
	private Map<String, Object> process(Query query, Object bean) {
		Map<String, Object> beanMap = new HashMap<>();
		
		if (query.getSubQueries() != null) {
			for (Query subQuery : query.getSubQueries()) {
				String subQueryName = subQuery.getName();
				
				Object subQueryBean = getProperty(bean, subQueryName);
				
				if (subQueryBean == null || subQuery.getSubQueries() == null) {
					beanMap.put(subQueryName, subQueryBean);
					continue;
				}
				
				if (Iterable.class.isAssignableFrom(subQueryBean.getClass())) {
					List<Map<String, Object>> subQueryValues = new ArrayList<>();

					for (Object entry : (Iterable<?>) subQueryBean) {
						subQueryValues.add(process(subQuery, entry));
					}

					beanMap.put(subQueryName, subQueryValues);
				} else {
					Map<String, Object> subQueryValues = process(subQuery, subQueryBean);
					beanMap.put(subQueryName, subQueryValues);
				}
			}
		} else {
			Object value = getProperty(beanMap, query.getName());
			beanMap.put(query.getName(), value);
		}
		
		return beanMap;
	}

}
