package com.dibs.graphql.response.processor;

import java.util.ArrayList;
import java.util.List;

public class QueryResponseDataProcessorUtil {

	public static <P, A> Iterable<P> processAll(QueryResponseDataProcessor<P> processor, Iterable<A> beans) {
		List<P> processedBeans = new ArrayList<>();
		
		for (A bean : beans) {
			processedBeans.add(processor.process(bean));
		}
		
		return processedBeans;
	}
}
