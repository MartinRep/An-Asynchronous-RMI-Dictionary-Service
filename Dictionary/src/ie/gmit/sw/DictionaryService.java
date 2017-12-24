package ie.gmit.sw;

// Custom remote interface
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DictionaryService extends Remote 
{
	public String Lookup(String s) throws RemoteException;
	
	public Boolean AddDefinition(String word, String definition) throws RemoteException;
	
	public Boolean ModifyDefinition(String word, String newDefinition) throws RemoteException;
	
	public Boolean DeleteDefinition(String word) throws RemoteException;
}
