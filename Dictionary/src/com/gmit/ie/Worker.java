package com.gmit.ie;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
			String result = RMILookup(job.getWord());
			outQueue.put(job.getJobNumber(),result);
		} catch (MalformedURLException | RemoteException | NotBoundException | InterruptedException e) {
			System.out.printf("Job number: %d caused Exception: %s", job.getJobNumber(), e.toString());
		}	
	}
	
	public String RMILookup(String word) throws MalformedURLException, RemoteException, NotBoundException
	{
		dictionaryService = (DictionaryService) Naming.lookup("//localhost/Dictionary");
		return dictionaryService.lookup(word);
	}
}
