package com.dibs.graphql.response;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.dibs.graphql.breakroom.data.BreakRoom;
import com.dibs.graphql.breakroom.data.VendingMachine;
import com.dibs.graphql.data.request.Query;
import com.dibs.graphql.data.request.QueryBuilder;
import com.dibs.graphql.data.response.Response;
import com.dibs.graphql.response.manager.QueryResponseTypeFactory;
import com.dibs.graphql.response.manager.QueryResponseTypeManagerRegistry;
import com.dibs.graphql.response.manager.QueryResponseTypeReader;
import com.dibs.graphql.response.manager.QueryResponseTypeWriter;
import com.dibs.graphql.response.manager.impl.QueryResponseTypeFactoryMapImpl;
import com.dibs.graphql.response.manager.impl.QueryResponseTypeFactoryReflectionImpl;
import com.dibs.graphql.response.manager.impl.QueryResponseTypeReaderBeanImpl;
import com.dibs.graphql.response.manager.impl.QueryResponseTypeReaderMapImpl;
import com.dibs.graphql.response.manager.impl.QueryResponseTypeWriterBeanImpl;
import com.dibs.graphql.response.manager.impl.QueryResponseTypeWriterMapImpl;
import com.dibs.graphql.response.processor.impl.QueryResponseProcessor;
import com.dibs.graphql.serialize.impl.ResponseSerializerGsonImpl;

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
		
		Map<Class<?>, QueryResponseTypeFactory> factories = new HashMap<>();
		factories.put(Map.class, new QueryResponseTypeFactoryMapImpl());
		
		Map<Class<?>, QueryResponseTypeReader> readers = new HashMap<>();
		readers.put(Map.class, new QueryResponseTypeReaderMapImpl());

		Map<Class<?>, QueryResponseTypeWriter> writers = new HashMap<>();
		writers.put(Map.class, new QueryResponseTypeWriterMapImpl());
		
		QueryResponseTypeManagerRegistry.getInstance().setDefaultQueryResponseTypeFactory(new QueryResponseTypeFactoryReflectionImpl());
		QueryResponseTypeManagerRegistry.getInstance().setDefaultQueryResponseTypeReader(new QueryResponseTypeReaderBeanImpl());
		QueryResponseTypeManagerRegistry.getInstance().setDefaultQueryResponseTypeWriter(new QueryResponseTypeWriterBeanImpl());
		
		QueryResponseTypeManagerRegistry.getInstance().setTypeFactories(factories);
		QueryResponseTypeManagerRegistry.getInstance().setTypeReaders(readers);
		QueryResponseTypeManagerRegistry.getInstance().setTypeWriters(writers);
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
	
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testMap() throws IOException {	
		Map<String, Object> data = new QueryResponseProcessor(buildFullyInflatedBreakRoomQuery()).process(breakRoom, Map.class);
		
		assertNotNull(data.get("id"));
		assertNotNull(data.get("name"));
		assertNotNull(data.get("vendingMachines"));
		assertNotNull(((Map)((List)data.get("vendingMachines")).get(0)).get("id"));
		
		Response response = new Response();
		response.setData(data);
		
		ResponseSerializerGsonImpl serializer = new ResponseSerializerGsonImpl();
		serializer.serialize(new ByteArrayOutputStream(), response);
	}
}
