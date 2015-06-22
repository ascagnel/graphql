package com.dibs.graphql.parser;

import java.io.InputStream;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.parser.data.GraphQLNode;
import com.dibs.graphql.parser.data.GraphQLToken;
import com.dibs.graphql.parser.data.GraphQLTokenType;
import com.dibs.graphql.reader.GraphQLTokenReader;
import com.dibs.graphql.reader.impl.InputStreamGraphQLTokenReader;

public class GraphQLParser {
	private static final Log LOG = LogFactory.getLog(GraphQLParser.class);
	
	private Stack<GraphQLNode> nodes;
	
	private GraphQLTokenReader tokenReader;
	
	public GraphQLParser() {
	}
	
	public GraphQLNode parse(InputStream inputStream) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Parsing GraphQL input stream");
		}
		
		nodes = new Stack<>();
		
		GraphQLNode rootNode = null;
		
		tokenReader = new InputStreamGraphQLTokenReader(inputStream);
		
		while (tokenReader.hasNext()) {
			GraphQLToken token = tokenReader.next();
			GraphQLNode node = GraphQLNodeFactory.fromToken(token);
			
			if (rootNode == null) {
				rootNode = node;
			}
			
			// This check is a little weird but we have to be careful
			// about adding tokens that are only "}" as a child
			if (!nodes.isEmpty() && token.getValue() != null) {
				GraphQLNode prevNode = nodes.peek();
				prevNode.child(node);
			}
			
			if (token.getTokenType() == GraphQLTokenType.OBJECT_START) {
				nodes.push(node);
			} else if (token.getTokenType() == GraphQLTokenType.OBJECT_END) {
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
