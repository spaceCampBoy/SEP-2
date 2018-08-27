package server.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import shared.Account;
import shared.Customer;
import shared.DateUtil;
import shared.Transaction;

public class TransactionDBAccess{


	private Connection connection;
	private PreparedStatement pStatement;
	private String sql, driver, url, user, pw;
	private ResultSet resultSet;

	public TransactionDBAccess()
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



	public synchronized void insertTransfer(String accNo1, String regNo1, Transaction trans) {
		// TODO Auto-generated method stub
		sql = "INSERT INTO onlinebankingsystem.transaction (accNo1, regNo1, accNo2, regNo2, description, date, money_transfered)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
				try {
					openConnection();
					pStatement = connection.prepareStatement(sql);
					pStatement.setString(1, accNo1);
					pStatement.setString(2, regNo1);
					pStatement.setString(3, trans.getTransactionAccNo());
					pStatement.setString(4, trans.getRegNo());
					pStatement.setString(5, trans.getDescription());
					pStatement.setDate(6, Date.valueOf(trans.getTransferDate()));
					pStatement.setDouble(7, trans.getAmount());
					pStatement.executeUpdate();
					System.out.println("Transaction added to Database.");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					closeStatementConnection();
				}
	}
	public void insertToBeTransferred(String accNo1, String regNo1, Transaction trans) {
		// TODO Auto-generated method stub
		sql = "INSERT INTO onlinebankingsystem.tobe_Transfered (accNo1, regNo1, accNo2, regNo2, description, date, money_transfered)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, accNo1);
			pStatement.setString(2, regNo1);
			pStatement.setString(3, trans.getTransactionAccNo());
			pStatement.setString(4, trans.getRegNo());
			pStatement.setString(5, trans.getDescription());
			pStatement.setDate(6, Date.valueOf(trans.getTransferDate()));
			pStatement.setDouble(7, trans.getAmount());
			pStatement.executeUpdate();
			System.out.println("Transaction added to Database.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeStatementConnection();
		}
	}


	public void insertSavedTransfer(String cprNo, Transaction trans) {
		// TODO Auto-generated method stub
		sql = "INSERT INTO onlinebankingsystem.saved_transaction (cpr_no, accNo, regNo, description, date, money_transfered)"
				+ " VALUES (?, ?, ?, ?, ?, ?)";
				System.out.println("Account updated to Database.");
				try {
					openConnection();
					pStatement = connection.prepareStatement(sql);
					pStatement.setString(1, cprNo);
					pStatement.setString(2, trans.getTransactionAccNo());
					pStatement.setString(3, trans.getRegNo());
					pStatement.setString(4, trans.getDescription());
					pStatement.setDate(5, Date.valueOf(trans.getTransferDate()));
					pStatement.setDouble(6, trans.getAmount());
					pStatement.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					closeStatementConnection();
				}
	}


	public void deleteSavedTransaction(String cprNo, Transaction transaction) {
		// TODO Auto-generated method stub
		sql = "DELETE FROM onlinebankingsystem.saved_transaction where cpr_no=? AND accno=? AND regNo=?";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, cprNo);
			pStatement.setString(2, transaction.getTransactionAccNo());
			pStatement.setString(3, transaction.getRegNo());
			pStatement.executeUpdate();
			System.out.println("Saved transaction deleted from Database.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeStatementConnection();
		}
	}

	public ArrayList<Transaction> getAccountTransactions(Account account)
	{
		System.out.println("Getting transaction history");

		ArrayList<Transaction> transactions = new ArrayList<Transaction>();

		sql=("SELECT * FROM onlinebankingsystem.transaction");
		try
		{
			openConnection();
			pStatement = connection.prepareStatement(sql);
			resultSet = pStatement.executeQuery();
				while(resultSet.next())
				{
					if((account.getAccNo().trim().equalsIgnoreCase(resultSet.getString("accno1").trim())) &&
							(account.getRegNo().equalsIgnoreCase(resultSet.getString("regno1"))))
					{
						System.out.println("Transaction found");
						Transaction transaction = new Transaction();

						transaction.setTransactionAccNo(resultSet.getString("accno2"));
						transaction.setRegNo(resultSet.getString("regno2"));
						transaction.setDescription(resultSet.getString("description"));
						transaction.setTransferDate(DateUtil.dateFromDB(resultSet.getString("date")));
						transaction.setAmount(-(resultSet.getDouble("money_transfered")));

						transactions.add(transaction);
						System.out.println(account.toString());

					}
					else if((account.getAccNo().trim().equalsIgnoreCase(resultSet.getString("accno2").trim())) &&
							(account.getRegNo().equalsIgnoreCase(resultSet.getString("regno2"))))
					{
						System.out.println("Transaction found");
						Transaction transaction = new Transaction();

						transaction.setTransactionAccNo(resultSet.getString("accno1"));
						transaction.setRegNo(resultSet.getString("regno1"));
						transaction.setDescription(resultSet.getString("description"));
						transaction.setTransferDate(DateUtil.dateFromDB(resultSet.getString("date")));
						transaction.setAmount((resultSet.getDouble("money_transfered")));

						transactions.add(transaction);
						System.out.println(account.toString());

					}

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

		return transactions;
	}


	public void setSavedTransactionsForCustomer(Customer customer)
	{
		System.out.println("Checking For saved transaction For customer");

		sql="SELECT * FROM onlinebankingsystem.saved_transaction"
				+ " WHERE cpr_no=?";

		ResultSet resultSet;
		try
		{	openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, customer.getCPRNo());
			resultSet = pStatement.executeQuery();
				while(resultSet.next())
				{
						System.out.println("Saved Transaction found");
						Transaction transaction = new Transaction();

						transaction.setTransactionAccNo(resultSet.getString("accno"));
						transaction.setRegNo(resultSet.getString("regno"));
						transaction.setDescription(resultSet.getString("description"));
						transaction.setTransferDate(DateUtil.dateFromDB(resultSet.getString("date")));
						transaction.setAmount((resultSet.getDouble("money_transfered")));

						customer.addTransaction(transaction);
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
	
	public int checkToBeTransferred()
	{
		int count = 0;
		sql=("SELECT * FROM onlinebankingsystem.transaction"
				+ " WHERE date = CURRENT_DATE");
		try
		{
			openConnection();
			pStatement = connection.prepareStatement(sql);
			resultSet = pStatement.executeQuery();
				while(resultSet.next())
				{
						System.out.println("Transaction found");
						Transaction transaction = new Transaction();
						
						transaction.setTransactionAccNo(resultSet.getString("accno2"));
						transaction.setRegNo(resultSet.getString("regno2"));
						transaction.setDescription(resultSet.getString("description"));
						transaction.setTransferDate(DateUtil.dateFromDB(resultSet.getString("date")));
						transaction.setAmount(-(resultSet.getDouble("money_transfered")));
						
						insertTransfer(resultSet.getString("accno1"),resultSet.getString("regno1"), transaction);
						deleteToBeTransferred(resultSet.getInt("transferid"));
						
						count++;
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

		return count;
	}
	
	private void deleteToBeTransferred(int transferid) {
		// TODO Auto-generated method stub
		sql = "DELETE FROM onlinebankingsystem.saved_transaction"
				+ " where transferid=?";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setInt(1, transferid);
			pStatement.executeUpdate();
			System.out.println("Saved transaction deleted from Database.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			closeStatementConnection();
		}
	}
	
}
