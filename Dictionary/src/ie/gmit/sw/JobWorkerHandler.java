package ie.gmit.sw;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Singleton to pass variables between classes and also initializes the inQueue and outQueue. Also handles the ThreadPool

public class JobWorkerHandler 
{
	private static JobWorkerHandler instance = null;
	private static ArrayBlockingQueue<Job> inQueue = new ArrayBlockingQueue<>(10);
	private static ConcurrentHashMap<Integer, String> outQueue = new ConcurrentHashMap<>();
	private static ExecutorService executor;
	private static volatile int jobNumber = 0;
	
	private JobWorkerHandler()
	{
	}
	
	public static synchronized JobWorkerHandler init()
	{
		if(instance == null)
		{
			instance = new JobWorkerHandler();
			//also initialize actual workers 10 or so.
			executor = Executors.newFixedThreadPool(10);
			for (int i = 0; i < 10; i++) 
			{
				Runnable worker = new Worker();
				executor.execute(worker);
			}
		}
		return instance;
	}
	
	public static synchronized ArrayBlockingQueue<Job> getInQueue() {
		return inQueue;
	}

	public static ConcurrentHashMap<Integer, String> getOutQueue() {
		return outQueue;
	}
	
	public synchronized int getJobNumber()
	{
		return jobNumber;
	}
	
	public static String getResult(int jobNumber)
	{
		return outQueue.remove(jobNumber);
	}
		
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	protected void finalize() throws Throwable {
		JobWorkerHandler.Shutdown();
		super.finalize();
	}

	public static void Shutdown()
	{
		executor.shutdown();
	}
}
