package rmi.server;

/*
 * This class controls access to the virtual Db. Implements Custom remote interface. It's called through RMI 
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ie.gmit.sw.DictionaryService;

public class DbController extends UnicastRemoteObject implements DictionaryService{

	private static final long serialVersionUID = 2L;
	
	private static FileService fileService = new FileService();

	public DbController() throws RemoteException {
		super();
	}

	@Override
	public String Lookup(String s) throws RemoteException {
		// All the words in dictionary are all Capital.
		String word = s.toUpperCase();
		String result = fileService.getDefinition(word);		
		// testing only Delay response by 10 second
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean AddDefinition(String word, String definition) throws RemoteException {
		word = word.toUpperCase();
		System.out.println("Word: " + word + " Definition: "+ definition);
		return fileService.AddDefinition(word, definition);
	}

	@Override
	public Boolean ModifyDefinition(String word, String newDefinition) throws RemoteException {
		word = word.toUpperCase();
		return fileService.ModifyDefinition(word, newDefinition);
	}

	@Override
	public Boolean DeleteDefinition(String word) throws RemoteException {
		word = word.toUpperCase();
		return fileService.DeleteDefinition(word);
	}
	

}
