package com.dibs.graphql.deserialize.parser.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.deserialize.data.QueryToken;
import com.dibs.graphql.deserialize.data.TokenData;
import com.dibs.graphql.deserialize.parser.StreamReader;
import com.dibs.graphql.deserialize.parser.TokenParser;
import com.dibs.graphql.deserialize.parser.TokenUtil;
import com.dibs.graphql.util.Util;

public class QueryTokenParser implements TokenParser<QueryToken> {
	private static final Log LOG = LogFactory.getLog(QueryTokenParser.class);
	
	private StreamReader streamReader;
	private ArgumentParser argumentParser;
	
	public QueryTokenParser(ArgumentParser argumentParser, StreamReader streamReader) {
		this.argumentParser = argumentParser;
		this.streamReader = streamReader;
	}
	
	public void setStreamReader(StreamReader streamReader) {
		this.streamReader = streamReader;
	}
	
	public void setArgumentParser(ArgumentParser argumentParser) {
		this.argumentParser = argumentParser;
	}
	
	public QueryToken next() throws IOException {
		QueryToken graphQltoken = new QueryToken();

		TokenData token = streamReader.readUntilPunctuator();
		
		if (TokenUtil.isAlias(token)) {
			graphQltoken.setAlias(StreamReader.nullIfEmpty(new String(token.getValue())));
			token = streamReader.readUntilPunctuator();
		}
		
		Util.assertContains(TokenUtil.TOKEN_VALUE_TERMINATORS, token.getType());

		// Token value (property name) is going to be the first value that comes back if it exists
		String graphQLTokenValue = StreamReader.nullIfEmpty(new String(token.getValue()));
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("Parsing collection named [" + graphQLTokenValue + "]");
		}
		
		// Loop to see if there are additional tokens, such as attributes or filters
		while (!TokenUtil.isTokenTerminator(token)) {
			if (TokenUtil.isArgumentStart(token)) {
				graphQltoken.setArguments(argumentParser.next());
			}
			
			token = streamReader.readUntilPunctuator();
		}
		
		graphQltoken.setValue(graphQLTokenValue);
		graphQltoken.setTokenType(token.getType());
		
		return graphQltoken;
	}
}
