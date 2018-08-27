package shared;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A class creating an Admin object from User object
 * @author Hristo Getov
 * @version 1.0
 */

public class Admin extends Person implements Serializable{
	private int empNo;

	/**
	 * No-Argument constructor initialize an Admin
	 */

	public Admin(){
		empNo = 0;

	}
	/**
	 * One-Argument constructor initialize Admin
	 * @param fName
	 * @param midName
	 * @param lName
	 * @param idNumber
	 * @param birthday
	 * @param address
	 * @param nationality
	 * @param phoneNumber
	 * @param email
	 */

	public Admin(String fName,String lName, String CPRNo, LocalDate birthday,char gender, String address, int postCode,String city, String country, String nationality, String phoneNumber, String email, int empNo) {
		super(fName,lName,CPRNo,birthday,gender,address,postCode,city,country,nationality,phoneNumber,email);
		this.empNo=empNo;
	}

	/**
	 * Gets employee number of the admin
	 * @return employee number
	 */
	public int getEmpNo() {
		return empNo;
	}
	/**
	 * Sets the employee number of the admin
	 * @param empNo
	 */
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	/**
	 * Creates Customer Object and returns it
	 * @param fName
	 * @param midName
	 * @param lName
	 * @param idNumber
	 * @param birthday
	 * @param address
	 * @param nationality
	 * @param phoneNumber
	 * @param email
	 * @return Customer Object
	 */
	public Person createCustomer(String fName,String lName, String CPRNo, LocalDate birthday,char gender, String address,int postCode,String city,String country, String nationality, String phoneNumber, String email){
		Person customer = new Customer(fName,lName,CPRNo, birthday,gender, address, postCode, city, country, nationality, phoneNumber, email);
		return customer;
	}

	/**
	 * Creates an Admin Object and returns it
	 * @param fName
	 * @param midName
	 * @param lName
	 * @param idNumber
	 * @param birthday
	 * @param address
	 * @param nationality
	 * @param phoneNumber
	 * @param email
	 * @return Admin Object
	 */
	public Person createAdmin(String fName,String lName, String CPRNo, LocalDate birthday,char gender, String address, int postCode, String city, String country, String nationality, String phoneNumber, String email, int empNo){
		Person admin = new Admin(fName,lName, CPRNo, birthday,gender, address, postCode,city, country, nationality, phoneNumber, email,empNo);
		return admin;
	}
	/**
	 * Creates an Account Object and returns it
	 * @param accNo
	 * @param balance
	 * @return Account Object
	 */

	public Account createAccount(String accNo, int balance, int regNo,int transferLimit,int creditLimit){
		//Account account = new Account(accNo,balance,regNo,transferLimit,creditLimit);
		return null;
	}
	/**
	 * Add Account to Customer
	 * @param cust
	 * @param acc
	 */

	public void addAccount(Person cust,Account acc){
		Customer temp =(Customer) cust;
//		temp.setAccount(acc);
	}
	/**
	 * String representation of the Admin Object
	 * @return String representation of the Admin Object in format empNo: fName  lName, birthday, idNumber, address, nationality, phone number, email
	 */

	public String toString(){
		return "Admin: " + empNo +": " + super.toString();
	}

}
