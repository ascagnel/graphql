package com.dibs.graphql.deserialize.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.data.Query;
import com.dibs.graphql.data.QueryBuilder;
import com.dibs.graphql.data.deserialize.Punctuator;
import com.dibs.graphql.data.deserialize.QueryToken;
import com.dibs.graphql.deserialize.Deserializer;
import com.dibs.graphql.deserialize.parser.StreamReader;
import com.dibs.graphql.deserialize.parser.impl.ArgumentParser;
import com.dibs.graphql.deserialize.parser.impl.QueryTokenParser;

public class QueryDeserializerImpl implements Deserializer<Query> {
	private static final Log LOG = LogFactory.getLog(QueryDeserializerImpl.class);
	
	public QueryDeserializerImpl() {
	}
	
	@Override
	public Query deserialize(InputStream inputStream) throws IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Parsing GraphQL input stream");
		}
		
		Stack<Query> nodes = new Stack<>();
		
		StreamReader streamReader = new StreamReader(inputStream);
		ArgumentParser argumentParser = new ArgumentParser(streamReader);
		QueryTokenParser tokenParser = new QueryTokenParser(argumentParser, streamReader);
				
		Query rootNode = null;
		
		try {			
			while (streamReader.ready()) {
				QueryToken token = tokenParser.next();
				
				Query node = new QueryBuilder()
					.name(token.getValue())
					.alias(token.getAlias())
					.arguments(token.getArguments())
					.build();
				
				if (rootNode == null) {
					rootNode = node;
				}
				
				// This check is a little weird but we have to be careful
				// about adding tokens that are only "}" as a child
				if (!nodes.isEmpty() && token.getValue() != null) {
					Query prevNode = nodes.peek();
					
					List<Query> subQueries = prevNode.getSubQueries();
					
					if (subQueries == null) {
						subQueries = new ArrayList<>();
						prevNode.setSubQueries(subQueries);
					}
					
					subQueries.add(node);
				}
				
				if (token.getTokenType() == Punctuator.OPEN_CURLY_BRACE) {
					nodes.push(node);
				} else if (token.getTokenType() == Punctuator.CLOSE_CURLY_BRACE) {
					nodes.pop();
				}
			}
			
			if (LOG.isDebugEnabled()) {
				LOG.debug("Finished parsing input stream, closing GraphQL token reader.");
			}
		} finally {
			streamReader.close();
		}
		
		return rootNode;
	}
	
}
