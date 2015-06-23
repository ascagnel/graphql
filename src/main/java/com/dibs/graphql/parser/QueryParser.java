package com.dibs.graphql.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.data.Query;
import com.dibs.graphql.data.parse.QueryToken;
import com.dibs.graphql.data.parse.TokenType;
import com.dibs.graphql.reader.QueryTokenReader;
import com.dibs.graphql.reader.impl.StreamingQueryTokenReader;

public class QueryParser {
	private static final Log LOG = LogFactory.getLog(QueryParser.class);
	
	private Stack<Query> nodes;
	
	private QueryTokenReader tokenReader;
	
	public QueryParser() {
	}
	
	public Query parse(InputStream inputStream) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Parsing GraphQL input stream");
		}
		
		nodes = new Stack<>();
		
		Query rootNode = null;
		
		tokenReader = new StreamingQueryTokenReader(inputStream);
		
		while (tokenReader.hasNext()) {
			QueryToken token = tokenReader.next();
			Query node = GraphQLNodeFactory.fromToken(token);
			
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
			
			if (token.getTokenType() == TokenType.OBJECT_START) {
				nodes.push(node);
			} else if (token.getTokenType() == TokenType.OBJECT_END) {
				nodes.pop();
			}
		}
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("Finished parsing input stream, closing GraphQL token reader.");
		}
		
		tokenReader.close();
		
		return rootNode;
	}
	
}
