package com.dibs.graphql.parser;

import com.dibs.graphql.data.Query;
import com.dibs.graphql.data.QueryBuilder;
import com.dibs.graphql.data.parse.QueryToken;

public class GraphQLNodeFactory {

	public static Query fromToken(QueryToken token) {
		Query node = new QueryBuilder()
			.name(token.getValue())
			.params(token.getAttributes())
			.build();
		
		return node;
	}
}
