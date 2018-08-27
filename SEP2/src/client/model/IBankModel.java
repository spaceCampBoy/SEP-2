package client.model;

import shared.Admin;
import shared.Customer;

public interface IBankModel {
	void saveCustomerData(Customer customer);
	void deleteCustomerData();
	Customer getCustomerData();
	void saveUsername(String username);
	String getUsername();
}
