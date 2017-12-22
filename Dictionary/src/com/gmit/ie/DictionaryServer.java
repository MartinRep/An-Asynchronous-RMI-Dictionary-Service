package com.gmit.ie;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class DictionaryServer 
{

	
	public static void main(String[] args) throws Exception {
	try {
			LocateRegistry.createRegistry(1099);
            DbController dbControler = new DbController();
            System.out.println("Server running...");
			Naming.rebind("dictionaryServer", dbControler);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	

}
