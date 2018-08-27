package server;

import java.util.ArrayList;

import server.jdbc.*;
import server.util.NemID;
import shared.*;

public class DBAdapter implements IDBAdapter {
	private AccountDBAccess accDB;
	private AdminDBAccess adminDB;
	private CustomerDBAccess cusDB;
	private PersonDBAccess personDB;
	private TransactionDBAccess transDB;
	private NemIDDBAccess nemDB;
	
	public DBAdapter() {
		accDB = new AccountDBAccess();
		adminDB = new AdminDBAccess();
		cusDB = new CustomerDBAccess();
		personDB = new PersonDBAccess();
		transDB = new TransactionDBAccess();
		nemDB = new NemIDDBAccess();

	}

	@Override
	public void add(Object obj) {
		if (obj instanceof Admin) {
			Admin admin = (Admin) obj;
			if(!(personDB.existPerson((Person)admin)))
			{
				personDB.insertPerson((Person)admin);
			}
			adminDB.createAdmin(admin);
			handleNemID(admin);
		} else if (obj instanceof Customer) {
			Customer customer = (Customer) obj;
			if(!(personDB.existPerson((Person)customer)))
			{
				personDB.insertPerson((Person)customer);
			}
			cusDB.createCustomer(customer);
			handleNemID(customer);
		} else if (obj instanceof Account) {
			Account account = (Account) obj;
			accDB.createAccount(account);
		} else {
			System.out.println("Object not competible..");
		}

	}

	@Override
	public void delete(String cprNo, Object obj) {
		if (obj instanceof Admin) {
			Admin admin = (Admin) obj;
			adminDB.deleteAdmin(admin);
		} else if (obj instanceof Customer) {
			Customer customer = (Customer) obj;
			cusDB.deleteCustomer(customer);
		} else if (obj instanceof Account) {
			Account account = (Account) obj;
			accDB.deleteAccount(account);
		} else if(obj instanceof Transaction) {
			Transaction transaction = (Transaction) obj;
			transDB.deleteSavedTransaction(cprNo, transaction);

		}else {
			System.out.println("Object not competible..");
		}
	}

	@Override
	public void update(Object obj) {
		if (obj instanceof Admin) {
			Admin admin = (Admin) obj;
			adminDB.updateAdmin(admin);
		} else if (obj instanceof Customer) {
			Customer customer = (Customer) obj;
			cusDB.updateCustomer(customer);
		} else if (obj instanceof Account) {
			Account account = (Account) obj;
			accDB.updateAccount(account);
		} else {
			System.out.println("Object not competible..");
		}

	}

	@Override
	public void assignAccountToCustomer(String cprNo, String accNo, String regNo) {
		accDB.assignAccountToCustomer(cprNo, accNo, regNo);
	}

	@Override
	public String login(String username, String password) {
		// TODO Auto-generated method stub
		String cprNo = nemDB.getCPRFromUserCredentials(username, password);
		if (cprNo.equals(null)) {
			return null;
		}

		String key = nemDB.getKeyFromNemID(cprNo);
		return key;
	}


	@Override
	public Object checkValue(String username, String password, String key, String value, Object obj) {
		Object object = null;
		// TODO Auto-generated method stub
		System.out.println("get cprNo");
		String cprNo = nemDB.getCPRFromUserCredentials(username, password);

		System.out.println("cpr " + cprNo + " checking key and value");
		boolean check = nemDB.checkNemIDKeyValue(cprNo, key, value);

		System.out.println("if statement for checked key and value" + check);
		if (check == true)
		{
			if (obj instanceof Admin)
			{
				System.out.println("Admin object to return");
				// getting admin information( if no admin is found then null is returned)
				object = adminDB.getAdminInfo(cprNo, (Admin) obj); 

			}
			if (obj instanceof Customer)
			{
				System.out.println("Customer object to return");
				object = cusDB.getCustomerInfo(cprNo);

				if (object == null)
				{
					return null;
				}
				// getting arraylist of accounts
				ArrayList<Account> accounts = accDB.getCustomerAcounts(cprNo); 

				System.out.println("Populating accounts with account transactions and adding them to customer");
				for (int i = 0; i < accounts.size(); i ++)
				{
					accounts.get(i).setTransactions(transDB.getAccountTransactions(accounts.get(i)));
					// adding every customer info to account, who shares it
					cusDB.setSharedAccountCustomers(((Customer)object).getCPRNo(), accounts.get(i)); 
					System.out.println(accounts.get(i).toString());
					// adding account to customer
					
					((Customer) object).addAccount(accounts.get(i));
					System.out.println("Account added to customer");
				}
			transDB.setSavedTransactionsForCustomer(((Customer)object)); // adding savedTransaction to customer

			}
			else
			{
				System.out.println("object not recognized");
				return null;
			}
		}
		else
		{
			System.out.println("Value did not match");
			return null;
		}
		System.out.println(object.toString());
		return object;
	}

