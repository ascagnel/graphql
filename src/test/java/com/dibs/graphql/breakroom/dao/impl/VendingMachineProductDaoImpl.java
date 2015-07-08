package com.dibs.graphql.breakroom.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dibs.graphql.breakroom.dao.VendingMachineProductDao;
import com.dibs.graphql.breakroom.data.VendingMachineProduct;
import com.dibs.graphql.data.QueryTree;

@Component
public class VendingMachineProductDaoImpl implements VendingMachineProductDao {

	@Override
	public VendingMachineProduct read(QueryTree query) {
		VendingMachineProduct product = new VendingMachineProduct();
		product.setCalories(System.currentTimeMillis());
		product.setDescription("A tasty snack attack ");
		product.setName("Snack " + query.getParamValue(VendingMachineProduct.ID_PROPERTY));
		product.setId(Long.valueOf(query.getParamValue(VendingMachineProduct.ID_PROPERTY)));
		
		return product;
	}

	@Override
	public List<VendingMachineProduct> filter(QueryTree query) {
		List<VendingMachineProduct> products = new LinkedList<>();
		
		for (int i = 0; i < 5; i++) {
			VendingMachineProduct product = new VendingMachineProduct();
			product.setCalories(System.currentTimeMillis());
			product.setDescription("A tasty snack attack " + i);
			product.setName("Snack " + i);
			product.setId(Long.valueOf(i));
			
			products.add(product);
		}

		return products;
	}

}
