package server.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NemIDDBAccess {

	private Connection connection;
	private PreparedStatement pStatement;
	private String sql, driver, url, user, pw;
	private ResultSet resultSet;

	public NemIDDBAccess()
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


	public void storeUserCredentials(String CPRNo, String userName, String password) {

		sql = "INSERT INTO onlinebankingsystem.user_credentials (cpr_no, user_name, password)"
				+ "VALUES (?,?,?)";
		System.out.println("User credentials updated to Database.");
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, CPRNo.trim());
			pStatement.setString(2, userName);
			pStatement.setString(3, password);
			pStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			closeConnection();
		}

	}


	public void storeNemID(String cprNo, String[][] nemID) {
		// TODO Auto-generated method stub
		sql = "INSERT INTO onlinebankingsystem.nem_id(cpr_no, key, value)"
				+ "VALUES (?,?,?)";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			for(int i = 0; i < nemID.length; i++)
			{
				pStatement.setString(1, cprNo);
				pStatement.setString(2, nemID[i][0]);
				pStatement.setString(3, nemID[i][1]);

				pStatement.executeUpdate();
			}

			System.out.println("New NemID stored into Database for "+ cprNo);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}

	}


	public String getCPRFromUserCredentials(String username, String password) {
		// TODO Auto-generated method stub
		String cprNo = "";
		sql=("SELECT * FROM onlinebankingsystem.user_credentials"
				+ " WHERE user_name = ? AND password = ?");
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, username);
			pStatement.setString(2, password);
			resultSet = pStatement.executeQuery();
			while(resultSet.next())
			{
					cprNo = resultSet.getString("cpr_no");
					System.out.println(cprNo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return cprNo;
	}


	public String getKeyFromNemID(String cprNo) {
		// TODO Auto-generated method stub
		String key = null;
		sql=("SELECT * FROM onlinebankingsystem.NEM_ID"
				+ " WHERE cpr_no = ?");
		int random = (int) Math.floor(Math.random() * 132);
		try {
			System.out.println("Getting a Key for cpr = "+cprNo);
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, cprNo);
			resultSet = pStatement.executeQuery();
			int count = 0;
			while(resultSet.next())
			{
				if(cprNo.equals(resultSet.getString("cpr_no")))
				{
					if(count > random){
						key = resultSet.getString(2);
						return key;
					}
					count++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return key;
	}


	public boolean checkNemIDKeyValue(String cprNo, String key, String value) {
		// TODO Auto-generated method stub`
		boolean result = false;
		sql=("SELECT * FROM onlinebankingsystem.NEM_ID"
				+ " WHERE cpr_no = ? AND key = ? AND value = ?");
		try {
			System.out.println("checking in database");
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, cprNo);
			pStatement.setString(2, key.trim());
			pStatement.setString(3, value.trim());
			resultSet = pStatement.executeQuery();
			while(resultSet.next())
			{
					System.out.println("Key and Value matched");
					result = true;
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



	public boolean checkNemID(String cprNo) {
		// TODO Auto-generated method stub
		boolean result = false;
		sql=("SELECT * FROM onlinebankingsystem.NEM_ID"
				+ " WHERE cpr_no = ?");
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, cprNo);
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

}
