package com.gmit.ie;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class JobWorkerHandler 
{
	private static JobWorkerHandler instance = null;
	private static ArrayBlockingQueue<Job> inQueue = new ArrayBlockingQueue<>(10);
	private static ConcurrentHashMap<Integer, String> outQueue = new ConcurrentHashMap<>(); 
	
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
		}
	}
	
	public static ArrayBlockingQueue<Job> getInQueue() {
		return inQueue;
	}

	public static ConcurrentHashMap<Integer, String> getOutQueue() {
		return outQueue;
	}
}
