package model;

public class PracticeModuleClue extends Clue{
	public String[] getClues(String category) {
		String[] clues = getAllClues(category);
		return randomiseClues(clues, 1);
	}

	public int[] getClueValues(String category) {
		return null;
	}
}
