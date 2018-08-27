package shared;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class creating an Account object with account number and balance
 * @author Hristo Getov
 * @version 1.0
 */

public class Account implements Serializable{

	private String name;
	private String accNo;
	private double balance;
	private String regNo;
	private int transferLimit;
	private int creditLimit;
	private ArrayList<Transaction> transactions;
	private ArrayList<Customer> sharedWith;
	/**
	 * No-Argument constructor
	 */

	public Account(){
	transactions = new ArrayList<Transaction>();
	sharedWith = new ArrayList<Customer>();
	}

	/**
	 * Five-Argument constructor initialize the account number and the balance
	 * @param accNo
	 * @param balance
	 */

	public Account(String name, String accNo, double balance, String regNo, int transferLimit, int creditLimit){
		this.name = name;
		this.accNo = accNo;
		this.balance = balance;
		this.regNo = regNo;
		this.transferLimit = transferLimit;
		this.creditLimit = creditLimit;
		transactions = new ArrayList<Transaction>();
		sharedWith = new ArrayList<Customer>();
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	/**
	 * Gets the account number
	 * @return account number
	 */
	public String getAccNo() {
		return accNo;
	}
	/**
	 * Sets the account number
	 * @param accNo
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	/**
	 * Gets the balance
	 * @return balance
	 */
	public double getBalance() {
		return balance;
	}
	/**
	 * Sets balance
	 * @param balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public int getTransferLimit() {
		return transferLimit;
	}
	public void setTransferLimit(int transferLimit) {
		this.transferLimit = transferLimit;
	}
	public int getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(int creditLimit) {
		this.creditLimit = creditLimit;
	}
	public ArrayList<Transaction> getTransactions(){
		return transactions;
	}
	public void setTransactions(ArrayList<Transaction> transactions){
		this.transactions=transactions;
	}

	public void addTransactions(Transaction transaction){
		transactions.add(transaction);
	}


	public void addintoSharedWith(Customer customer)
	{
		sharedWith.add(customer);
	}

	public ArrayList<Customer> getSharedWith(){
		return sharedWith;
	}

	public void getSharedWithAtIndex(int index){
		sharedWith.get(index);
	}


	/**
	 * String representation of  Account
	 * @return String representation of Account in format account number, balance
	 */

	public String toString(){
		return "Account: \nName: " + name + " " + "Registration number: " + regNo + " Account: " + accNo + ", transfer limit: " +transferLimit + ", credit limit: " + creditLimit + "\nBalance: " + balance+"\n"+
				"Transactions: " + transactions;
	}

}
