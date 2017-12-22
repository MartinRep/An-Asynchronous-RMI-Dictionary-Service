package com.gmit.ie;

import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable
{
	private BlockingQueue<Job> queue;
	private Job job;
	
	public Worker(BlockingQueue<Job> queue, Job job)  
	{
		super();
		this.queue = queue;
		this.job = job;
	}

	@Override
	public void run() 
	{
		try {
			queue.put(job);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
		
}
