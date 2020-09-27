package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Score {
	private File _scoreRecordFile;
	
	public Score() {
		_scoreRecordFile = new File("data/score");
	}
	
	public int getScore() {
		if (!_scoreRecordFile.exists()) {
			try {
				_scoreRecordFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			setScore(0);
		}
		return readCurrentScore();
	}
	
	private void setScore(int value) {
		try {		
			FileWriter fw = new FileWriter(_scoreRecordFile);
			fw.write(Integer.toString(value));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int readCurrentScore() {
		String score = null;
		try {
			Scanner scanner = new Scanner(_scoreRecordFile);
			score = scanner.nextLine();
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(score);
	}
	
	public void updateScore(int value) {
		int currentScore = readCurrentScore();
		setScore(currentScore + value);
	}
	
	public void resetScore() {
		_scoreRecordFile.delete();
	}
}
