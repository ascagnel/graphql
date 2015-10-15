package com.dibs.graphql.response;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.dibs.graphql.breakroom.data.BreakRoom;
import com.dibs.graphql.breakroom.data.VendingMachine;
import com.dibs.graphql.data.request.Query;
import com.dibs.graphql.data.request.QueryBuilder;
import com.dibs.graphql.data.response.Response;
import com.dibs.graphql.response.processor.impl.QueryResponseProcessor;
import com.dibs.graphql.serialize.impl.ResponseSerializerGsonImpl;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
	
	private Query buildFullyInflatedBreakRoomQuery() {
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
	
	private Query buildPartiallyInflatedBreakRoomQuery() {
		Query breakRoom = 
			new QueryBuilder()
						.subQuery(new QueryBuilder().name("id").build())
						.subQuery(new QueryBuilder()
							.name("vendingMachines")
								.subQuery(new QueryBuilder().name("id").build())
							.build())
					.build();
		
		return breakRoom;
	}
	
	@Test
	public void testBean() throws IOException {
		BreakRoom responseBreakRoom = new QueryResponseProcessor(buildPartiallyInflatedBreakRoomQuery()).process(breakRoom, BreakRoom.class);
		
		assertNotNull(responseBreakRoom.getId());
		assertNull(responseBreakRoom.getName());
		assertNotNull(responseBreakRoom.getVendingMachines());
		assertNotNull(responseBreakRoom.getVendingMachines().get(0).getId());
		assertNull(responseBreakRoom.getVendingMachines().get(0).getMerchandiseCount());
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMap() throws IOException {	
		Map<String, Object> data = new QueryResponseProcessor(buildFullyInflatedBreakRoomQuery()).process(breakRoom, Map.class);
		
		assertNotNull(data.get("id"));
		assertNotNull(data.get("name"));
		assertNotNull(data.get("vendingMachines"));
		assertNotNull(((Map<?,?>)((List<?>)data.get("vendingMachines")).get(0)).get("id"));
		
		Response response = new Response();
		response.setData(data);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ResponseSerializerGsonImpl serializer = new ResponseSerializerGsonImpl();
		serializer.serialize(outputStream, response);
				
		assertNotNull(outputStream);
		assertTrue(outputStream.size() > 0);
	}
	
	@Test
	public void testBeanToJsonElement() throws IOException {
		JsonElement responseBreakRoomJson = new QueryResponseProcessor(buildPartiallyInflatedBreakRoomQuery()).process(breakRoom, JsonElement.class);
		
		JsonObject responseJsonObject = responseBreakRoomJson.getAsJsonObject();
		
		assertNotNull(responseJsonObject.get("id"));
		assertNull(responseJsonObject.get("name"));
		assertNotNull(responseJsonObject.get("vendingMachines"));
		assertNotNull(responseJsonObject.get("vendingMachines").getAsJsonArray().get(0).getAsJsonObject().get("id"));
		assertNull(responseJsonObject.get("vendingMachines").getAsJsonArray().get(0).getAsJsonObject().get("merchandiseCount"));
	}
	
	@Test
	public void testJsonElementToJsonElement() throws IOException {
		JsonElement breakRoomJson = new Gson().toJsonTree(breakRoom);
		JsonElement responseBreakRoomJson = new QueryResponseProcessor(buildPartiallyInflatedBreakRoomQuery()).process(breakRoomJson, JsonElement.class);
		
		JsonObject responseJsonObject = responseBreakRoomJson.getAsJsonObject();
		
		assertNotNull(responseJsonObject.get("id"));
		assertNull(responseJsonObject.get("name"));
		assertNotNull(responseJsonObject.get("vendingMachines"));
		assertNotNull(responseJsonObject.get("vendingMachines").getAsJsonArray().get(0).getAsJsonObject().get("id"));
		assertNull(responseJsonObject.get("vendingMachines").getAsJsonArray().get(0).getAsJsonObject().get("merchandiseCount"));
	}
}
