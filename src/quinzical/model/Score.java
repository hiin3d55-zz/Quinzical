package quinzical.model;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class represents the Score which a user has gained from Quinzical
 * @author Sherman
 *
 */
public class Score {
	private File _scoreRecordFile;

	public Score() {
		_scoreRecordFile = new File("data/score");
		if (!_scoreRecordFile.exists()) {
			File dir = new File("data");
			dir.mkdir();
			try {
				_scoreRecordFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			setScore(0);
		}
	}
	
	/**
	 * Stores the score of a user into data/score file
	 * @param value The value to be stored.
	 */
	private void setScore(int value) {
		try {		
			FileWriter fw = new FileWriter(_scoreRecordFile);
			fw.write(Integer.toString(value));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves the current score of a user.
	 * @return The score of a user.
	 */
	public int getScore() {
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
	
	/**
	 * Updates the score of a user. This method adds up to its current score
	 * @param value The score to be added on top of current score.
	 */
	public void updateScore(int value) {
		int currentScore = getScore();
		setScore(currentScore + value);
	}
	
	/**
	 * Deletes the data/score file which acts as a reset to a user's score.
	 */
	public void resetScore() {
		_scoreRecordFile.delete();
	}
}
