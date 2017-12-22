package com.gmit.ie;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DbController extends UnicastRemoteObject implements DictionaryService{

	private static final long serialVersionUID = 2L;

	public DbController() throws RemoteException {
		super();
	}

	@Override
	public String Lookup(String s) throws RemoteException {
		DbService dbService = new DbService();
		String result = dbService.getDefinition(s);
		return result;
	}
	

}
