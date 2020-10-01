package model;

public class QuestionBank {
	private Category _category; 
	private Clue _clue;

	
	/**
	 * @param gameModule Give true if data retrieved is for games module, otherwise false.
	 */
	public QuestionBank(boolean gameModule) {
		if (gameModule) {
			_clue = new GamesModuleClue();
			_category = new GamesModuleCategory();
		} else {
			_clue = new PracticeModuleClue();
			_category = new PracticeModuleCategory();
		}
	}
	
	
	public String[] requestCategory() {
		return _category.getCategories();
	}
	
	public String[] requestClueForCategory(String category) {
		return _clue.getClues(category);
	}
	
	/**
	 * Some answers have multiple answers.
	 */
	public String[] answerForClue(String category, String clue) {
		return _clue.getAnswer(category, clue);
	}
	
	public void resetGame() {
		_category.removeCategoryData();
	}
	
	public void updateClue(String category, String clue) {
		_clue.update(category,clue);
	}
}
