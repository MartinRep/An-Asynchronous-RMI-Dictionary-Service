package com.gmit.ie;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Worker implements Runnable
{
	private ArrayBlockingQueue<Job> inQueue;
	private ConcurrentHashMap<Integer, String> outQueue;
	
	public Worker(ArrayBlockingQueue<Job> inQueue, ConcurrentHashMap<Integer, String> outQueue) {
		super();
		this.inQueue = inQueue;
		this.outQueue = outQueue;
	}	

	@Override
	public void run() 
	{
		try {
			Job job = inQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Call RMI method
		// Output result to outQueue
	}
}
