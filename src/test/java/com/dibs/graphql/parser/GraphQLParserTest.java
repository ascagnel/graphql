package com.dibs.graphql.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.dibs.graphql.data.Query;
import com.dibs.graphql.data.QueryBuilder;
import com.dibs.graphql.serialize.QuerySerializer;

public class GraphQLParserTest {
	
	private static final String SERIALIZED_USER = "{user(id:123){id,name,isViewerFriend,profilePicture(width:50px){uri,width,height},test{123}}}";
	
	private String userString = "{\n" + 
					"  user (id: 123) {\n" + 
					"    id,\n" + 
					"    name,\n" + 
					"    isViewerFriend,\n" + 
					"    profilePicture (width:50px) {\n" + 
					"      uri,\n" + 
					"      width,\n" + 
					"      height\n" + 
					"    }, test {\n" + 
					"		123 \n" + 
					"    } " +
					"  }\n" + 
					"}";

	private Query userNode = 
			new QueryBuilder()
				.subQuery(
					new QueryBuilder()
						.name("user")
						.param("id", "123")
						.subQuery(new QueryBuilder().name("id").build())
						.subQuery(new QueryBuilder().name("name").build())
						.subQuery(new QueryBuilder().name("isViewerFriend").build())
						.subQuery(
							new QueryBuilder()
								.name("profilePicture")
								.param("width", "50px")
								.subQuery(new QueryBuilder().name("uri").build())
								.subQuery(new QueryBuilder().name("width").build())
								.subQuery(new QueryBuilder().name("height").build())
							.build())
						.subQuery(
							new QueryBuilder()
								.name("test")
								.subQuery(new QueryBuilder().name("123").build()).build()
						)
					.build()
				).build();
						
	
	@Test
	public void test() throws Exception {
		QueryParser parser = new QueryParser();
		Query rootNode = parser.parse(new ByteArrayInputStream(userString.getBytes()));
		
		assertNotNull(rootNode);
		assertEquals(1, rootNode.getSubQueries().size());
		assertEquals("user", rootNode.getSubQueries().get(0).getName());
		assertEquals(userNode, rootNode);
	}
	
	@Test
	public void testWrite() throws IOException {
		Query rootQuery = userNode;
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		QuerySerializer serializer = new QuerySerializer();
		serializer.serialize(outputStream, rootQuery);
		
		String outputString = outputStream.toString();
		assertEquals(SERIALIZED_USER, outputString);
	}
}
