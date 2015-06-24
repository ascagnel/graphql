package com.dibs.graphql.deserialize;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import com.dibs.graphql.data.Query;
import com.dibs.graphql.data.QueryTestUtil;

public class DeserializationTest {

	
	@Test
	public void test() throws Exception {
		QueryDeserializer parser = new QueryDeserializer();
		Query rootNode = parser.parse(new ByteArrayInputStream(QueryTestUtil.getUserString().getBytes()));
		
		assertNotNull(rootNode);
		assertEquals(1, rootNode.getSubQueries().size());
		assertEquals("user", rootNode.getSubQueries().get(0).getName());
		assertEquals(QueryTestUtil.buildUser(), rootNode);
	}
	
}
