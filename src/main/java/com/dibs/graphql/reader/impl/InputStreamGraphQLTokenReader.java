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

		char[] buffer = new char[1024];

		int nextCharInt;
		
		String attributeKey = null;
		int bufferIndex = 0;
		
		while((nextCharInt = reader.read()) != -1) {
			char nextChar = (char) nextCharInt;
			
			GraphQLTokenType tokenType = GraphQLTokenType.fromValue(nextChar);
			
			if (tokenType != null) {
				if (isAttributeKeyTerminator(tokenType)) {
					attributeKey = nullIfEmpty(new String(buffer));
					buffer = new char[1024];
				} else if (isAttributeValueTerminator(tokenType)) {
					String attributeValue = nullIfEmpty(new String(buffer));
					attributes.put(attributeKey, attributeValue);
				}			
				
				if (tokenType == GraphQLTokenType.ATTRIBUTE_END) {
					break;
				}
			} else {
				buffer[bufferIndex] = nextChar;
				bufferIndex++;
			}
		}
		
		return attributes;
	}
	
	private boolean isAttributeKeyTerminator(GraphQLTokenType tokenType) {
		return tokenType == GraphQLTokenType.ATTRIBUTE_DELIM;
	}
	
	private boolean isAttributeValueTerminator(GraphQLTokenType tokenType) {
		return tokenType == GraphQLTokenType.OBJECT_DELIM 
						|| tokenType ==  GraphQLTokenType.ATTRIBUTE_END;
	}
	private boolean isTokenValueTerminator(GraphQLTokenType tokenType) {
		return tokenType == GraphQLTokenType.ATTRIBUTE_START 
						|| tokenType == GraphQLTokenType.OBJECT_DELIM 
						|| tokenType == GraphQLTokenType.OBJECT_END;
	}
	
	public GraphQLToken next() throws IOException {
		char[] valueBuffer = new char[1024];

		int bufferIndex = 0;
		int intChar;
		
		GraphQLToken token = new GraphQLToken();
		
		while((intChar = reader.read()) != -1) {
			char curChar = (char) intChar;
			
			GraphQLTokenType tokenType = GraphQLTokenType.fromValue(curChar);
			
			if (tokenType != null) {
				token.setTokenType(tokenType);
				
				if (isTokenValueTerminator(tokenType)) {
					token.setValue(nullIfEmpty(new String(valueBuffer)));
				}
				
				if (tokenType == GraphQLTokenType.ATTRIBUTE_START) {
					token.setAttributes(parseAttributes(reader));
				} else {
					break;
				}
			} else {
				valueBuffer[bufferIndex] = curChar;
				bufferIndex++;
			}
		}
		
		return token;
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
