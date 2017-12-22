package com.gmit.ie;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JobWorkerHandler 
{
	private static JobWorkerHandler instance = null;
	private static ArrayBlockingQueue<Job> inQueue = new ArrayBlockingQueue<>(10);
	private static ConcurrentHashMap<Integer, String> outQueue = new ConcurrentHashMap<>();
	private static ExecutorService executor;
	
	private JobWorkerHandler(ArrayBlockingQueue<Job> inQueue, ConcurrentHashMap<Integer, String> outQueue)
	{
		JobWorkerHandler.inQueue = inQueue;
		JobWorkerHandler.outQueue = outQueue;
	}
	
	public static void init(ArrayBlockingQueue<Job> inQueue, ConcurrentHashMap<Integer, String> outQueue)
	{
		if(instance == null)
		{
			instance = new JobWorkerHandler(inQueue, outQueue);
			//also initialize actual workers 10 or so.
			executor = Executors.newFixedThreadPool(10);
			for (int i = 0; i < 10; i++) 
			{
				Runnable worker = new Worker(inQueue, outQueue);
				executor.execute(worker);
			}
		}
	}
	
	public static ArrayBlockingQueue<Job> getInQueue() {
		return inQueue;
	}

	public static ConcurrentHashMap<Integer, String> getOutQueue() {
		return outQueue;
	}
	
	public static void Shutdown()
	{
		executor.shutdown();
	}
}
