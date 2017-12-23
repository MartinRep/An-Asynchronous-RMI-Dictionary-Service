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
	private static DictionaryService dictionaryService;
	private Job job;
	
	public Worker(ArrayBlockingQueue<Job> inQueue, ConcurrentHashMap<Integer, String> outQueue) {
		super();
		this.inQueue = inQueue;
		this.outQueue = outQueue;
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
			//Gets the results from remote server
			String result = RMILookup(job.getWord());
			//dev only
			System.out.println(result);
			//Output the results into outQueue HashMap
			outQueue.put(job.getJobNumber(),result);
			//devOnly
			System.out.println("Workers map size: "+outQueue.size());
		} catch (MalformedURLException | RemoteException | NotBoundException | InterruptedException e) {
			System.out.printf("Job number: %d caused Exception: %s", job.getJobNumber(), e.toString());
		}	
	}
	//Actual RMI calling method.
	public String RMILookup(String word) throws MalformedURLException, RemoteException, NotBoundException
	{
		dictionaryService = (DictionaryService) Naming.lookup("rmi://localhost:1099/dictionaryServer");
		return dictionaryService.Lookup(word);
	}
}
