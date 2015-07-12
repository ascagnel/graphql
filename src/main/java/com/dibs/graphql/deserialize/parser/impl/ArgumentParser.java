package com.dibs.graphql.deserialize.parser.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.data.deserialize.TokenData;
import com.dibs.graphql.deserialize.TypeResolver;
import com.dibs.graphql.deserialize.parser.StreamUtil;
import com.dibs.graphql.deserialize.parser.TokenUtil;
import com.dibs.graphql.util.Util;

public class ArgumentParser {

	private static final Log LOG = LogFactory.getLog(ArgumentParser.class);

	public Map<String, Object> parseArguments(Reader reader) throws IOException {
		Map<String, Object> attributes = new HashMap<>();
		
		TokenData argumentKeyData = null;
		TokenData argumentValueData = null;
		
		for (boolean reachedArgumentTerminator = false; !reachedArgumentTerminator; reachedArgumentTerminator = TokenUtil.isAttributeTerminator(argumentValueData)) {
			argumentKeyData = StreamUtil.readUntilPunctuator(reader);
			Util.assertContains(TokenUtil.ARGUMENT_KEY_TERMINATORS, argumentKeyData.getType());
			
			Object typedArgumentValue = null;
			
			argumentValueData = StreamUtil.readUntilPunctuator(reader);
			if (TokenUtil.isArrayStart(argumentValueData)) {
				List<TokenData> arrayElements = new ArrayList<>();
				
				argumentValueData = StreamUtil.readUntilPunctuator(reader);
				
				for (boolean isArrayEnd = false; !isArrayEnd; isArrayEnd = TokenUtil.isArrayTerminator(argumentValueData), argumentValueData = StreamUtil.readUntilPunctuator(reader)) {					
					arrayElements.add(argumentValueData);
				}
				
				typedArgumentValue = TypeResolver.arrayRawDataToTypedValue(arrayElements.toArray(new TokenData[0]));
			} else {
				typedArgumentValue = TypeResolver.rawDataToTypedValue(argumentValueData);
			}
			
			Util.assertContains(TokenUtil.ARGUMENT_VALUE_TERMINATORS, argumentValueData.getType());
			
			// Format the input to remove any leading/trailing whitespace
			String argumentKey = StreamUtil.nullIfEmpty(new String(argumentKeyData.getValue()));
						
			// Save the filter
			attributes.put(argumentKey, typedArgumentValue);
		}
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("Parsed arguments [ " + attributes + "]");
		}
		
		return attributes;
	}
}
