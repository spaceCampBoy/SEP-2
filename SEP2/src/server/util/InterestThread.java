package server.util;

import server.IDBAdapter;

public class InterestThread extends Thread{
	
private IDBAdapter dbAdapter;
	
	public InterestThread(IDBAdapter dbAdapter)
	{
		this.dbAdapter = dbAdapter;
	}
	
	public void run() {
		while(true){
				dbAdapter.addInterest();
			delay();
		}
	}
	
	private void delay()
	{
		try {
			Thread.sleep(604800000); // Sleep for a week
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
