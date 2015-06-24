package com.dibs.graphql.serialize;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.dibs.graphql.data.Query;
import com.dibs.graphql.data.QueryTestUtil;
import com.dibs.graphql.serialize.QuerySerializer;

public class SerializationTest {

	private static final String NON_PRETTY_SERIALIZED_USER = "{user(id:123){id,name,isViewerFriend,profilePicture(width:50px){uri,width,height},test{123}}}";
	
	private static final String PRETTY_SERIALIZED_USER = 
					"{\n" + 
					"	user (id:123) {\n" + 
					"		id,\n" + 
					"		name,\n" + 
					"		isViewerFriend,\n" + 
					"		profilePicture (width:50px) {\n" + 
					"			uri,\n" + 
					"			width,\n" + 
					"			height\n" + 
					"		},\n" + 
					"		test {\n" + 
					"			123\n" + 
					"		}\n" + 
					"	}\n" + 
					"}\n";
						
	@Test
	public void testSerialize() throws IOException {
		Query userQuery = QueryTestUtil.buildUser();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		QuerySerializer serializer = new QuerySerializer();
		serializer.serialize(outputStream, userQuery, false);
		
		String nonPrettyOutputString = outputStream.toString();
		assertEquals(NON_PRETTY_SERIALIZED_USER, nonPrettyOutputString);
		
		outputStream = new ByteArrayOutputStream();

		serializer.serialize(outputStream, userQuery, true);
		
		String prettyOutputString = outputStream.toString();
		assertEquals(PRETTY_SERIALIZED_USER, prettyOutputString);	
	}
	
	@Test
	public void testSerializePrettyPrint() throws IOException {
		Query userQuery = QueryTestUtil.buildUser();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		QuerySerializer serializer = new QuerySerializer();
		serializer.serialize(outputStream, userQuery, true);
		
		String prettyOutputString = outputStream.toString();
		assertEquals(PRETTY_SERIALIZED_USER, prettyOutputString);	
	}
}
