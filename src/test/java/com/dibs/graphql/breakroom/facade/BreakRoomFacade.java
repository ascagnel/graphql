package com.dibs.graphql.breakroom.facade;

import com.dibs.graphql.breakroom.dao.BreakRoomDao;
import com.dibs.graphql.breakroom.data.BreakRoom;
import com.dibs.graphql.data.QueryTree;

public interface BreakRoomFacade {
	public void setBreakRoomDao(BreakRoomDao breakRoomDao);
	public void setVendingMachineFacade(VendingMachineFacade vendingMachineFacade);
	
	public BreakRoom read(QueryTree query);
}
