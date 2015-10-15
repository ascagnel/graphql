package com.dibs.graphql.deserialize;

import java.util.List;
import java.util.Stack;

import com.dibs.graphql.data.request.Query;
import com.dibs.graphql.deserialize.data.TokenData;

public class DeserializationExceptionPayload {

	private String message;
	private Integer lineNumber;
	private Integer columnNumber;
	private TokenData lastToken;
	private List<String> tokenPath;
	private Stack<Query> path;
	
	public DeserializationExceptionPayload() {
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}
	
	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public Integer getColumnNumber() {
		return columnNumber;
	}
	
	public void setColumnNumber(Integer columnNumber) {
		this.columnNumber = columnNumber;
	}

	public TokenData getLastToken() {
		return lastToken;
	}

	public void setLastToken(TokenData lastToken) {
		this.lastToken = lastToken;
	}

	public List<String> getTokenPath() {
		return tokenPath;
	}

	public void setTokenPath(List<String> tokenPath) {
		this.tokenPath = tokenPath;
	}

	public Stack<Query> getPath() {
		return path;
	}

	public void setPath(Stack<Query> path) {
		this.path = path;
	}
	
	public String buildMessage() {
		StringBuffer buffer = new StringBuffer();
		
		// Error Message
		buffer.append("Error Message: [");
		buffer.append(message);
		buffer.append("], ");
		
		// Line Number
		buffer.append("Line Number: [");
		buffer.append(lineNumber);
		buffer.append("], ");
		
		// Column Number
		buffer.append("Column Number: [");
		buffer.append(columnNumber);
		buffer.append("], ");
		
		// Last Token
		buffer.append("Last Token: [");
		if (lastToken != null) {
			if (lastToken.getValue() != null) {
				buffer.append(new String(lastToken.getValue()).trim());
			}
			
			if (lastToken.getType() != null) {
				buffer.append(lastToken.getType().getValue());
			}
		}
		buffer.append("], ");
		
		// Path
		buffer.append("Path: [");
		if (path != null) {			
			if (!path.isEmpty()) {
				for (Query query: path) {
					buffer.append(query.getName());
					buffer.append(".");
				}
				
				buffer.setLength(buffer.length() - 1);
			}			
		}
		buffer.append("]");
		
		return buffer.toString();
	}
}
