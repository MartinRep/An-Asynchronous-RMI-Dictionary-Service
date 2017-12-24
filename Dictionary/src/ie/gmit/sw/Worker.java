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
	private static ArrayBlockingQueue<Job> inQueue;
	private static ConcurrentHashMap<Integer, String> outQueue ;
	private DictionaryService dictionaryService;
	private Job job;
	Boolean succesfull = null;
	private static int workerNumber = 0;
	private int thisWorkerNumber;
	
	public Worker() 
	{
		super();
		inQueue = JobWorkerHandler.getInQueue();
		outQueue = JobWorkerHandler.getOutQueue();
		thisWorkerNumber = workerNumber;
		workerNumber++;
	}

	@Override
	public void run() 
	{	
		try {
			//takes job from the BlockingQueue
			job = inQueue.take();
			//dev only
			System.out.println("Job number: "+job.getJobNumber());
			System.out.println("Word: "+ job.getWord());
			System.out.println("Job Type: " + job.getJobType());
			System.out.println("definition: " + job.getDefinition());
			System.out.println("Worker Number: " + thisWorkerNumber);
			//Gets the results from remote server
			switch(job.getJobType())
			{
				case GET:
					String result = RMILookup(job.getWord());
					//dev only
					System.out.println(result);
					//Output the results into outQueue HashMap
					outQueue.put(job.getJobNumber(),result);
					break;
					
				case ADD:
					succesfull = RMIAddDefinition(job.getWord(), job.getDefinition());
					//dev only
					System.out.println(succesfull);
					//Output the results into outQueue HashMap
					if(succesfull != null) outQueue.put(job.getJobNumber(), succesfull.toString());
					break;
					
				case MODIFY:
					succesfull = RMIModifyDefinition(job.getWord(), job.getDefinition());
					//dev only
					System.out.println(succesfull);
					//Output the results into outQueue HashMap
					if(succesfull != null) outQueue.put(job.getJobNumber(), succesfull.toString());
					break;
				
				case DELETE:
					succesfull = RMIDeleteDefinition(job.getWord());
					//dev only
					System.out.println(succesfull);
					//Output the results into outQueue HashMap
					if(succesfull != null) outQueue.put(job.getJobNumber(), succesfull.toString());
					break;
			}
			//devOnly
			System.out.println("Workers map size: " + outQueue.size());
		} catch (MalformedURLException | RemoteException | NotBoundException | InterruptedException e) {
			System.out.printf("Job number: %d with Method: %s caused Exception: %s", job.getJobNumber(), job.getJobType(), e.toString());
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
