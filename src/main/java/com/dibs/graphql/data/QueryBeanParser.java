package com.dibs.graphql.data;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.dibs.graphql.data.request.Query;

public class QueryBeanParser {
	
	public static Map<String, Object> parse(Query query, Object bean) {
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
					List<Map<String, Object>> subQueryValues = new ArrayList<>();;

					 for (Object entry : (Iterable<?>) subQueryBean) {
						 subQueryValues.add(parse(subQuery, entry));
					 }
					
					 beanMap.put(subQueryName, subQueryValues);
				} else {
					Map<String, Object> subQueryValues = parse(subQuery, subQueryBean);
					beanMap.put(subQueryName, subQueryValues);
				}
			}
		} else {
			Object value = getProperty(beanMap, query.getName());
			beanMap.put(query.getName(), value);
		}
		
		return beanMap;
	}
	
	private static Object getProperty(Object bean, String fieldName) {
		try {
			return PropertyUtils.getProperty(bean, fieldName);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException("Unable to read field [" + fieldName + "] on class [" + bean.getClass() + "]");
		}
	}
}
