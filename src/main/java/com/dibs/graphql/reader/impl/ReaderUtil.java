package com.dibs.graphql.reader.impl;

import java.io.IOException;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.parser.data.GraphQLTokenType;

public class ReaderUtil {
	private static final Log LOG = LogFactory.getLog(ReaderUtil.class);
	
	public static class Token {
		private char[] value;
		private GraphQLTokenType type;
		
		public Token() {
		}
		
		public Token(char[] value, GraphQLTokenType type) {
			this.value = value;
			this.type = type;
		}

		public char[] getValue() {
			return value;
		}

		public void setValue(char[] value) {
			this.value = value;
		}

		public GraphQLTokenType getType() {
			return type;
		}

		public void setType(GraphQLTokenType type) {
			this.type = type;
		}
	}
	
	public static Token readUntilToken(Reader reader) throws IOException {
		int nextInt;
		
		char[] buffer = new char[256];
		int index = 0;
		
		GraphQLTokenType tokenType = null;
		
		while ((nextInt = reader.read()) != -1) {
			char nextChar = (char) nextInt;
			
			tokenType = GraphQLTokenType.fromValue(nextChar);
			
			// We have a token
			if (tokenType != null) {
				break;
			}
			
			buffer = insertAndResize(buffer, index, nextChar);
			index++;
		}
		
		Token token = new Token(buffer, tokenType);

		if (LOG.isDebugEnabled()) {
			LOG.debug("Returning read values buffer: [" + new String(buffer) + "] tokenType [" + tokenType + "]");
		}
		
		return token;
	}
	
	public static char[] insertAndResize(char[] buffer, int index, char charToAppend) {
		if (index >= buffer.length - 1) {
			buffer = new char[buffer.length * 2];
		}
		
		buffer[index] = charToAppend;
		return buffer;
	}
}
