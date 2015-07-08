package com.dibs.graphql.breakroom.facade;

import java.util.List;

import com.dibs.graphql.breakroom.dao.VendingMachineProductDao;
import com.dibs.graphql.breakroom.data.VendingMachineProduct;
import com.dibs.graphql.data.QueryTree;

public interface VendingMachineProductFacade {

	public void setProductDao(VendingMachineProductDao productDao);
	
	public VendingMachineProduct read(QueryTree query);
	public List<VendingMachineProduct> filter(QueryTree query);
}
