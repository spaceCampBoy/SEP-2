package server.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import shared.Account;

public class AccountDBAccess{

	private Connection connection;
	private PreparedStatement pStatement;
	private String sql, driver, url, user, pw;
	private ResultSet resultSet;

	public AccountDBAccess()
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

	private void closeConnection()
	{
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public String createAccount(Account account) {
		// TODO Auto-generated method stub
		sql = "INSERT INTO onlinebankingsystem.Account (accNo, regNo, AccName, balance, transferLimit, creditLimit)"
				+ " VALUES (?, ?, ?, ?, ?, ?)";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, account.getAccNo().trim());
			pStatement.setString(2, account.getRegNo());
			pStatement.setString(3, account.getName());
			pStatement.setDouble(4, account.getBalance());
			pStatement.setInt(5, account.getTransferLimit());
			pStatement.setInt(6, account.getCreditLimit());
			pStatement.executeUpdate();
			System.out.println("Account added to Database.");
			return "Account added!";
		} catch (SQLException e) {

			e.printStackTrace();
			if(e.getErrorCode()==23505){
				return "Account already exists!";
			}
			else{
				return e.getMessage();
			}
		}
		finally {
			closeConnection();
		}
	}


	public void deleteAccount(Account account) {
		// TODO Auto-generated method stub
		sql = "DELETE FROM onlinebankingsystem.account"
				+ " WHERE crp_no= ?";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, account.getAccNo().trim());
			pStatement.executeUpdate();
			System.out.println("Account deleted from Database.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
	}


	public void updateAccount(Account account) {
		// TODO Auto-generated method stub
		sql = "UPDATE onlinebankingsystem.Account set accName= ? "
					+ " WHERE accNo= ?";
		System.out.println("Account updated to Database.");
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, account.getName());
			pStatement.setString(2, account.getAccNo().trim());
			pStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
	}


	public String assignAccountToCustomer(String cprNo, String accNo, String regNo) {
		// TODO Auto-generated method stub
		sql = "INSERT INTO onlinebankingsystem.customer_account (cpr_No, accNo, regno)"
				+ "VALUES (?, ?, ?)";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, cprNo);
			pStatement.setString(2, accNo);
			pStatement.setString(3, regNo);
			pStatement.executeUpdate();
			System.out.println("Account successfully added to customer");
			return "Account successfully added to customer";
		} catch (SQLException e) {
			e.printStackTrace();
			if(e.getErrorCode()==23505){
				return "Account already assign to the customer";
			}
			else{
				return e.getMessage();
			}
		}
		finally {
			closeConnection();
		}
	}


	public void updateAccountBalance(String accNo, double balance) {
		// TODO Auto-generated method stub
		sql = "Update onlinebankingsystem.Account set balance = balance+? "
				+ " WHERE accno= ?";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setDouble(1, balance);
			pStatement.setString(2, accNo);
			pStatement.executeUpdate();
			System.out.println("Balance updated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
	}


	public boolean checkAccount(String accNo) {
		// TODO Auto-generated method stub
		boolean result = false;
		sql="SELECT * FROM onlinebankingsystem.Account"
				+ " WHERE accno= ?";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, accNo);
			resultSet = pStatement.executeQuery();
			while(resultSet.next())
			{
					result = true;
					break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return result;
	}


	public ArrayList<Account> getCustomerAcounts(String cprNo) {
		// TODO Auto-generated method stub
		ArrayList<Account> accounts = new ArrayList<Account>();
		sql="SELECT customer_account.cpr_no, account.accno, account.regno, account.accname, account.balance, account.transferlimit, account.creditlimit" +
				" FROM onlinebankingsystem.account" +
				" INNER JOIN onlinebankingsystem.customer_account" +
				" ON customer_account.accno=account.accno AND customer_account.regno=account.regno AND customer_account.cpr_no = ?";

		try
		{
				System.out.println("getting account info");
				openConnection();
				pStatement = connection.prepareStatement(sql);
				pStatement.setString(1, cprNo);
				resultSet = pStatement.executeQuery();
				while(resultSet.next())
				{
						Account account = new Account();

						account.setAccNo(resultSet.getString("accno"));
						account.setBalance(resultSet.getDouble("balance"));
						account.setCreditLimit(resultSet.getInt("creditLimit"));
						account.setName(resultSet.getString("accname"));
						account.setRegNo(resultSet.getString("regno"));
						account.setTransferLimit(resultSet.getInt("transferlimit"));

						accounts.add(account);
						System.out.println(account.toString());
				}
			}

		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return accounts;
	}

	public void addInterest() {
		// TODO Auto-generated method stub
		sql = "Update onlinebankingsystem.Account set balance = balance * 1.05";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.executeUpdate();
			System.out.println("Balance updated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
	}

}
