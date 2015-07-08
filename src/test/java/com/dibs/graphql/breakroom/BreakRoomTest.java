package com.dibs.graphql.breakroom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.dibs.graphql.breakroom.dao.BreakRoomDao;
import com.dibs.graphql.breakroom.dao.VendingMachineDao;
import com.dibs.graphql.breakroom.dao.VendingMachineProductDao;
import com.dibs.graphql.breakroom.dao.impl.BreakRoomDaoImpl;
import com.dibs.graphql.breakroom.dao.impl.VendingMachineDaoImpl;
import com.dibs.graphql.breakroom.dao.impl.VendingMachineProductDaoImpl;
import com.dibs.graphql.breakroom.data.BreakRoom;
import com.dibs.graphql.breakroom.data.VendingMachine;
import com.dibs.graphql.breakroom.data.VendingMachineProduct;
import com.dibs.graphql.breakroom.facade.BreakRoomFacade;
import com.dibs.graphql.breakroom.facade.VendingMachineFacade;
import com.dibs.graphql.breakroom.facade.VendingMachineProductFacade;
import com.dibs.graphql.breakroom.facade.impl.BreakRoomFacadeImpl;
import com.dibs.graphql.breakroom.facade.impl.VendingMachineFacadeImpl;
import com.dibs.graphql.breakroom.facade.impl.VendingMachineProductFacadeImpl;
import com.dibs.graphql.data.Query;
import com.dibs.graphql.data.QueryBuilder;
import com.dibs.graphql.data.QueryTree;


public class BreakRoomTest {

	private BreakRoomFacade breakRoomFacade;
	
	@Before
	public void setUp() {
		VendingMachineProductDao vendingMachineProductDao = new VendingMachineProductDaoImpl();
		VendingMachineProductFacade vendingMachineProductFacade = new VendingMachineProductFacadeImpl();
		vendingMachineProductFacade.setProductDao(vendingMachineProductDao);
		
		VendingMachineDao vendingMachineDao = new VendingMachineDaoImpl();
		VendingMachineFacade vendingMachineFacade = new VendingMachineFacadeImpl();
		vendingMachineFacade.setMachineDao(vendingMachineDao);
		vendingMachineFacade.setVendingMachineProductFacade(vendingMachineProductFacade);
		

		BreakRoomDao breakRoomDao = new BreakRoomDaoImpl();
		breakRoomFacade = new BreakRoomFacadeImpl();
		breakRoomFacade.setBreakRoomDao(breakRoomDao);
		breakRoomFacade.setVendingMachineFacade(vendingMachineFacade);
	}
	
	private Query buildShallowBreakRoom() {
		Query breakRoom = 
			new QueryBuilder()
				.subQuery(new QueryBuilder()
					.name("breakRoom")
					.param("id", "123")
						.subQuery(new QueryBuilder().name("id").build())
						.subQuery(new QueryBuilder().name("name").build())
						.build())
					.build();
		
		return breakRoom;
	}
	
	private Query buildFullyInflatedBreakRoom() {
		Query breakRoom = 
			new QueryBuilder()
				.subQuery(new QueryBuilder()
					.name("breakRoom")
					.param("id", "123")
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
						.build())
					.build();
		
		return breakRoom;
	}
	
	@Test
	public void testShallowTopFields() {
		QueryTree requestQuery = QueryTree.fromQuery(buildShallowBreakRoom());
		BreakRoom breakRoom = breakRoomFacade.read(requestQuery.getChildTree("breakRoom"));
		assertNotNull(breakRoom);
		assertNotNull(breakRoom.getId());
		assertEquals(new Long(123), breakRoom.getId());
		assertNotNull(breakRoom.getName());
		assertNull(breakRoom.getVendingMachines());
	}
	
	@Test
	public void testFullInflation() {
		QueryTree requestQuery = QueryTree.fromQuery(buildFullyInflatedBreakRoom());
		BreakRoom breakRoom = breakRoomFacade.read(requestQuery.getChildTree("breakRoom"));
		
		assertNotNull(breakRoom);
		assertNotNull(breakRoom.getId());
		assertEquals(new Long(123), breakRoom.getId());
		assertNotNull(breakRoom.getName());
		assertNotNull(breakRoom.getVendingMachines());
		
		VendingMachine machine = breakRoom.getVendingMachines().get(0);
		assertNotNull(machine.getId());
		assertNotNull(machine.getMerchandiseCount());
		assertNotNull(machine.getServicedBy());
		assertNotNull(machine.getProducts());
		
		VendingMachineProduct product = machine.getProducts().get(0);
		assertNotNull(product.getId());
		assertNotNull(product.getName());
		assertNotNull(product.getDescription());
		assertNotNull(product.getCalories());
	}
}
