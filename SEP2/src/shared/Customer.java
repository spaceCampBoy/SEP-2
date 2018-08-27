package shared;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * A class creating a Customer object with Customer's information using User object.
 * @author Hristo Getov
 * @version 1.0
 */

public class Customer extends Person implements Serializable{
	private ArrayList<Account> accounts;
	private ArrayList<Transaction> savedTransactions;
	//private Account account;

	public Customer() {
		accounts = new ArrayList<>();
		savedTransactions = new ArrayList<>();
	}

	/**
	    * No-argument constructor initializing the Customer
	    * Neeed if there is no customers account

	    */
	public Customer(String fName, String lName, String CPRNo, LocalDate birthday,char gender, String address,int postCode,String city,String country, String nationality, String phoneNumber, String email){
		super(fName,lName,CPRNo,birthday,gender,address,postCode,city,country,nationality,phoneNumber,email);
		accounts = new ArrayList<>();
	}
	/**
	 * One argument constructor initiaizing account
	 * @param fName
	 * @param midName
	 * @param lName
	 * @param idNumber
	 * @param birthday
	 * @param address
	 * @param nationality
	 * @param phoneNumber
	 * @param email
	 * @param account
	 */
	public Customer(String fName, String lName, String CPRNo, LocalDate birthday,char gender, String address, int postCode, String city,String country, String nationality, String phoneNumber, String email,Account account){
		super(fName,lName,CPRNo,birthday,gender,address,postCode,city,country,nationality,phoneNumber,email);
		accounts.add(account);
	}
	/**
	 * Gets customers account
	 * @return Account Object
	 */

	   public Account getAccountAtIndex(int i) {
		return accounts.get(i);
	}
	   /**
	    * Sets customer's account
	    * @param account
	    */
	public void setAccount(Account account, int index) {
		accounts.set(index, account);
	}

	public void addAccount(Account account){
		accounts.add(account);
	}

	public int getAccountSize() {
		return accounts.size();
	}

	public int getsavedTransactionsSize(){
		return savedTransactions.size();
	}

	public void deleteTransactionAtIndex(int index){
		savedTransactions.remove(index);
	}

	public void addTransaction(Transaction transaction){
		savedTransactions.add(transaction);
	}
	
	
	public Transaction getTransactionAtIndex(int index){
		return savedTransactions.get(index);
	}

	/**
	    * returns a string representation of the customer's data
	    * @return a string representation of the date in format: firstname,middlename,lastname,ID number,birthday, address, nationality, phone number, email.
	    */
	public String toString(){
		return "Customer: " + super.toString() + "\tAccount number: " + accounts;
	}
}
