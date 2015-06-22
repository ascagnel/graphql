package com.dibs.graphql.parser;

import com.dibs.graphql.parser.data.GraphQLNode;
import com.dibs.graphql.parser.data.GraphQLToken;

public class GraphQLNodeFactory {

	public static GraphQLNode fromToken(GraphQLToken token) {
		GraphQLNode node = new GraphQLNode()
			.value(token.getValue())
			.attributes(token.getAttributes());
		
		return node;
	}
}
