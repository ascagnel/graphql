package com.dibs.graphql.breakroom.data;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class BreakRoom {

	public static final String ID_PROPERTY = "id";
	private Long id;
	
	public static final String NAME_PROPERTY = "name";
	private String name;
	
	public static final String VENDING_MACHINES_PROPERTY = "vendingMachines";	
	private List<VendingMachine> vendingMachines;

	public BreakRoom() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<VendingMachine> getVendingMachines() {
		return vendingMachines;
	}

	public void setVendingMachines(List<VendingMachine> vendingMachines) {
		this.vendingMachines = vendingMachines;
	}

	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}
}
