package com.dibs.graphql.deserialize.parser;

import java.io.IOException;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.data.deserialize.TokenData;
import com.dibs.graphql.data.deserialize.Punctuator;

public class StreamUtil {
	private static final Log LOG = LogFactory.getLog(StreamUtil.class);
	
	private static final int DEFAULT_BUFFER_SIZE = 256;
	
	private static int bufferSize = DEFAULT_BUFFER_SIZE;
	
	public static void setBufferSize(int bufferSize) {
		StreamUtil.bufferSize = bufferSize;
	}

	public static TokenData readUntilPunctuator(Reader reader) throws IOException {
		int nextInt;
		
		char[] buffer = new char[bufferSize];
		int index = 0;
		
		Punctuator tokenType = null;
		
		while ((nextInt = reader.read()) != -1) {
			char nextChar = (char) nextInt;
			
			tokenType = Punctuator.fromValue(nextChar);
			
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
		char[] retVal = null;
		
		// If the index is greater than the size, double size and copy contents
		if (index >= buffer.length - 1) {
			retVal = new char[buffer.length * 2];
			
			for (int i = 0; i < buffer.length; i++) {
				retVal[i] = buffer[i];
			}			
		} else {
			retVal = buffer;
		}

		retVal[index] = charToAppend;
		
		return retVal;
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
