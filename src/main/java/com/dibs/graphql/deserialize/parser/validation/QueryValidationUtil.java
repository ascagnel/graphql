package com.dibs.graphql.deserialize.parser.validation;

import java.util.Set;

import com.dibs.graphql.deserialize.DeserializationException;
import com.dibs.graphql.deserialize.DeserializationExceptionPayload;
import com.dibs.graphql.deserialize.DeserializationExceptionPayloadFactory;
import com.dibs.graphql.deserialize.InputMessageLocationDataProvider;

public class QueryValidationUtil {

	public static <D> void validatePunctuator(Set<D> expected, D actual, InputMessageLocationDataProvider provider) throws DeserializationException {
		if (!expected.contains(actual)) {
			DeserializationExceptionPayload payload = DeserializationExceptionPayloadFactory.fromInputMessageLocationDataProvider("Invalid punctuator [" + actual + "]; Expected one of: " + expected, provider);
			throw new DeserializationException(payload);
		}
	}
}
