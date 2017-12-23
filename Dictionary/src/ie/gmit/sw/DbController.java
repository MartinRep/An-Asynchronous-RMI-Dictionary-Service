package ie.gmit.sw;

/*
 * This class controls access to the virtual Db. Implements Custom remote interface. It's called through RMI 
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DbController extends UnicastRemoteObject implements DictionaryService{

	private static final long serialVersionUID = 2L;

	public DbController() throws RemoteException {
		super();
	}

	@Override
	public String Lookup(String s) throws RemoteException {
		// All the words in dictionary are all Capital.
		String word = s.toUpperCase();
		DbService dbService = new DbService();
		String result = dbService.getDefinition(word);		
		// testing only Delay response by 10 second
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
