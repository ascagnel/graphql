package com.dibs.graphql.reader.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.parser.data.GraphQLToken;
import com.dibs.graphql.parser.data.GraphQLTokenType;
import com.dibs.graphql.reader.GraphQLTokenReader;
import com.dibs.graphql.reader.impl.ReaderUtil.Token;

public class InputStreamGraphQLTokenReader implements GraphQLTokenReader {
	private static final Log LOG = LogFactory.getLog(InputStreamGraphQLTokenReader.class);
	
	private Reader reader;
	
	public InputStreamGraphQLTokenReader(InputStream inputStream) {
		init(inputStream);
	}
	
	private void init(InputStream inputStream) {
		reader = new InputStreamReader(inputStream);
	}
	
	public boolean hasNext() {
		try {
			return reader.ready();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Map<String, String> parseAttributes(Reader reader) throws IOException {
		Map<String, String> attributes = new HashMap<>();
		
		String attributeKey = null;
		Token token = ReaderUtil.readUntilToken(reader);
		
		// Loop on attributes
		while (!isAttributeTerminator(token)) {
			if (isAttributeKeyTerminator(token)) {
				attributeKey = nullIfEmpty(new String(token.getValue()));
			} else if (isAttributeValueTerminator(token)) {
				String attributeValue = nullIfEmpty(new String(token.getValue()));
				attributes.put(attributeKey, attributeValue);
			}
			
			token = ReaderUtil.readUntilToken(reader);
		}
		
		String attributeValue = nullIfEmpty(new String(token.getValue()));
		attributes.put(attributeKey, attributeValue);
		
		return attributes;
	}
	
	private boolean isAttributeStart(Token token) {
		GraphQLTokenType tokenType = token.getType();
		
		return tokenType == GraphQLTokenType.ATTRIBUTE_START;
	}
	
	private boolean isAttributeKeyTerminator(Token token) {
		GraphQLTokenType tokenType = token.getType();
		return tokenType == GraphQLTokenType.ATTRIBUTE_DELIM;
	}
	
	private boolean isAttributeValueTerminator(Token token) {
		GraphQLTokenType tokenType = token.getType();

		return tokenType == GraphQLTokenType.OBJECT_DELIM 
						|| tokenType ==  GraphQLTokenType.ATTRIBUTE_END;
	}
	
	private boolean isTokenValueTerminator(Token token) {
		GraphQLTokenType tokenType = token.getType();

		return tokenType == GraphQLTokenType.ATTRIBUTE_START 
						|| tokenType == GraphQLTokenType.OBJECT_DELIM 
						|| tokenType == GraphQLTokenType.OBJECT_END;
	}
	
	private boolean isTokenTerminator(Token token) {
		GraphQLTokenType tokenType = token.getType();

		return tokenType == GraphQLTokenType.OBJECT_START 
						|| tokenType == GraphQLTokenType.OBJECT_DELIM 
						|| tokenType == GraphQLTokenType.OBJECT_END;
	}
	
	private boolean isAttributeTerminator(Token token) {
		GraphQLTokenType tokenType = token.getType();

		return tokenType == GraphQLTokenType.ATTRIBUTE_END;
	}
	
	public GraphQLToken next() throws IOException {
		GraphQLToken graphQltoken = new GraphQLToken();

		Token token = ReaderUtil.readUntilToken(reader);
		
		// Token value (property name) is going to be the first value that comes back if it exists
		String graphQLTokenValue = nullIfEmpty(new String(token.getValue()));

		// Loop to see if there are additional tokens, such as attributes or filters
		while (!isTokenTerminator(token)) {
			if (isAttributeStart(token)) {
				graphQltoken.setAttributes(parseAttributes(reader));
			}
			
			token = ReaderUtil.readUntilToken(reader);
		}
		
		graphQltoken.setValue(graphQLTokenValue);
		graphQltoken.setTokenType(token.getType());
		
		return graphQltoken;
	}
	
	public void close() throws IOException {
		reader.close();
	}
	
	private String nullIfEmpty(String input) {
		if (input == null) {
			return null;
		}
		
		String trimmedInput = input.trim();
		
		if (trimmedInput.isEmpty()) {
			return null;
		}
		
		return trimmedInput;
	}
}
