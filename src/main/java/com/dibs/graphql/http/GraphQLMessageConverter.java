package com.dibs.graphql.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.dibs.graphql.data.GraphQLData;
import com.dibs.graphql.data.request.Query;
import com.dibs.graphql.deserialize.Deserializer;
import com.dibs.graphql.serialize.Serializer;

public class GraphQLMessageConverter extends AbstractHttpMessageConverter<GraphQLData> {
	
	public static final MediaType APPLICATION_GRAPHQL = new MediaType("application/graphql");
	
	private Map<Class<?>, Serializer<? extends GraphQLData>> serializers;
	private Map<Class<?>, Deserializer<? extends GraphQLData>> deserializers;

	public void setSerializers(Map<Class<?>, Serializer<? extends GraphQLData>> serializers) {
		this.serializers = serializers;
	}

	public void setDeserializers(Map<Class<?>, Deserializer<? extends GraphQLData>> deserializers) {
		this.deserializers = deserializers;
	}

	@Override
	protected boolean canRead(org.springframework.http.MediaType arg0) {
		return APPLICATION_GRAPHQL.equals(arg0);
	};
	
	protected Deserializer<? extends GraphQLData> determineDeserializer(Class<? extends GraphQLData> clazz) {
		return deserializers.get(clazz);
	}
	
	protected Serializer<? extends GraphQLData> determineSerializer(Class<? extends GraphQLData> clazz) {
		return serializers.get(clazz);
	}
	
	@Override
	protected GraphQLData readInternal(Class<? extends GraphQLData> arg0, HttpInputMessage arg1) throws IOException, HttpMessageNotReadableException {
		Deserializer<? extends GraphQLData> deserializer = determineDeserializer(arg0);
		
		if (deserializer == null) {
			throw new RuntimeException("Could not determine deserializer for class [" + arg0 + "]");
		}
		
		InputStream body = arg1.getBody();
		
		return deserializer.deserialize(body);	
	}

	@Override
	protected boolean supports(Class<?> arg0) {
		return Query.class.isAssignableFrom(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void writeInternal(GraphQLData arg0, HttpOutputMessage arg1) throws IOException, HttpMessageNotWritableException {	
		Serializer<GraphQLData> serializer = (Serializer<GraphQLData>) determineSerializer(arg0.getClass());
		
		if (serializer == null) {
			throw new RuntimeException("Could not determine serializer for class [" + arg0 + "]");
		}
		
		serializer.serialize(arg1.getBody(), arg0);
	}
}
