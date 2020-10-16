package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * This class represents the functionalities required to obtain clues from question bank.
 * @author Sherman
 *
 */
public abstract class Clue {
	protected File _dataRecordFolder;
	
	public Clue() {
		_dataRecordFolder = new File("data/categories");
	}
		
	public abstract String[] getClues(String category);
	
	/**
	 * Get all clues of a category from the question bank.
	 * @param category The category of which the clues are in.
	 * @return An array of all the clues in the category.
	 */
	protected String[] getAllClues(String category) {
		File file = new File("questionBank/" + category);
		List<String> clues = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				clues.add(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String[] cluesArray = clues.toArray(new String[clues.size()]);
		return formatClues(cluesArray);
	}
	
	/**
	 * Randomly picks a number of clues.
	 * @param clues The clues to be picked from.
	 * @param count The number of clues to be randomly generated.
	 * @return An array of all randomly selected clues.
	 */
	protected String[] randomiseClues(String[] clues, int count) {
		Set<String> randomised = new HashSet<String>();
		
		while (randomised.size() < count) {
			randomised.add(clues[(int)(Math.random() * clues.length)]);
		}
		return randomised.toArray(new String[randomised.size()]);
	}
	
	/**
	 * Given a raw clue retrieved from the question bank, format it to get the clue without the answer.
	 * @param clues The raw clue retrieved from the question bank.
	 * @return The clue which does not contain the answer.
	 */
	protected String[] formatClues(String[] clues) {
		String[] res = new String[clues.length];
		for (int i = 0; i < clues.length; i++) {
			res[i] = clues[i].split("[|]")[0];
		}
		return res;
	}
	
	/**
	 * Gets the answer(s) to a specified clue in a category.
	 * @param category The category that the specified clue is in.
	 * @param clue The clue for which an answer is needed.
	 * @return An array of answers. Clues may contain multiple answers.
	 */
	public String[] getAnswer(String category, String clue) {
		File file = new File("questionBank/" + category);
		String actualAnswer = null;
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				String currentClue = line.split("[|]")[0];
				if (currentClue.equals(clue)) {
					actualAnswer = line.split("[|]")[2];
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return actualAnswer.split("/");
	}
	
	public void update(String category, String clue) {}
	
	public abstract int[] getClueValues(String category);
}
