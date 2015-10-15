package com.dibs.graphql.deserialize.parser.validation;

import java.util.Set;

import com.dibs.graphql.deserialize.DeserializationException;
import com.dibs.graphql.deserialize.DeserializationExceptionPayload;
import com.dibs.graphql.deserialize.DeserializationExceptionPayloadFactory;
import com.dibs.graphql.deserialize.InputMessageLocationDataProvider;
import com.dibs.graphql.deserialize.data.Punctuator;

public class QueryValidationUtil {

	public static <D> void validatePunctuator(Set<Punctuator> expected, Punctuator actual, InputMessageLocationDataProvider provider) throws DeserializationException {
		if (!expected.contains(actual)) {
			StringBuffer messageBuffer = new StringBuffer();
			messageBuffer.append("Invalid punctuator '");
			messageBuffer.append(actual.getValue());
			messageBuffer.append("'; Expected one of: ");
			messageBuffer.append("[");
			
			if (!expected.isEmpty()) {
				for (Punctuator punctuator : expected) {
					messageBuffer.append("'");
					messageBuffer.append(punctuator.getValue());
					messageBuffer.append("', ");
				}
				messageBuffer.setLength(messageBuffer.length() - 2);
			}
			
			DeserializationExceptionPayload payload = DeserializationExceptionPayloadFactory.fromInputMessageLocationDataProvider(messageBuffer.toString(), provider);
			
			throw new DeserializationException(payload);
		}
	}
}
