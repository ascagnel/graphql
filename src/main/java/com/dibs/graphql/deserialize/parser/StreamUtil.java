package com.dibs.graphql.parser.reader;

import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.data.parse.TokenData;
import com.dibs.graphql.data.parse.TokenType;

public class ReaderUtil {
	private static final Log LOG = LogFactory.getLog(ReaderUtil.class);
	
	private static final int DEFAULT_BUFFER_SIZE = 256;
	
	private static int bufferSize = DEFAULT_BUFFER_SIZE;
	
	public static void setBufferSize(int bufferSize) {
		ReaderUtil.bufferSize = bufferSize;
	}

	public static TokenData readUntilToken(Reader reader) throws IOException {
		int nextInt;
		
		char[] buffer = new char[bufferSize];
		int index = 0;
		
		TokenType tokenType = null;
		
		while ((nextInt = reader.read()) != -1) {
			char nextChar = (char) nextInt;
			
			tokenType = TokenType.fromValue(nextChar);
			
			// We have a token
			if (tokenType != null) {
				break;
			}
			
			buffer = insertAndResize(buffer, index, nextChar);
			index++;
		}
		
		TokenData token = new TokenData(buffer, tokenType);

		if (LOG.isDebugEnabled()) {
			LOG.trace("Returning read values buffer: [" + new String(buffer) + "] tokenType [" + tokenType + "]");
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
	
	@SuppressWarnings("unchecked")
	public static <T> Set<T> newHashSet(T... objs) {
	    Set<T> set = new HashSet<>();
	    
	    for (T obj : objs) {
	        set.add(obj);
	    }
	    
	    return set;
	}
	
	public static <D> void assertContains(Set<D> expected, D actual) {
		if (!expected.contains(actual)) {
			throw new RuntimeException("Invalid value [" + actual +"]. Expected one of: " + expected);
		}
	}

	public static String nullIfEmpty(String input) {
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
