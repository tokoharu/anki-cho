package roteLearningNote;

public class Problem {
	private int originalNumber = -1;
	private String problem;
	private String answer;
	
	
	public Problem(String problem, String answer, int originalNumber) {
		setProblem(problem);
		setAnswer(answer);
		setOriginalNumber(originalNumber);
	}
	
	public void setProblem(String problem) {
		this.problem = problem;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getProblem() {
		return this.problem;
	}
	
	public String getAnswer() {
		return this.answer;
	}

	public int getOriginalNumber() {
		return originalNumber;
	}

	public void setOriginalNumber(int originalNumber) {
		this.originalNumber = originalNumber;
	}
}
