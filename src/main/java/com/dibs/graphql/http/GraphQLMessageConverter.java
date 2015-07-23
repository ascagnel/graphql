package com.dibs.graphql.http;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.dibs.graphql.data.Query;
import com.dibs.graphql.deserialize.QueryDeserializer;
import com.dibs.graphql.deserialize.SerializationException;
import com.dibs.graphql.deserialize.impl.QueryDeserializerStackImpl;
import com.dibs.graphql.serialize.QuerySerializer;
import com.dibs.graphql.serialize.impl.QuerySerializerImpl;

public class GraphQLMessageConverter extends AbstractHttpMessageConverter<Query> {
	
	public static final MediaType APPLICATION_GRAPHQL = new MediaType("application/graphql");
	
	private static final QuerySerializer DEFAULT_SERIALIZER = new QuerySerializerImpl();
	
	private QuerySerializer serializer = DEFAULT_SERIALIZER;

	public void setSerializer(QuerySerializer serializer) {
		this.serializer = serializer;
	}

	@Override
	protected boolean canRead(org.springframework.http.MediaType arg0) {
		return APPLICATION_GRAPHQL.equals(arg0);
	};
	
	@Override
	protected Query readInternal(Class<? extends Query> arg0, HttpInputMessage arg1) throws IOException, HttpMessageNotReadableException {
		InputStream body = arg1.getBody();
		
		QueryDeserializer deserializer = new QueryDeserializerStackImpl(body);
		
		try {
			return deserializer.deserialize();		
		} catch (SerializationException e) {
			throw new HttpMessageNotReadableException(e.getMessage(), e);
		}
	}

	@Override
	protected boolean supports(Class<?> arg0) {
		return Query.class.isAssignableFrom(arg0);
	}

	@Override
	protected void writeInternal(Query arg0, HttpOutputMessage arg1) throws IOException, HttpMessageNotWritableException {	
		try {
			serializer.serialize(arg1.getBody(), arg0, false);
		} catch (SerializationException e) {
			throw new HttpMessageNotReadableException(e.getMessage(), e);
		}
	}
}
