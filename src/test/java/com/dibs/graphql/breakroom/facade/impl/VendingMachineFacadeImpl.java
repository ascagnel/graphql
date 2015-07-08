package com.dibs.graphql.breakroom.facade.impl;

import java.util.List;

import com.dibs.graphql.breakroom.dao.VendingMachineDao;
import com.dibs.graphql.breakroom.data.VendingMachine;
import com.dibs.graphql.breakroom.data.VendingMachineProduct;
import com.dibs.graphql.breakroom.facade.VendingMachineFacade;
import com.dibs.graphql.breakroom.facade.VendingMachineProductFacade;
import com.dibs.graphql.data.QueryTree;
import com.dibs.graphql.prune.FieldTrimmer;

public class VendingMachineFacadeImpl implements VendingMachineFacade {

	private VendingMachineDao machineDao;
	private VendingMachineProductFacade vendingMachineProductFacade;

	public VendingMachineFacadeImpl() {
	}
	
	public VendingMachineDao getMachineDao() {
		return machineDao;
	}

	public void setMachineDao(VendingMachineDao machineDao) {
		this.machineDao = machineDao;
	}

	public VendingMachineProductFacade getVendingMachineProductFacade() {
		return vendingMachineProductFacade;
	}

	public void setVendingMachineProductFacade(VendingMachineProductFacade vendingMachineProductFacade) {
		this.vendingMachineProductFacade = vendingMachineProductFacade;
	}

	@Override
	public VendingMachine read(QueryTree query) {
		// An id is required to read
		if (query.getParamValue(VendingMachine.ID_PROPERTY) == null) {
			throw new RuntimeException("Param [" + VendingMachine.ID_PROPERTY +"] required to read a VendingMachine");
		}
		
		VendingMachine machine = machineDao.read(query);
		
		List<VendingMachineProduct> vendingMachineProducts = vendingMachineProductFacade.filter(null);
		machine.setProducts(vendingMachineProducts);
		
		// Trim off any fields not defined in the query
		FieldTrimmer.trim(machine, query.getChildFields());
		
		return machine;
	}

	@Override
	public List<VendingMachine> filter(QueryTree query) {
		List<VendingMachine> vendingMachines = machineDao.filter(query);
		
		QueryTree productQuery = query.getChildTree(VendingMachine.PRODUCTS_PROPERTY);
		
		// If the field isn't defined in the schema, don't fetch it
		if (productQuery != null) {			
			for (VendingMachine vendingMachine : vendingMachines) {
				
				List<VendingMachineProduct> vendingMachineProducts = vendingMachineProductFacade.filter(productQuery);
				vendingMachine.setProducts(vendingMachineProducts);
			}
		}
		
		// Trim off any fields not defined in the query
		FieldTrimmer.trim(vendingMachines, query.getChildFields());
		
		return vendingMachines;
	}

}
