package com.dibs.graphql.deserialize.parser.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

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
	private ArgumentParser argumentParser;
	
	public StreamingQueryTokenParser(InputStream inputStream) {
		init(inputStream);
	}
	
	private void init(InputStream inputStream) {
		reader = new InputStreamReader(inputStream);
		argumentParser = new ArgumentParser();
	}
	
	public boolean hasNext() {
		try {
			return reader.ready();
		} catch (IOException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}
	
	public QueryToken next() throws IOException {
		QueryToken graphQltoken = new QueryToken();

		TokenData token = StreamUtil.readUntilPunctuator(reader);
		
		if (TokenUtil.isAlias(token)) {
			graphQltoken.setAlias(StreamUtil.nullIfEmpty(new String(token.getValue())));
			token = StreamUtil.readUntilPunctuator(reader);
		}
		
		Util.assertContains(TokenUtil.TOKEN_VALUE_TERMINATORS, token.getType());

		// Token value (property name) is going to be the first value that comes back if it exists
		String graphQLTokenValue = StreamUtil.nullIfEmpty(new String(token.getValue()));
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("Parsing collection named [" + graphQLTokenValue + "]");
		}
		// Loop to see if there are additional tokens, such as attributes or filters
		while (!TokenUtil.isTokenTerminator(token)) {
			if (TokenUtil.isArgumentStart(token)) {
				graphQltoken.setArguments(argumentParser.parseArguments(reader));
			}
			
			token = StreamUtil.readUntilPunctuator(reader);
		}
		
		graphQltoken.setValue(graphQLTokenValue);
		graphQltoken.setTokenType(token.getType());
		
		return graphQltoken;
	}
	
	public void close() throws IOException {
		reader.close();
	}
}
