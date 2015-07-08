package com.dibs.graphql.breakroom.facade;

import java.util.List;

import com.dibs.graphql.breakroom.dao.VendingMachineDao;
import com.dibs.graphql.breakroom.data.VendingMachine;
import com.dibs.graphql.data.QueryTree;

public interface VendingMachineFacade {
	public void setMachineDao(VendingMachineDao machineDao);
	public void setVendingMachineProductFacade(VendingMachineProductFacade vendingMachineProductFacade);
	
	public VendingMachine read(QueryTree query);
	public List<VendingMachine> filter(QueryTree query);

}