	private void handleNemID(Object obj) {
		boolean haveNemID = false;
		String[][] nemID = null;
		String password = "";
		if (obj instanceof Admin) {
			Admin temp = (Admin) obj;

			haveNemID = nemDB.checkNemID(temp.getCPRNo());
			if (!haveNemID) {
				nemID = NemID.generateKeysValues();
				password = temp.getlName();
				nemDB.storeNemID(temp.getCPRNo(), nemID);
				nemDB.storeUserCredentials(temp.getCPRNo(), temp.getEmail(), password);
			}
			sendUserCredentials(temp.getEmail(), password, nemID, temp.getEmail());
		} else {
			Customer temp = (Customer) obj;

			haveNemID = nemDB.checkNemID(temp.getCPRNo());
			if (!haveNemID) {

				nemID = NemID.generateKeysValues();
				password = temp.getlName();
				nemDB.storeNemID(temp.getCPRNo(), nemID);
				nemDB.storeUserCredentials(temp.getCPRNo(), temp.getEmail(), password);
			}
			sendUserCredentials(temp.getEmail(), password, nemID, temp.getEmail());

		}

	}

	private void sendUserCredentials(String username, String password, String[][] nemID, String receiverEmailID) {
		String subject = "Congratulations - Online Banking System";

		String body = "Username : " + username + " password : " + password;

		if (nemID != null) {
			body += "\n---------------- NemID ---------------- \n";
			for (int i = 0; i < nemID.length - 1; i++) {
				body += nemID[i][0] + "   " + nemID[i][1] + "              " + nemID[++i][0] + "   " + nemID[i][1]
						+ "\n";
			}
		}
		new SendEmail(receiverEmailID, subject, body);
	}

	public int transferMoney(Account acc, String cprNo,Transaction trans, boolean saveTransaction) {
		System.out.println("checking balance, credit limit and transfer limit of account : "+acc.getAccNo());
		if ((acc.getBalance() + acc.getCreditLimit()) > trans.getAmount() && trans.getAmount() < acc.getTransferLimit())
		{

			if(acc.getAccNo().trim().equalsIgnoreCase(trans.getTransactionAccNo()) && acc.getRegNo().trim().equalsIgnoreCase(trans.getRegNo()))
			{
				return 3;
			}
			System.out.println("checking receiver's Account : "+ trans.getTransactionAccNo());
			if (accDB.checkAccount(trans.getTransactionAccNo()))
			{
				System.out.println("Account : " + trans.getTransactionAccNo() + " exists, sending money");
				accDB.updateAccountBalance(acc.getAccNo(), -trans.getAmount());
				accDB.updateAccountBalance(trans.getTransactionAccNo(), trans.getAmount());
				transDB.insertTransfer(acc.getAccNo(), acc.getRegNo(), trans);
				System.out.println("Transfer successful");
				if(saveTransaction)
				{
					transDB.insertSavedTransfer(cprNo, trans);
				}
				return 2;
			}

			else
			{
				System.out.println("Receiver's Account : "+ trans.getTransactionAccNo() +" does not exist");
				return 1;
			}
		}

		else
		{
			System.out.println("balance, credit limit or transfer limit of account : "+acc.getAccNo()+" is not sufficient");
			return 0;
		}

	}

	@Override
	public ArrayList<Customer> getAllCustomersWithLastName(String lastName) {
		// TODO Auto-generated method stub
		ArrayList<Customer> cusArray = cusDB.getAllCustomers(lastName);
		
		for(int j = 0; j < cusArray.size(); j++)
		{
			ArrayList<Account> accounts = accDB.getCustomerAcounts(cusArray.get(j).getCPRNo());

			System.out.println("Populating accounts with transactions and adding them to customer");
			for (int i = 0; i < accounts.size(); i ++)
			{
				accounts.get(i).setTransactions(transDB.getAccountTransactions(accounts.get(i)));
				cusDB.setSharedAccountCustomers(cusArray.get(j).getCPRNo(), accounts.get(i));
				System.out.println(accounts.get(i).toString());
				cusArray.get(j).addAccount(accounts.get(i));
				System.out.println("Account added to customer");
			}
		transDB.setSavedTransactionsForCustomer(cusArray.get(j)); // adding savedTransaction to customer
		}
		
		return cusArray;
		
	}

	@Override
	public Customer getCustomerInfo(String cprNo) {
		// TODO Auto-generated method stub
		Customer cus = cusDB.getCustomerInfo(cprNo);
		ArrayList<Account> accounts = accDB.getCustomerAcounts(cus.getCPRNo());

		System.out.println("Populating accounts with transactions and adding them to customer");
		for (int i = 0; i < accounts.size(); i ++)
		{
			accounts.get(i).setTransactions(transDB.getAccountTransactions(accounts.get(i)));
			cusDB.setSharedAccountCustomers(cus.getCPRNo(), accounts.get(i));
			System.out.println(accounts.get(i).toString());
			cus.addAccount(accounts.get(i));
			System.out.println("Account added to customer");
		}
		transDB.setSavedTransactionsForCustomer(cus); // adding savedTransaction to customer
	
		
		return cus;
	}

	@Override
	public void dailyTransactionCheck() {
		// TODO Auto-generated method stub
		int totalTrans = transDB.checkToBeTransferred();
		System.out.println(totalTrans + " Transaction(s) were processed from tobe_transferred");
	}

	@Override
	public void addInterest() {
		// TODO Auto-generated method stub
		accDB.addInterest();
	}
	
	

}
