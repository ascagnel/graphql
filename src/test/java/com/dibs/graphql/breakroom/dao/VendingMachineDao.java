package com.dibs.graphql.breakroom.dao;

import java.util.List;

import com.dibs.graphql.breakroom.data.VendingMachine;
import com.dibs.graphql.data.request.QueryTree;

public interface VendingMachineDao {
	
	public VendingMachine read(QueryTree query);
	public List<VendingMachine> filter(QueryTree query);

}
