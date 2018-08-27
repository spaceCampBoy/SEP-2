package client.model;

import shared.Admin;
import shared.Customer;

public class BankModel implements IBankModel{

	private Customer customer;
	private Admin admin;
	private String username;

	@Override
	public void saveCustomerData(Customer customer) {
		this.customer = customer;
	}

	@Override
	public void deleteCustomerData() {
		customer = null;
		username = null;
	}

	@Override
	public Customer getCustomerData() {
		return customer;
	}

	@Override
	public void saveUsername(String username) {
		this.username = username;
	}

	@Override
	public String getUsername() {
		return username;
	}
}
