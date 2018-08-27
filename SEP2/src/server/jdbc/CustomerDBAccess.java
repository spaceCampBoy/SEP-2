package server.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import shared.Account;
import shared.Customer;
import shared.DateUtil;

public class CustomerDBAccess {

	private Connection connection;
	private PreparedStatement pStatement;
	private String sql, driver, url, user, pw;
	private ResultSet resultSet;

	public CustomerDBAccess()
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


	public String createCustomer(Customer customer) {
		// TODO Auto-generated method stub
		sql = "INSERT INTO onlinebankingsystem.Customer (CPR_No) "
				+ "values (?)";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, customer.getCPRNo().trim());
			pStatement.executeUpdate();
			System.out.println("Customer User added to Database.");
			return "Customer User added!";
		} catch (SQLException e) {

			e.printStackTrace();
			if(e.getErrorCode()==23505){
				return "Customer user already exists!";
			}
			else{
				return e.getMessage();
			}
		}
		finally {
			closeStatementConnection();
		}
	}


	public void deleteCustomer(Customer customer) {
		// TODO Auto-generated method stub
		sql = "DELETE FROM onlinebankingsystem.customer where crp_no= ? ";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, customer.getCPRNo().trim());
			pStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Customer User deleted from Database.");
	}


	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		sql = "UPDATE onlinebankingsystem.person set fname= ?, lname= ?,dob= ?, gender= ?, address= ?, postal_Code= ?, city= ?, country= ?, phoneno= ?, email= ?, nationality= ? "
				+ " WHERE cpr_no =?";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, customer.getfName().trim());
			pStatement.setString(2, customer.getlName().trim());
			pStatement.setDate(3, Date.valueOf(customer.getBirthday()));
			pStatement.setString(4, String.valueOf(customer.getGender()));
			pStatement.setString(5, customer.getAddress());
			pStatement.setInt(6, customer.getPostalCode());
			pStatement.setString(7, customer.getCity());
			pStatement.setString(8, customer.getCountry());
			pStatement.setInt(9, Integer.parseInt(customer.getPhoneNumber()));
			pStatement.setString(10, customer.getEmail());
			pStatement.setString(11, customer.getNationality());
			pStatement.setString(12, customer.getCPRNo());
			pStatement.executeUpdate();
			System.out.println("Customer User updated to Database.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeStatementConnection();
		}
	}


	public Customer getCustomerInfo(String cprNo) {
		// TODO Auto-generated method stub
		sql="SELECT * FROM onlinebankingsystem.person, onlinebankingsystem.customer" +
				" WHERE person.cpr_no = customer.cpr_no  AND customer.cpr_no = ?";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, cprNo);
			resultSet = pStatement.executeQuery();
			while(resultSet.next())
			{
				Customer customer = new Customer();
					customer.setCPRNo(resultSet.getString("cpr_no"));
					customer.setfName(resultSet.getString("fname"));
					customer.setlName(resultSet.getString("lname"));
					customer.setBirthday(DateUtil.dateFromDB(resultSet.getString("dob")));
					customer.setGender(resultSet.getString("gender").charAt(0));
					customer.setAddress(resultSet.getString("address"));
					customer.setPhoneNumber(resultSet.getString("phoneno"));
					customer.setPosteCode(resultSet.getInt("postal_code"));
					customer.setCity(resultSet.getString("city"));
					customer.setCountry(resultSet.getString("country"));
					customer.setEmail(resultSet.getString("email"));
					customer.setNationality(resultSet.getString("nationality"));

					System.out.println(customer.toString());
					return customer;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeStatementConnection();
		}
		return null;
	}


	public ArrayList<Customer> getAllCustomers(String lastName) {
		// TODO Auto-generated method stub
		ArrayList<Customer> customers = new ArrayList<Customer>();
		 sql="SELECT * FROM onlinebankingsystem.person, onlinebankingsystem.customer" +
		 		" WHERE person.cpr_no = customer.cpr_no  AND person.lname = ?";
			try {
				openConnection();
				pStatement = connection.prepareStatement(sql);
				pStatement.setString(1, lastName);
				resultSet = pStatement.executeQuery();
				System.out.println("Getting all customers with last name as :" +lastName);
				while(resultSet.next())
				{
					Customer customer = new Customer();
						customer.setCPRNo(resultSet.getString("cpr_no"));
						customer.setfName(resultSet.getString("fname"));
						customer.setlName(resultSet.getString("lname"));
						customer.setBirthday(DateUtil.dateFromDB(resultSet.getString("dob")));
						customer.setGender(resultSet.getString("gender").charAt(0));
						customer.setAddress(resultSet.getString("address"));
						customer.setPhoneNumber(resultSet.getString("phoneno"));
						customer.setPosteCode(resultSet.getInt("postal_code"));
						customer.setCity(resultSet.getString("city"));
						customer.setCountry(resultSet.getString("country"));
						customer.setEmail(resultSet.getString("email"));
						customer.setNationality(resultSet.getString("nationality"));

						customers.add(customer);
					}
				}

			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				closeStatementConnection();
			}
			return customers;
	}



	public void setSharedAccountCustomers(String cprNo, Account account) {
		// TODO Auto-generated method stub

		System.out.println("Checking for shared account costumers");

		sql="SELECT customer_account.cpr_no, person.fname, person.lname" +
				" FROM onlinebankingsystem.person" +
				" INNER JOIN onlinebankingsystem.customer_account" +
				" ON customer_account.cpr_no = person.cpr_no AND "
				+ "customer_account.regno= ? AND customer_account.accno= ? AND customer_account.cpr_no != ?";

		ResultSet resultSet;
		try
		{	openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, account.getRegNo());
			pStatement.setString(2, account.getAccNo());
			pStatement.setString(3, cprNo);
			resultSet = pStatement.executeQuery();
				while(resultSet.next())
				{
						System.out.println("Saved Transaction found");
						Customer customer = new Customer();

						customer.setfName(resultSet.getString("fname"));
						customer.setlName(resultSet.getString("lname"));

						account.addintoSharedWith(customer);

				}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeStatementConnection();
		}
	}


}
