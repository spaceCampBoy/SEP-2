package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface IServer extends Remote {
	void addObjectToDatabase(Object obj) throws RemoteException;
	void deleteObjectFromDatabase(String cprNo, Object obj) throws RemoteException;
	void changeObjectInDatabase(Object obj) throws RemoteException;
	ArrayList<Customer> getAllCustomerWithLastName(String lastName) throws RemoteException;
	String logIn(String username, String password) throws RemoteException;
	Object checkValue(String username, String password, String key, String value, Object obj) throws RemoteException;
	int transferMoney(Account acc, String cprNo,Transaction trans, boolean saveTransfer) throws RemoteException;
	void assignAccountToCustomer(String cprNo, String accNo, String regNo) throws RemoteException;
	Customer getCustomerAllInfo(String cprNo) throws RemoteException;
}


