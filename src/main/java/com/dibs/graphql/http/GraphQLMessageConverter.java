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
import com.dibs.graphql.deserialize.ParseException;
import com.dibs.graphql.deserialize.QueryDeserializer;
import com.dibs.graphql.serialize.QuerySerializer;

public class GraphQLMessageConverter extends AbstractHttpMessageConverter<Query> {
	
	public static final MediaType APPLICATION_GRAPHQL = new MediaType("application/graphql");
	
	@Override
	protected boolean canRead(org.springframework.http.MediaType arg0) {
		return APPLICATION_GRAPHQL.equals(arg0);
	};
	
	@Override
	protected Query readInternal(Class<? extends Query> arg0, HttpInputMessage arg1) throws IOException, HttpMessageNotReadableException {
		InputStream body = arg1.getBody();
		QueryDeserializer parser = new QueryDeserializer();
		
		try {
			return parser.parse(body);		
		} catch (ParseException e) {
			throw new HttpMessageNotReadableException(e.getMessage(), e);
		}
	}

	@Override
	protected boolean supports(Class<?> arg0) {
		return Query.class.isAssignableFrom(arg0);
	}

	@Override
	protected void writeInternal(Query arg0, HttpOutputMessage arg1) throws IOException, HttpMessageNotWritableException {	
		QuerySerializer querySerializer = new QuerySerializer();
		querySerializer.serialize(arg1.getBody(), arg0, false);
	}
}
