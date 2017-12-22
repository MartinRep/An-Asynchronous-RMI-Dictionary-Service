package com.gmit.ie;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class DictionaryServer 
{

	
	public static void main(String[] args) throws Exception {
		//System.setProperty("java.security.policy", "policy.all");
//		if (System.getSecurityManager() == null) {
//				System.setSecurityManager(new SecurityManager());
//		    }
	try {
			//DictionaryService engine = new DictionaryServer();
            //DictionaryService stub =   (DictionaryService) UnicastRemoteObject.exportObject(engine, 1099);
            //Registry registry = LocateRegistry.getRegistry();
			LocateRegistry.createRegistry(1099);
            DbService dbService = new DbService();
            //registry.rebind("dictionaryServer", dbService);
            System.out.println("Server running...");
			Naming.rebind("dictionaryServer", dbService);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

}
