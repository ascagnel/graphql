package com.dibs.graphql.parser;

import java.io.Closeable;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.parser.data.GraphQLToken;
import com.dibs.graphql.parser.data.GraphQLTokenType;

public class GraphQLTokenInputStreamReader implements Closeable {
	private static final Log LOG = LogFactory.getLog(GraphQLTokenInputStreamReader.class);
	
	private static final int TOKEN_VALUE_GROUP = 1;
	private static final int OBJECT_ARG_GROUP = 3;
	private static final int ATTRIBUTE_GROUP = 2;
	
	private static final int ATTRIBUTE_KEY_GROUP = 1;
	private static final int ATTRIBUTE_VALUE_GROUP = 2;
	
	/**
	 * Matches:
	 * 		{
	 * 		field {
	 * 		field, 
	 * 		field (attributes) {
	 * 		}
	 * 
	 * ([a-zA-Z0-9]+)?\\s* -> value + whitespace (optional)
	 * (\\(.*\\))?\\s* -> attributes + whitespace (optional)
	 * (?=[\\{\\}\\,]) -> match must have one of [{ } ,]
	 * ([\\{|\\}]?) -> object start/end [{ }]
	 * ([\\,])? -> delimiter [,]
	 */
	private Pattern NEXT_TOKEN_PATTERN = Pattern.compile("([a-zA-Z0-9]+)?\\s*(\\(.*\\))?\\s*(?=[\\{\\}\\,])([\\{|\\}]?)([\\,])?");
	
	/**
	 * Matches:
	 * 		(key : value)
	 */
	private Pattern ATTRIBUTE_KEY_VALUE_PATTERN = Pattern.compile("\\s*\\(([a-zA-Z0-9_]+)\\s*\\:\\s*([a-zA-Z0-9_]+)\\)");
	
	private Scanner scanner;
	
	public GraphQLTokenInputStreamReader(InputStream inputStream) {
		init(inputStream);
	}
	
	private void init(InputStream inputStream) {
		scanner = new Scanner(inputStream);
	}
	
	public boolean hasNext() {
		return scanner.hasNext();
	}
	
	public GraphQLToken nextToken() {
		String fullTokenMatch = scanner.findWithinHorizon(NEXT_TOKEN_PATTERN, 0);
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("Parsing token [" + fullTokenMatch + "]");
		}

		String tokenValue = nullIfEmpty(scanner.match().group(TOKEN_VALUE_GROUP));
		String tokenType = nullIfEmpty(scanner.match().group(OBJECT_ARG_GROUP));
		String attributes = nullIfEmpty(scanner.match().group(ATTRIBUTE_GROUP));
		
		GraphQLToken token = new GraphQLToken();
		token.setValue(tokenValue);
		token.setFullTokenValue(fullTokenMatch);
		
		if (attributes != null) {
			token.setAttributes(parseAttributes(attributes));
		}
		
		if (tokenType != null) {
			token.setTokenType(GraphQLTokenType.fromValue(null));
		}
		
		return token;
	}
	
	public void close() {
		scanner.close();
	}
	
	private String nullIfEmpty(String input) {
		if (input == null || input.isEmpty()) {
			return null;
		}
		
		return input;
	}
	
	private Map<String, String> parseAttributes(String attributes) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Parsing attributes [" + attributes + "]");
		}
		
		Map<String, String> attributeMap = new HashMap<>();

		Matcher attributeMatcher = ATTRIBUTE_KEY_VALUE_PATTERN.matcher(attributes);
		
		while (attributeMatcher.find()) {
			String attributeKey = attributeMatcher.group(ATTRIBUTE_KEY_GROUP);
			String attributeValue = attributeMatcher.group(ATTRIBUTE_VALUE_GROUP);
		
			if (LOG.isDebugEnabled()) {
				LOG.debug("Found attribute key [" + attributeKey + "] value [" + attributeValue + "]");
			}
			
			attributeMap.put(attributeKey, attributeValue);
		}
		
		return attributeMap;
	}
}
