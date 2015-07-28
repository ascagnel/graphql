package com.dibs.graphql.deserialize.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.deserialize.data.Punctuator;
import com.dibs.graphql.deserialize.data.TokenData;

public class StreamReader {
	private static final Log LOG = LogFactory.getLog(StreamReader.class);
	
	private static final int DEFAULT_BUFFER_SIZE = 256;
	
	private int bufferSize = DEFAULT_BUFFER_SIZE;
	
	private Reader reader;
	private int lineNumber;
	private int columnNumber;
	
	public StreamReader(InputStream inputStream) {
		init(inputStream);
	}
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}
	
	private void init(InputStream inputStream) {
		reader = new InputStreamReader(inputStream);
		lineNumber = 1;
		columnNumber = 0;
	}
	
	public TokenData readUntilPunctuator() throws IOException {
		int nextInt;
		
		char[] buffer = new char[bufferSize];
		int index = 0;
		
		Punctuator tokenType = null;
		
		while ((nextInt = reader.read()) != -1) {
			char nextChar = (char) nextInt;
	
			updateStreamLocation(nextChar);
			
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
	
	private boolean isNewline(char charToTest) {
		return charToTest == '\n';
	}
	
	private char[] insertAndResize(char[] buffer, int index, char charToAppend) {
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
	
	private void updateStreamLocation(char curChar) {
		if (isNewline(curChar)) {
			lineNumber++;
			columnNumber = 0;
		} else {
			columnNumber++;
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
	
	public boolean ready() throws IOException {
		return reader.ready();
	}
	
	public void close() throws IOException {
		reader.close();
	}
}
