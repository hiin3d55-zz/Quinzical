package model.clue;

/**
 * This class represents functionalities to retrieve clues for Practice Module.
 * @author se2062020
 *
 */
public class PracticeModuleClue extends Clue{
	
	/**
	 * Get all the clues that are in a category from the question bank.
	 */
	public String[] getClues(String category) {
		String[] clues = getAllClues(category);
		return randomiseClues(clues, 1);
	}

	/**
	 * This method does nothing as it is intended for GamesModuleClue
	 */
	public int[] getClueValues(String category) {
		return null;
	}
}
