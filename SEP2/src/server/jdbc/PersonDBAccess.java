package server.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import shared.Person;

public class PersonDBAccess {

	private Connection connection;
	private PreparedStatement pStatement;
	private String sql, driver, url, user, pw;
	private ResultSet resultSet;

	public PersonDBAccess()
	{
		/*
		 *  Setting up a connection to the Database
		 *  1. Loading JDBC driver
		 *  2. URL for database
		 *  3. Username to database
		 *  4. password to database
		 */

		driver = "org.postgresql.Driver";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url = "jdbc:postgresql://localhost:5432/postgres";
		user = "postgres";
		//pw = "8803096680";
		 pw = "postgres"; //
		//pw = "521765";

		sql = "";
		pStatement = null;
		resultSet = null;

	}

	private void openConnection() throws SQLException
	{
			connection = DriverManager.getConnection(url, user, pw);
	}

	private void closeStatementConnection()
	{
		try {
			pStatement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public boolean existPerson(Person person) {
		// TODO Auto-generated method stub
		boolean result = false;
		sql=("SELECT * FROM onlinebankingsystem.person"
				+ " WHERE person.cpr_no = ?");
		try {
			System.out.println("checking in database if person already exists");
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, person.getCPRNo());
			resultSet = pStatement.executeQuery();
			while(resultSet.next())
			{
				System.out.println("person exists");
					result = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeStatementConnection();
		}
		return result;
	}


	public String insertPerson(Person person) {
		// TODO Auto-generated method stub
		sql = "INSERT INTO onlinebankingsystem.person (CPR_No, fName, lName, DOB, Gender, address, postal_Code, city, country, phoneNo, email, nationality)"
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				openConnection();
				pStatement = connection.prepareStatement(sql);
				pStatement.setString(1, person.getCPRNo());
				pStatement.setString(2, person.getfName().trim());
				pStatement.setString(3, person.getlName().trim());
				pStatement.setDate(4, Date.valueOf(person.getBirthday()));
				pStatement.setString(5, String.valueOf(person.getGender()));
				pStatement.setString(6, person.getAddress());
				pStatement.setInt(7, person.getPostalCode());
				pStatement.setString(8, person.getCity());
				pStatement.setString(9, person.getCountry());
				pStatement.setString(10, person.getPhoneNumber());
				pStatement.setString(11, person.getEmail());
				pStatement.setString(12, person.getNationality());
				pStatement.executeUpdate();
				System.out.println("Person added to Database.");
				return "Person added";
			} catch (SQLException e) {

				e.printStackTrace();
				if(e.getErrorCode()==23505){
					return "Admin user already exists!";
				}
				else{
					return e.getMessage();
				}
			}
			finally {
				closeStatementConnection();
			}
	}


	public void updatePerson(Person person) {
		// TODO Auto-generated method stub
		/*sql = "UPDATE onlinebankingsystem.person set fname= '"+person.getfName()+"', lname= '"+person.getlName()
		+ "',dob= DATE'"+DateUtil.dateForDB(person.getBirthday())+"', gender= '"+person.getGender()+"', address= '"+person.getAddress()+"', postal_Code= "+person.getPostalCode()
		+", city= '"+person.getCity()+"', country= '"+person.getCountry()+"', phoneno='"+person.getPhoneNumber()+"', email='"+person.getEmail()+"', nationality='"+person.getNationality()
		+ "'"
		+ "WHERE cpr_no ='"+person.getCPRNo()+"',";
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("person updated in Database.");*/

	}

}
