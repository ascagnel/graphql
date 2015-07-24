package com.dibs.graphql.breakroom.dao;

import java.util.List;

import com.dibs.graphql.breakroom.data.VendingMachineProduct;
import com.dibs.graphql.data.request.QueryTree;

public interface VendingMachineProductDao {
	
	public VendingMachineProduct read(QueryTree query);
	public List<VendingMachineProduct> filter(QueryTree query);
}
