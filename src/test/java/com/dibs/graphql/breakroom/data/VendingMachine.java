package com.dibs.graphql.breakroom.data;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class VendingMachine {

	public static final String ID_PROPERTY = "id";
	private Integer id;
	private Integer merchandiseCount;
	private String servicedBy;
	
	public static final String PRODUCTS_PROPERTY = "products";
	private List<VendingMachineProduct> products;

	public VendingMachine() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMerchandiseCount() {
		return merchandiseCount;
	}

	public void setMerchandiseCount(Integer merchandiseCount) {
		this.merchandiseCount = merchandiseCount;
	}

	public String getServicedBy() {
		return servicedBy;
	}

	public void setServicedBy(String servicedBy) {
		this.servicedBy = servicedBy;
	}

	public List<VendingMachineProduct> getProducts() {
		return products;
	}

	public void setProducts(List<VendingMachineProduct> products) {
		this.products = products;
	}
	
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}
}
