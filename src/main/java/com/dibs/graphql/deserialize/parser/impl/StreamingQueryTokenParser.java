package com.dibs.graphql.parser.reader.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.data.parse.QueryToken;
import com.dibs.graphql.data.parse.TokenData;
import com.dibs.graphql.parser.reader.QueryTokenReader;
import com.dibs.graphql.parser.reader.ReaderUtil;
import com.dibs.graphql.parser.reader.TokenUtil;

public class StreamingQueryTokenReader implements QueryTokenReader {
	private static final Log LOG = LogFactory.getLog(StreamingQueryTokenReader.class);
	
	private Reader reader;
	
	public StreamingQueryTokenReader(InputStream inputStream) {
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
			key = ReaderUtil.readUntilToken(reader);
			ReaderUtil.assertContains(TokenUtil.FILTER_KEY_TERMINATORS, key.getType());
			
			value = ReaderUtil.readUntilToken(reader);
			ReaderUtil.assertContains(TokenUtil.FILTER_VALUE_TERMINATORS, value.getType());
			
			// Format the input to remove any leading/trailing whitespace
			String filterKey = ReaderUtil.nullIfEmpty(new String(key.getValue()));
			String filterValue = ReaderUtil.nullIfEmpty(new String(value.getValue()));
			
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

		TokenData token = ReaderUtil.readUntilToken(reader);
		
		ReaderUtil.assertContains(TokenUtil.TOKEN_VALUE_TERMINATORS, token.getType());

		// Token value (property name) is going to be the first value that comes back if it exists
		String graphQLTokenValue = ReaderUtil.nullIfEmpty(new String(token.getValue()));
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("Parsing collection named [" + graphQLTokenValue + "]");
		}
		// Loop to see if there are additional tokens, such as attributes or filters
		while (!TokenUtil.isTokenTerminator(token)) {
			if (TokenUtil.isAttributeStart(token)) {
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
}
