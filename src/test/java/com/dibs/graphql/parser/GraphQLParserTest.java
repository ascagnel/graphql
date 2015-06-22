package com.dibs.graphql.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import com.dibs.graphql.parser.data.GraphQLNode;

public class GraphQLParserTest {
	
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

	private GraphQLNode userNode = 
			new GraphQLNode()
				.child(
					new GraphQLNode()
						.value("user")
						.attribute("id", "123")
						.child(new GraphQLNode().value("id"))
						.child(new GraphQLNode().value("name"))
						.child(new GraphQLNode().value("isViewerFriend"))
						.child(
							new GraphQLNode()
								.value("profilePicture")
								.attribute("width", "50px")
								.child(new GraphQLNode().value("uri"))
								.child(new GraphQLNode().value("width"))
								.child(new GraphQLNode().value("height")))
						.child(
							new GraphQLNode()
								.value("test")
								.child(new GraphQLNode().value("123"))
						)
				);
						
	
	@Test
	public void test() throws Exception {
		GraphQLParser parser = new GraphQLParser();
		GraphQLNode rootNode = parser.parse(new ByteArrayInputStream(userString.getBytes()));
		
		assertNotNull(rootNode);
		assertEquals(1, rootNode.getChildren().size());
		assertEquals("user", rootNode.getChildren().get(0).getValue());
		assertEquals(userNode, rootNode);
	}
}
