package ie.gmit.sw;

/*
 * Model Class for Job
 */

public class Job 
{
	private int jobNumber;
	private String word;
	private JobType jobType;
	private String definition;
	
	public Job(int jobNumber, String word, JobType jobType) {
		super();
		setJobNumber(jobNumber);
		setWord(word);
		setJobType(jobType);
		setDefinition("");
	}
	
	public Job(int jobNumber, String word, JobType jobType, String definition) {
		super();
		setJobNumber(jobNumber);
		setWord(word);
		setJobType(jobType);
		setDefinition(definition);
	}
	
	public int getJobNumber() {
		return jobNumber;
	}
	private void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}
	public String getWord() {
		return word;
	}
	private void setWord(String word) {
		this.word = word;
	}
	
	public JobType getJobType() {
		return jobType;
	}
	private void setJobType(JobType jobType) {
		this.jobType = jobType;
	}
	
	public String getDefinition() {
		return definition;
	}

	private void setDefinition(String definition) {
		this.definition = definition;
	}
}
