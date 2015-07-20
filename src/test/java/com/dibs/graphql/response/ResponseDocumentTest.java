package com.dibs.graphql.response;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.json.Json;

import org.junit.Before;
import org.junit.Test;

import com.dibs.graphql.breakroom.data.BreakRoom;
import com.dibs.graphql.breakroom.data.VendingMachine;
import com.dibs.graphql.data.Query;
import com.dibs.graphql.data.QueryBuilder;
import com.dibs.graphql.data.Response;
import com.dibs.graphql.data.ResponseDocumentBuilder;
import com.dibs.graphql.serialize.impl.ResponseSerializerImpl;
import com.google.gson.JsonObject;

public class ResponseDocumentTest {

	private BreakRoom breakRoom;
	
	@Before
	public void setUp() {
		breakRoom = new BreakRoom();
		breakRoom.setId(123);
		breakRoom.setName("A beautiful break room");
		
		
		VendingMachine vendingMachine = new VendingMachine();
		vendingMachine.setId(456);
		vendingMachine.setMerchandiseCount(4);
		vendingMachine.setServicedBy("Matt");
		
		List<VendingMachine> vendingMachines = new ArrayList<>();
		vendingMachines.add(vendingMachine);
		
		breakRoom.setVendingMachines(vendingMachines);
	}
	
	private Query buildFullyInflatedBreakRoom() {
		Query breakRoom = 
			new QueryBuilder()
						.subQuery(new QueryBuilder().name("id").build())
						.subQuery(new QueryBuilder().name("name").build())
						.subQuery(new QueryBuilder()
							.name("vendingMachines")
								.subQuery(new QueryBuilder().name("id").build())
								.subQuery(new QueryBuilder().name("merchandiseCount").build())
								.subQuery(new QueryBuilder().name("servicedBy").build())
								.subQuery(new QueryBuilder()
									.name("products")
										.subQuery(new QueryBuilder().name("id").build())
										.subQuery(new QueryBuilder().name("name").build())
										.subQuery(new QueryBuilder().name("description").build())
										.subQuery(new QueryBuilder().name("calories").build())
									.build())
							.build())
					.build();
		
		return breakRoom;
	}
	
	@Test
	public void test() throws IOException {
		ResponseDocumentBuilder document = new ResponseDocumentBuilder(buildFullyInflatedBreakRoom());
		document.addFromBean(breakRoom);
		
		Map<String, Object> data = document.getData();
		
		assertNotNull(data.get("id"));
		assertNotNull(data.get("name"));
		assertNotNull(data.get("vendingMachines"));
		assertNotNull(((Map)((List)data.get("vendingMachines")).get(0)).get("id"));
		
		Response response = new Response();
		response.setData(data);
		ResponseSerializerImpl serializer = new ResponseSerializerImpl();
		serializer.serialize(response, null);
	}
}