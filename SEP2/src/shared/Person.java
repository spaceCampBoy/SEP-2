package shared;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A class creating a Person object with first name,last name,birthday,gender,idNumber,address,nationality,phone number, email.
 * @author Hristo Getov
 * @version 1.0
 */
public abstract class Person implements Serializable{
	private String fName;
	private String lName;
	private String CPRNo;
	private LocalDate birthday;
	private char gender;
	private String address;
	private int posteCode;
	private String city;
	private String country;
	private String nationality;
	private String phoneNumber;
	private String email;

	/**
	 * No-argument constructor
	 * Needed if some of the users data is not fulfill
	 */
	public Person(){
		fName="";
		lName = "";
		CPRNo = "";
		birthday=null;
		gender='M';
		address = "";
		posteCode=0;
		city="";
		country = "";
		nationality="";
		phoneNumber = "";
		email = "";
	}
	/**
	 * Nine-argument constructor initializing the Person
	 * @param fName
	 * @param lName
	 * @param CPRNo
	 * @param birthday
	 * @param gender
	 * @param address
	 * @param posteCode
	 * @param city
	 * @param country
	 * @param nationality
	 * @param phoneNumber
	 * @param email
	 */
	public Person(String fName, String lName,String CPRNo,LocalDate birthday,char gender, String address,int posteCode,String city,String country, String nationality, String phoneNumber, String email){
		this.fName=fName;
		this.lName = lName;
		this.CPRNo = CPRNo;
		this.birthday=birthday;
		this.gender=gender;
		this.address = address;
		this.posteCode=posteCode;
		this.city=city;
		this.country=country;
		this.nationality=nationality;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	/**
	 * Gets the first name of the person
	 * @return first name of the person
	 */

	public String getfName() {
		return fName;
	}

	/**
	 * Sets first name of the person
	 * @param fName
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}
	/**
	 * Gets the gender of person
	 * @return gender of the person
	 */

	public char getGender(){
		return gender;
	}
	/**
	 * Gets last name of the person
	 * @return last name of the person
	 */

	public String getlName() {
		return lName;
	}
	/**
	 * Sets last name of the person
	 * @param lName
	 */

	public void setlName(String lName) {
		this.lName = lName;
	}
	/**
	 * Sets gender of the person
	 * @param gender
	 */
	public void setGender(char gender){
		this.gender=gender;
	}

	/**
	 * Gets the cpr number of the person
	 * @return Id number of the person
	 */

	public String getCPRNo() {
		return CPRNo;
	}
	/**
	 * Sets the cpr number of the person
	 * @param idNumber
	 */

	public void setCPRNo(String CPRNo) {
		this.CPRNo = CPRNo;
	}
	/**
	 * Gets the birthday of the person
	 * @return birthday of the person
	 */

	public LocalDate getBirthday() {
		return birthday;
	}
	/**
	 * Sets the birthday of the person
	 * @param birthday
	 */

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	/**
	 * Gets the address of the person
	 * @return addres of the person
	 */

	public String getAddress() {
		return address;
	}
	/**
	 * Sets the addres of the person
	 * @param addres
	 */

	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * Gets poste code of a person
	 * @return poste code of the person
	 */
	public int getPostalCode() {
		return posteCode;
	}
	/**
	 * Sets poste code of a person
	 * @param posteCode
	 */
	public void setPosteCode(int posteCode) {
		this.posteCode = posteCode;
	}
	/**
	 * Gets city
	 * @return city of a person
	 */
	public String getCity() {
		return city;
	}
	/**
	 * sets city of a person
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * gets country
	 * @return country of a person
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * Sets country of a person
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * Gets the nationality of the person
	 * @return the nationality of the person
	 */

	public String getNationality() {
		return nationality;
	}
	/**
	 * Sets the nationality of the person
	 * @param nationality
	 */

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	/**
	 * Gets the phone number of the person
	 * @return return the phone number of the person
	 */

	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * Sets the phone of the person
	 * @param phoneNumber
	 */

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * Gets the email of the person
	 * @return the email of the person
	 */

	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email of the person
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * String representation of the person
	 * @return String representation of a person in format fName  lName, birthday,gender, CPRNo, address,poste code, city, country, nationality, phone number, email
	 */
	public String toString(){
		return fName + " " + " " + lName +", birthday: " + birthday + "gender: " + gender +", cpr number: " + CPRNo + ", address: " + address +" poste code: " + posteCode+ " city: "+ city+" country: " + country + ", nationality: " + nationality + " phone number: " + phoneNumber + ", email: " + email;
 	}
}