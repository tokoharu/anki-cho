package roteLearningNote;

//import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
//import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.*;
//import com.google.gson.annotations.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.*;


public class Model {
//	private String filename = "/home/shogo/ドキュメント/roteLearning/inputFile.in" ;
	public Note note;
	public Model(Note note) {
		setNote(note);
	}
	public void setNote(Note note) {
		this.note = note;
	}
	
	public void saveState() {

		try{
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(note.inputFile)));

			Gson gson = new Gson();
			
			writer.println(gson.toJson(note.problemset));
			writer.println(gson.toJson(note.tags));
			
			writer.close();
			System.out.println("Save to " + note.inputFile.getName());
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void readFile() {
		try{
			// input of Version 1.
			/*
			BufferedReader reader = new BufferedReader(new FileReader(note.inputFile));
			int n = Integer.parseInt(reader.readLine());
			
			for(int i=0; i<n; i++) {
				int m = Integer.parseInt(reader.readLine());
				System.out.println("prob" + m);
				String[] problem = new String[m];
				for(int j=0; j<m; j++) {
					String tmp = reader.readLine();
					problem[j] = tmp;
				}

				m = Integer.parseInt(reader.readLine());
				String[] answer = new String[m];
				System.out.println("ans" + m);
				for(int j=0; j<m; j++) {
					String tmp = reader.readLine();
					answer[j] = tmp;
				}
				note.problems.add(problem);
				note.answers.add(answer);
			}
			*/
			
			
			InputStreamReader isr = new InputStreamReader(new FileInputStream(note.inputFile));
			JsonReader jsr = new JsonReader(isr);
			Gson gson = new Gson();
			note.problemset = gson.fromJson(jsr, new TypeToken<ArrayList<Problem>>(){}.getType());
			note.tags = gson.fromJson(jsr, new TypeToken<ArrayList<Tag>> (){}.getType());
			jsr.close();
			isr.close();
			
			checkReading();
		}
		catch(IOException e) {
			System.out.println("IOExceptioon" + e.getMessage());
		}
		catch(Exception e) {
			System.out.println(e.toString());
			System.out.println(e.getMessage());
			System.out.println("reading error.");
		}
	}
	public void checkReading() {
		boolean allNoNumber = true;
		int[] count = new int[note.problemset.size() + 10];
		for(int i=0; i<note.problemset.size(); i++)
			count[i] = 0;
		for(int i=0; i<note.problemset.size(); i++) {
			int originNum = note.problemset.get(i).getOriginalNumber();
			if(originNum != 0) {
				allNoNumber = false;
				count[originNum] ++ ;
			}
		}
		if(allNoNumber) {
			System.out.println("There are no original numbers.");
			System.out.println("So, add original numbers.");
			for(int i=0; i<note.problemset.size(); i++) {
				note.problemset.get(i).setOriginalNumber(i+1);
				System.out.println("num" + (int)(i+1));
			}
		}
		if(note.tags.size()==0) {
			note.tags.add(new Tag("ROOT"));
		}
		
		try {
			for(int i=0; i<note.problemset.size()+5; i++) {
				if(count[i] > 1) {
					throw new Exception("duplication" + i);
				}
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	public void setJTable() {
		String[][] tableData = new String[note.problemset.size()][3] ; 
		
		for(int i=0; i<note.problemset.size(); i++) {
			tableData[i][0] = String.valueOf(i);
			tableData[i][1] = note.problemset.get(i).getProblem();
			tableData[i][2] = note.problemset.get(i).getAnswer();
		}
		for(; 0<note.allProblemTableModel.getRowCount();)
			note.allProblemTableModel.removeRow(0);
		for(int i=0; i<note.problemset.size(); i++)
			note.allProblemTableModel.addRow(tableData[i]);
		
		
	}
/*
	private StringBuilder makeStringBuild(String[] strs) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<strs.length; i++) sb.append(" "+strs[i]+"\n");
		return sb;
	}
	*/
	private void printNowState() {
		if(note.isAnswer) {
			note.mainAnswerText.setText(note.problemset.get(note.problemNum).getAnswer());
			return ;
		}
		
		note.mainProblemText.setText(note.problemset.get(note.problemNum).getProblem());
		note.mainAnswerText.setText("");
	}
	
	
	
	public void printNext() {
		if(note.isAnswer) {
			if(note.problemset.isEmpty()) {
				System.out.println("Empty.");
				note.mainProblemText.setText("Empty");
				return ;
			}
			note.isAnswer = false;
			note.problemNum = (int)(Math.random() * (double) note.problemset.size()) ;
			note.probNumLabel.setText(String.valueOf(note.problemNum));
			printNowState();
		}
		else {
			note.isAnswer = true;
			printNowState();
		}
	}
	
}
