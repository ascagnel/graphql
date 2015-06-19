package com.dibs.graphql.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import com.dibs.graphql.parser.data.GraphQLNode;

public class GraphQLParserTest {
	
	private String example = "{\n" + 
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

	@Test
	public void test() throws Exception {
		GraphQLParser parser = new GraphQLParser();
		GraphQLNode rootNode = parser.parse(new ByteArrayInputStream(example.getBytes()));
		
		assertNotNull(rootNode);
		assertEquals(1, rootNode.getChildren().size());
		assertEquals("user", rootNode.getChildren().get(0).getValue());

	}
}
