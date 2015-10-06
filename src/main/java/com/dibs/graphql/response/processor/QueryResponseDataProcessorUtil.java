package com.dibs.graphql.response.processor;

import java.util.ArrayList;
import java.util.List;

import com.dibs.graphql.response.processor.impl.QueryResponseProcessor;

public class QueryResponseDataProcessorUtil {

	public static <P, A> Iterable<P> processAll(QueryResponseProcessor processor, Iterable<A> beans, Class<P> responseType) {
		List<P> processedBeans = new ArrayList<>();
		
		for (A bean : beans) {
			processedBeans.add((P) processor.process(bean, responseType));
		}
		
		return processedBeans;
	}
}
