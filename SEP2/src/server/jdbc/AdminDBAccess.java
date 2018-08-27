package server.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import shared.Admin;
import shared.DateUtil;

public class AdminDBAccess {

	private Connection connection;
	private PreparedStatement pStatement;
	private String sql, driver, url, user, pw;
	private ResultSet resultSet;

	public AdminDBAccess()
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


	public String createAdmin(Admin admin) {
		// TODO Auto-generated method stub
		sql = "INSERT INTO onlinebankingsystem.admin (CPR_No)"
				+ "values (?)";
			try {
				openConnection();
				pStatement = connection.prepareStatement(sql);
				pStatement.setString(1, admin.getCPRNo().trim());
				pStatement.executeUpdate();
				System.out.println("Admin User added to Database.");
				return "Admin User added";
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


	public void deleteAdmin(Admin admin) {
		// TODO Auto-generated method stub
		sql = "DELETE FROM onlinebankingsystem.admin where crp_no= ? ";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, admin.getCPRNo().trim());
			pStatement.executeUpdate();
			System.out.println("Admin User deleted from Database.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeStatementConnection();
		}
	}


	public void updateAdmin(Admin admin) {
		/* TODO Auto-generated method stub
		sql = "UPDATE onlinebankingsystem.admin set fname= '"+admin.getfName()+"', lname= '"+admin.getlName()
		+ "',dob= DATE'"+DateUtil.dateForDB(admin.getBirthday())+"', gender= '"+admin.getGender()+"', address= '"+admin.getAddress()+"', postal_Code= "+admin.getPostalCode()
		+", city= '"+admin.getCity()+"', country= '"+admin.getCountry()+"', phoneno='"+admin.getPhoneNumber()+"', email='"+admin.getEmail()+"', nationality='"+admin.getNationality()
				+ "', empNo="+admin.getEmpNo()
						+ "WHERE cpr_no ='"+admin.getCPRNo()+"',";
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Admin User updated to Database.");
		*/
	}


	public Admin getAdminInfo(String cprNo, Admin admin) {
		// TODO Auto-generated method stub
		sql=("SELECT person.*, admin.empno" +
				" FROM onlinebankingsystem.person" +
				" INNER JOIN onlinebankingsystem.admin" +
				" ON person.cpr_no = admin.cpr_no AND admin.cpr_no = ?");
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, cprNo);
			resultSet = pStatement.executeQuery();
			while(resultSet.next())
			{
					admin.setCPRNo(resultSet.getString("cpr_no"));
					admin.setfName(resultSet.getString("fname"));
					admin.setlName(resultSet.getString("lname"));
					admin.setBirthday(DateUtil.dateFromDB(resultSet.getString("dob")));
					admin.setGender(resultSet.getString("gender").charAt(0));
					admin.setAddress(resultSet.getString("address"));
					admin.setPhoneNumber(resultSet.getString("phoneno"));
					admin.setPosteCode(resultSet.getInt("postal_code"));
					admin.setCity(resultSet.getString("city"));
					admin.setCountry(resultSet.getString("country"));
					admin.setEmail(resultSet.getString("email"));
					admin.setNationality(resultSet.getString("nationality"));
					admin.setEmpNo(resultSet.getInt("empno"));

					System.out.println(admin.toString());
					return admin;
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

}
