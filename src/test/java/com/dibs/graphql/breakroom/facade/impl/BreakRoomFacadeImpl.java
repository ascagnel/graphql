package com.dibs.graphql.breakroom.facade.impl;

import java.util.List;

import com.dibs.graphql.breakroom.dao.BreakRoomDao;
import com.dibs.graphql.breakroom.data.BreakRoom;
import com.dibs.graphql.breakroom.data.VendingMachine;
import com.dibs.graphql.breakroom.facade.BreakRoomFacade;
import com.dibs.graphql.breakroom.facade.VendingMachineFacade;
import com.dibs.graphql.data.QueryTree;
import com.dibs.graphql.prune.FieldTrimmer;

public class BreakRoomFacadeImpl implements BreakRoomFacade {
	
	private BreakRoomDao breakRoomDao;
	private VendingMachineFacade vendingMachineFacade;
	
	public BreakRoomFacadeImpl() {
	}
	
	public BreakRoomDao getBreakRoomDao() {
		return breakRoomDao;
	}

	public void setBreakRoomDao(BreakRoomDao breakRoomDao) {
		this.breakRoomDao = breakRoomDao;
	}

	public VendingMachineFacade getVendingMachineFacade() {
		return vendingMachineFacade;
	}

	public void setVendingMachineFacade(VendingMachineFacade vendingMachineFacade) {
		this.vendingMachineFacade = vendingMachineFacade;
	}

	@Override
	public BreakRoom read(QueryTree query) {
		if (query.getArgumentValue(BreakRoom.ID_PROPERTY) == null) {
			throw new RuntimeException("Param [" + BreakRoom.ID_PROPERTY + "] is required to read a break room");
		}
		
		BreakRoom breakRoom = breakRoomDao.read(query);
		
		QueryTree vendingMachineQuery = query.getChildTree(BreakRoom.VENDING_MACHINES_PROPERTY);

		if (vendingMachineQuery != null) {						
			List<VendingMachine> vendingMachines = vendingMachineFacade.filter(vendingMachineQuery);
			breakRoom.setVendingMachines(vendingMachines);
		}
		
		FieldTrimmer.trim(breakRoom, query.getChildFields());
		
		return breakRoom;
	}

}
