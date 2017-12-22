package com.gmit.ie;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

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
			job = inQueue.take();
			System.out.println("Job number: "+job.getJobNumber());
			System.out.println("Word: "+ job.getWord());
			String result = RMILookup(job.getWord());
			System.out.println(result);
			outQueue.put(job.getJobNumber(),result);
			System.out.println("Workers map size: "+outQueue.size());
		} catch (MalformedURLException | RemoteException | NotBoundException | InterruptedException e) {
			System.out.printf("Job number: %d caused Exception: %s", job.getJobNumber(), e.toString());
		}	
	}
	
	public String RMILookup(String word) throws MalformedURLException, RemoteException, NotBoundException
	{
		dictionaryService = (DictionaryService) Naming.lookup("rmi://localhost:1099/dictionaryServer");
		return dictionaryService.lookup(word);
	}
}
