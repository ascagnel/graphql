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
import com.dibs.graphql.data.deserialize.QueryToken;
import com.dibs.graphql.data.deserialize.Punctuator;
import com.dibs.graphql.deserialize.QueryDeserializer;
import com.dibs.graphql.deserialize.SerializationException;
import com.dibs.graphql.deserialize.parser.QueryTokenParser;
import com.dibs.graphql.deserialize.parser.impl.StreamingQueryTokenParser;

public class QueryDeserializerStackImpl implements QueryDeserializer {
	private static final Log LOG = LogFactory.getLog(QueryDeserializerStackImpl.class);
	
	private Stack<Query> nodes;
	
	private QueryTokenParser tokenReader;
	
	public QueryDeserializerStackImpl() {
	}
	
	public Query deserialize(InputStream inputStream) throws SerializationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Parsing GraphQL input stream");
		}
		
		nodes = new Stack<>();
		
		Query rootNode = null;
		
		try {
			tokenReader = new StreamingQueryTokenParser(inputStream);
			
			while (tokenReader.hasNext()) {
				QueryToken token = tokenReader.next();
				
				Query node = new QueryBuilder()
					.name(token.getValue())
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
		} catch (IOException e) {
			throw new SerializationException(e);
		} finally {
			try {
				tokenReader.close();
			} catch (IOException e) {
				throw new SerializationException(e);
			}
		}
		return rootNode;
	}
	
}
