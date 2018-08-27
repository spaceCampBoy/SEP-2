package server;

import java.util.ArrayList;

import shared.Account;
import shared.Customer;
import shared.Transaction;

public interface IDBAdapter {
	/**
	 *
	 * @param obj
	 */
	void add(Object obj);
	void delete(String cprNo, Object obj);
	void update(Object obj);
	void assignAccountToCustomer(String cprNo, String accNo, String regNo);
	String login(String username, String password);
	Object checkValue(String username, String password, String key, String value, Object obj);
	int transferMoney(Account acc,String cprNo, Transaction trans, boolean saveTransfer);
	ArrayList<Customer> getAllCustomersWithLastName(String lastName);
	Customer getCustomerInfo(String cprNo);
	void dailyTransactionCheck();
	void addInterest();
}