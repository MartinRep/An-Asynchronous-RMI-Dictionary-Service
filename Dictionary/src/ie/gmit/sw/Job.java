package ie.gmit.sw;

/*
 * Model Class for Job
 */

public class Job 
{
	private int jobNumber;
	private String word;
	
	public Job(int jobNumber, String word) {
		super();
		this.jobNumber = jobNumber;
		this.word = word;
	}
	public int getJobNumber() {
		return jobNumber;
	}
	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
}
