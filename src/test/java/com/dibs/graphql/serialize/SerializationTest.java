package com.dibs.graphql.serialize;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.dibs.graphql.data.Query;
import com.dibs.graphql.data.QueryTestUtil;
import com.dibs.graphql.serialize.impl.QuerySerializerImpl;

public class SerializationTest {

	private static final String NON_PRETTY_SERIALIZED_USER = "{user(id:123){identifier:id,name(style:FULL),isViewerFriend,profilePicture(height:100px,width:50px){uri,width,height},test(field:[abc, def, fg, 456]){123}}}";
	
	private static final String PRETTY_SERIALIZED_USER = 
					"{\n" + 
					"	user (id:123) {\n" + 
					"		identifier: id,\n" + 
					"		name (style:FULL),\n" + 
					"		isViewerFriend,\n" + 
					"		profilePicture (height:100px, width:50px) {\n" + 
					"			uri,\n" + 
					"			width,\n" + 
					"			height\n" + 
					"		},\n" + 
					"		test (field:[abc, def, fg, 456]) {\n" + 
					"			123\n" + 
					"		}\n" + 
					"	}\n" + 
					"}\n";
						
	@Test
	public void testSerialize() throws IOException {
		Query userQuery = QueryTestUtil.buildUser();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		QuerySerializerImpl serializer = new QuerySerializerImpl();
		serializer.setPrettyPrint(false);
		serializer.serialize(outputStream, userQuery);
		
		String nonPrettyOutputString = outputStream.toString();
		assertEquals(NON_PRETTY_SERIALIZED_USER, nonPrettyOutputString);
	}
	
	@Test
	public void testSerializePrettyPrint() throws IOException {
		Query userQuery = QueryTestUtil.buildUser();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		QuerySerializerImpl serializer = new QuerySerializerImpl();
		serializer.setPrettyPrint(true);
		serializer.serialize(outputStream, userQuery);
		
		String prettyOutputString = outputStream.toString();
		assertEquals(PRETTY_SERIALIZED_USER, prettyOutputString);	
	}
}
