package com.dibs.graphql.breakroom.facade.impl;

import java.util.List;

import com.dibs.graphql.breakroom.dao.VendingMachineProductDao;
import com.dibs.graphql.breakroom.data.VendingMachineProduct;
import com.dibs.graphql.breakroom.facade.VendingMachineProductFacade;
import com.dibs.graphql.data.QueryTree;
import com.dibs.graphql.prune.FieldTrimmer;

public class VendingMachineProductFacadeImpl implements VendingMachineProductFacade {

	private VendingMachineProductDao productDao;
	
	public VendingMachineProductFacadeImpl() {
	}
	
	public VendingMachineProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(VendingMachineProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public VendingMachineProduct read(QueryTree query) {
		// An id is required to read
		if (query.getArgumentValue(VendingMachineProduct.ID_PROPERTY) == null) {
			throw new RuntimeException("Param [" + VendingMachineProduct.ID_PROPERTY +"] required to read a VendingMachineProduct");
		}
		
		VendingMachineProduct product = productDao.read(query);

		// Trim off any fields not defined in the query
		FieldTrimmer.trim(product, query.getChildFields());
		
		return product;
	}

	@Override
	public List<VendingMachineProduct> filter(QueryTree query) {
		List<VendingMachineProduct> products = productDao.filter(query);
		
		// Trim off any fields not defined in the query
		FieldTrimmer.trim(products, query.getChildFields());
		
		return products;
	}
}
