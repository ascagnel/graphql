package com.dibs.graphql.deserialize.parser.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dibs.graphql.data.request.Arguments;
import com.dibs.graphql.deserialize.TypeResolver;
import com.dibs.graphql.deserialize.data.TokenData;
import com.dibs.graphql.deserialize.parser.StreamReader;
import com.dibs.graphql.deserialize.parser.TokenParser;
import com.dibs.graphql.deserialize.parser.TokenUtil;
import com.dibs.graphql.util.Util;

public class ArgumentParser implements TokenParser<Arguments>{

	private static final Log LOG = LogFactory.getLog(ArgumentParser.class);

	private StreamReader streamReader;
	
	public ArgumentParser() {
	}
	
	public ArgumentParser(StreamReader streamReader) {
		this.streamReader = streamReader;
	}

	public Arguments next() throws IOException {
		if (streamReader == null) {
			throw new RuntimeException("A StreamReader must be set to parse arguments");
		}
		
		Arguments arguments = new Arguments();
		
		TokenData argumentKeyData = null;
		TokenData argumentValueData = null;
		
		for (boolean reachedArgumentTerminator = false; !reachedArgumentTerminator; reachedArgumentTerminator = TokenUtil.isAttributeTerminator(argumentValueData)) {
			argumentKeyData = streamReader.readUntilPunctuator();
			Util.assertContains(TokenUtil.ARGUMENT_KEY_TERMINATORS, argumentKeyData.getType());
			
			Object typedArgumentValue = null;
			
			argumentValueData = streamReader.readUntilPunctuator();
			if (TokenUtil.isArrayStart(argumentValueData)) {
				List<TokenData> arrayElements = new ArrayList<>();
				
				argumentValueData = streamReader.readUntilPunctuator();
				
				for (boolean isArrayEnd = false; !isArrayEnd; isArrayEnd = TokenUtil.isArrayTerminator(argumentValueData), argumentValueData = streamReader.readUntilPunctuator()) {					
					arrayElements.add(argumentValueData);
				}
				
				typedArgumentValue = TypeResolver.arrayRawDataToTypedValue(arrayElements.toArray(new TokenData[0]));
			} else {
				typedArgumentValue = TypeResolver.rawDataToTypedValue(argumentValueData);
			}
			
			Util.assertContains(TokenUtil.ARGUMENT_VALUE_TERMINATORS, argumentValueData.getType());
			
			// Format the input to remove any leading/trailing whitespace
			String argumentKey = StreamReader.nullIfEmpty(new String(argumentKeyData.getValue()));
						
			// Save the filter
			arguments.put(argumentKey, typedArgumentValue);
		}
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("Parsed arguments [ " + arguments + "]");
		}
		
		return arguments;
	}
}
