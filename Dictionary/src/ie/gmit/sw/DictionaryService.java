package ie.gmit.sw;

// Custom remote interface
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DictionaryService extends Remote 
{
	public String Lookup(String s) throws RemoteException;
}
