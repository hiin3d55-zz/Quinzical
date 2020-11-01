package quinzical.model.clue;

/**
 * This class represents a set of functionalities to manipulate clues for Practice Module.
 * @author Sherman
 *
 */
public class PracticeModuleClue extends Clue{
	
	/**
	 * Get a randomly generated clue for the specified category.
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
