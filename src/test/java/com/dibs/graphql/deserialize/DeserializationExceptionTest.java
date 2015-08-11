package com.dibs.graphql.deserialize;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import com.dibs.graphql.data.request.Query;
import com.dibs.graphql.deserialize.data.Punctuator;
import com.dibs.graphql.deserialize.impl.QueryDeserializerImpl;

public class DeserializationExceptionTest {

	private static final String invalidQueryTerminator = 
			"{\n" + 
			"	user {\n" + 
			"		abc,\n" + 
			"		def\n" + 
			"	)\n" + 
			"}";
	
	@Test(expected=DeserializationException.class)
	public void test() throws IOException, DeserializationException {
		Deserializer<Query> parser = new QueryDeserializerImpl();
		try {
			parser.deserialize(new ByteArrayInputStream(invalidQueryTerminator.getBytes()));
		} catch (DeserializationException e) {
			DeserializationExceptionPayload payload = e.getPayload();
			assertEquals(new Integer(5), payload.getLineNumber());
			assertEquals(new Integer(2), payload.getColumnNumber());
			assertEquals("def", new String(payload.getLastToken().getValue()).trim());
			assertEquals(Punctuator.CLOSE_PAREN, payload.getLastToken().getType());
			assertEquals(null, payload.getPath().get(0).getName());
			assertEquals("user", payload.getPath().get(1).getName());
			throw e;
		}
	}
}
