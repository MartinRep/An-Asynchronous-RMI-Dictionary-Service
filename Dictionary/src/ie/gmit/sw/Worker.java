package ie.gmit.sw;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Worker for ThreadPool. Takes care of jobs. Take them from inQueue and the insert results into outQueue hashMap
 */

public class Worker implements Runnable
{
	private ArrayBlockingQueue<Job> inQueue;
	private ConcurrentHashMap<Integer, String> outQueue;
	private DictionaryService dictionaryService;
	private Job job;
	Boolean succesfull = null;
	
	public Worker() 
	{
		super();
	}
	

	@Override
	public void run() 
	{	
		while(true)
		{
		try {
			this.inQueue = JobWorkerHandler.getInQueue();
			this.outQueue = JobWorkerHandler.getOutQueue();
			//takes job from the BlockingQueue
			job = inQueue.take();
			//dev only
			System.out.println("Job number: "+job.getJobNumber());
			System.out.println("Word: "+ job.getWord());
			System.out.println("definition: " + job.getDefinition());
			System.out.println("Job Type: " + job.getJobType());
			//Gets the results from remote server
			switch(job.getJobType())
			{
				case GET:
					String result = RMILookup(job.getWord());
					//dev only
					System.out.println(result);
					//Output the results into outQueue HashMap
					outQueue.put(job.getJobNumber(),result);
					//devOnly
					System.out.println("Workers map size: " + outQueue.size());
					break;
					
				case ADD:
					succesfull = RMIAddDefinition(job.getWord(), job.getDefinition());
					//dev only
					System.out.println(succesfull);
					//Output the results into outQueue HashMap
					if(succesfull != null) outQueue.put(job.getJobNumber(), succesfull.toString());
					//devOnly
					System.out.println("Workers map size: " + outQueue.size());
					break;
					
				case MODIFY:
					succesfull = RMIModifyDefinition(job.getWord(), job.getDefinition());
					//dev only
					System.out.println(succesfull);
					//Output the results into outQueue HashMap
					if(succesfull != null) outQueue.put(job.getJobNumber(), succesfull.toString());
					//devOnly
					System.out.println("Workers map size: " + outQueue.size());
					break;
				
				case DELETE:
					succesfull = RMIDeleteDefinition(job.getWord());
					//dev only
					System.out.println(succesfull);
					//Output the results into outQueue HashMap
					if(succesfull != null) outQueue.put(job.getJobNumber(), succesfull.toString());
					//devOnly
					System.out.println("Workers map size: " + outQueue.size());
					break;
			}
		} catch (MalformedURLException | RemoteException | NotBoundException | InterruptedException e) {
			System.out.printf("Job number: %d with Method: %s caused Exception: %s", job.getJobNumber(), job.getJobType(), e.toString());
		}
		}
	}
	//Actual RMI calling method.
	public String RMILookup(String word) throws MalformedURLException, RemoteException, NotBoundException
	{
		dictionaryService = (DictionaryService) Naming.lookup("rmi://localhost:1099/dictionaryServer");
		return dictionaryService.Lookup(word);
	}
	
	public Boolean RMIAddDefinition(String word, String definition) throws MalformedURLException, RemoteException, NotBoundException
	{
		dictionaryService = (DictionaryService) Naming.lookup("rmi://localhost:1099/dictionaryServer");
		return dictionaryService.AddDefinition(word, definition);
	}
	
	public Boolean RMIModifyDefinition(String word, String definition) throws MalformedURLException, RemoteException, NotBoundException
	{
		dictionaryService = (DictionaryService) Naming.lookup("rmi://localhost:1099/dictionaryServer");
		return dictionaryService.ModifyDefinition(word, definition);
	}
	
	public Boolean RMIDeleteDefinition(String word) throws MalformedURLException, RemoteException, NotBoundException
	{
		dictionaryService = (DictionaryService) Naming.lookup("rmi://localhost:1099/dictionaryServer");
		return dictionaryService.DeleteDefinition(word);
	}
	
}
