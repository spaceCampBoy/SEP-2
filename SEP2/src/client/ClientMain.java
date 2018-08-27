package client;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;

import client.model.BankModel;
import client.model.IBankModel;
import shared.*;
import utility.observer.RemoteObserver;
import utility.observer.RemoteSubject;

/**
 *
 * @author SpaceCampBoy
 * ClientMain Class is responsible for getting the server interface(IServer) and calling methods on it
 */
public class ClientMain {

	private IServer server;
	private IBankModel model;

	/**
	 * An empty Constructor for ClientMain class that gets an object of server interface
	 * export itself using UnicastRemoteObject and add itself as an observer
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws NotBoundException
	 */
	public ClientMain() throws RemoteException, MalformedURLException, NotBoundException
	{
		//server = (IServer) Naming.lookup("rmi://10.152.206.74:1099/bankingServer");
		server = (IServer) Naming.lookup("rmi://localhost:1099/bankingServer");

		model = new BankModel();

		System.out.println("Client running");
	}

	public void addCustomer(Customer customer) throws RemoteException{
		server.addObjectToDatabase(customer);
	}

	public void addAdmin(Admin admin) throws RemoteException{
		server.addObjectToDatabase(admin);
	}

	public void newAccount(Account account, String cprNo) throws RemoteException{
		server.addObjectToDatabase(account);
		server.assignAccountToCustomer(cprNo, account.getAccNo(), account.getRegNo());
	}

	public void assignAccountToCustomer(String cprNo, String accNo, String regNo) throws RemoteException {
		server.assignAccountToCustomer(cprNo, accNo, regNo);
	}

	public String login(String userName, String password) throws RemoteException{
		return server.logIn(userName, password);
	}

	public int checkValue(String userName, String password, String key, String value, Object object) throws RemoteException{

			if(object instanceof Customer){
				Object obj = server.checkValue(userName, password, key, value, object);
				if(obj == null) return 1;
				saveCustomerData((Customer) obj);
				saveUsername(userName);
				return 2;
			}
			if(object instanceof Admin){
				Object obj = server.checkValue(userName, password, key, value, object);
				if(obj == null)	return 1;
				return 3;
			}
	  return -1;
	}

	public void changeCustomer(Customer customer) throws RemoteException{
		server.changeObjectInDatabase(customer);
	}

	public int transferMoney(Account account, String cprNo, Transaction transaction, boolean save) throws RemoteException {
		return server.transferMoney(account, cprNo, transaction, save);
	}

	private void saveUsername(String username){
		model.saveUsername(username);
	}

	private void saveCustomerData(Customer customer){
		model.saveCustomerData(customer);
		System.out.println(customer);
	}

	public Customer getCustomerData(){
		return model.getCustomerData();
	}

	public String getUsername(){
		return model.getUsername();
	}

	public void deleteCustomerData(){
		model.deleteCustomerData();
	}

	public void changeAccountName(Account account) throws RemoteException {
		server.changeObjectInDatabase(account);
	}

	public void deleteSavedTransaction(String cprNo, Transaction transaction) throws RemoteException{
		server.deleteObjectFromDatabase(cprNo, transaction);
	}

	public ArrayList<Customer> getCustomerWithLastname(String lastname) throws RemoteException {
		return server.getAllCustomerWithLastName(lastname);
	}

	public void updateCustomerData() throws RemoteException{
		//System.out.println(server.getCustomerAllInfo(model.getCustomerData().getCPRNo()));
		model.saveCustomerData(server.getCustomerAllInfo(model.getCustomerData().getCPRNo()));
	}
}
