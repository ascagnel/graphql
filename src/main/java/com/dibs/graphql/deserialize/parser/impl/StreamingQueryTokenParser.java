package com.dibs.graphql.deserialize.parser.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.data.deserialize.QueryToken;
import com.dibs.graphql.data.deserialize.TokenData;
import com.dibs.graphql.deserialize.parser.QueryTokenParser;
import com.dibs.graphql.deserialize.parser.StreamUtil;
import com.dibs.graphql.deserialize.parser.TokenUtil;
import com.dibs.graphql.util.Util;

public class StreamingQueryTokenParser implements QueryTokenParser {
	private static final Log LOG = LogFactory.getLog(StreamingQueryTokenParser.class);
	
	private Reader reader;
	
	public StreamingQueryTokenParser(InputStream inputStream) {
		init(inputStream);
	}
	
	private void init(InputStream inputStream) {
		reader = new InputStreamReader(inputStream);
	}
	
	public boolean hasNext() {
		try {
			return reader.ready();
		} catch (IOException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}
	
	private Map<String, String> parseAttributes(Reader reader) throws IOException {
		Map<String, String> attributes = new HashMap<>();
		
		TokenData key = null;
		TokenData value = null;
		
		for (boolean reachedAttributeTerminator = false; !reachedAttributeTerminator; reachedAttributeTerminator = TokenUtil.isAttributeTerminator(value)) {
			key = StreamUtil.readUntilToken(reader);
			Util.assertContains(TokenUtil.FILTER_KEY_TERMINATORS, key.getType());
			
			value = StreamUtil.readUntilToken(reader);
			Util.assertContains(TokenUtil.FILTER_VALUE_TERMINATORS, value.getType());
			
			// Format the input to remove any leading/trailing whitespace
			String filterKey = StreamUtil.nullIfEmpty(new String(key.getValue()));
			String filterValue = StreamUtil.nullIfEmpty(new String(value.getValue()));
			
			// Save the filter
			attributes.put(filterKey, filterValue);
		}
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("Parsed filters [ " + attributes + "]");
		}
		
		return attributes;
	}
	
	public QueryToken next() throws IOException {
		QueryToken graphQltoken = new QueryToken();

		TokenData token = StreamUtil.readUntilToken(reader);
		
		Util.assertContains(TokenUtil.TOKEN_VALUE_TERMINATORS, token.getType());

		// Token value (property name) is going to be the first value that comes back if it exists
		String graphQLTokenValue = StreamUtil.nullIfEmpty(new String(token.getValue()));
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("Parsing collection named [" + graphQLTokenValue + "]");
		}
		// Loop to see if there are additional tokens, such as attributes or filters
		while (!TokenUtil.isTokenTerminator(token)) {
			if (TokenUtil.isAttributeStart(token)) {
				graphQltoken.setAttributes(parseAttributes(reader));
			}
			
			token = StreamUtil.readUntilToken(reader);
		}
		
		graphQltoken.setValue(graphQLTokenValue);
		graphQltoken.setTokenType(token.getType());
		
		return graphQltoken;
	}
	
	public void close() throws IOException {
		reader.close();
	}
}
