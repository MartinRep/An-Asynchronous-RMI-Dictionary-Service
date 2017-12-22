package com.gmit.ie;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DbService extends UnicastRemoteObject implements DictionaryService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public DbService() throws RemoteException {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public String lookup(String s) throws RemoteException {
		String tmp = "TEST";
		return tmp;
	}

}
