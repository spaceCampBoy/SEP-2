package server;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import server.util.InterestThread;
import server.util.ToBeTransThread;
import shared.*;

/**
 *
 * @author SpaceCampBoy
 *ServerMain class is responsible for running the server,
 *maintaining the client/server connection
 *and handling client requests
 */
public class ServerMain implements IServer {
	private IDBAdapter dbAdapter;
	private InterestThread iThread;
	private ToBeTransThread tThread;
	/**
	 * an empty constructor for ServerMain class
	 * that exports itself using UnicastRemoteObject
	 * @throws RemoteException
	 */
	public ServerMain() throws RemoteException
	{
		UnicastRemoteObject.exportObject(this, 0);
		dbAdapter = new DBAdapter();
		iThread = new InterestThread(dbAdapter);
		tThread = new ToBeTransThread(dbAdapter);
		iThread.start();
		tThread.start();
	}

	/**
	 * Main method for running the server
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
			LocateRegistry.createRegistry(1099);
			IServer server = new ServerMain();
			Naming.rebind("bankingServer", server);
			System.out.println("Starting server....");

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}


	@Override
	public synchronized void addObjectToDatabase(Object obj) {
		dbAdapter.add(obj);
	}

	@Override
	public synchronized void deleteObjectFromDatabase(String cprNo, Object obj) {
		dbAdapter.delete(cprNo, obj);
	}

	@Override
	public synchronized void changeObjectInDatabase(Object obj) {
		dbAdapter.update(obj);
	}

	@Override
	public synchronized String logIn(String username, String password) throws RemoteException {
		return dbAdapter.login(username, password);
	}

	@Override
	public synchronized Object checkValue(String username, String password, String key, String value, Object obj) throws RemoteException {
		System.out.println("Method checkValue called");
		 return dbAdapter.checkValue(username, password, key, value, obj);
	}

	@Override
	public synchronized void assignAccountToCustomer(String cprNo, String accNo, String regNo)
			throws RemoteException {
		dbAdapter.assignAccountToCustomer(cprNo, accNo, regNo);
	}

	@Override
	public synchronized int transferMoney(Account acc,String cprNo, Transaction trans, boolean saveTransfer) throws RemoteException {
		return dbAdapter.transferMoney(acc, cprNo, trans, saveTransfer);
	}

	@Override
	public synchronized ArrayList<Customer> getAllCustomerWithLastName(String lastName) throws RemoteException {
		return dbAdapter.getAllCustomersWithLastName(lastName);
	}

	@Override
	public synchronized Customer getCustomerAllInfo(String cprNo) throws RemoteException {
		// TODO Auto-generated method stub
		return dbAdapter.getCustomerInfo(cprNo);
	}


}