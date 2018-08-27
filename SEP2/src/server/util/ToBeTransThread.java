package server.util;

import server.IDBAdapter;

public class ToBeTransThread extends Thread{

private IDBAdapter dbAdapter;
	
	public ToBeTransThread(IDBAdapter dbAdapter)
	{
		this.dbAdapter = dbAdapter;
	}
	
	public void run() {
		while(true){
				dbAdapter.dailyTransactionCheck();
			delay();
		}
	}
	
	private void delay()
	{
		try {
			Thread.sleep(86400000); // Sleep for a day
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
