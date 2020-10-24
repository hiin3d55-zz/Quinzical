package model;

import model.category.Category;
import model.category.GamesModuleCategory;
import model.category.PracticeModuleCategory;
import model.clue.Clue;
import model.clue.GamesModuleClue;
import model.clue.PracticeModuleClue;

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
	
	/**
	 * Checks if 5 categories chosen by user has been stored into database
	 * @return Returns true if chosen, false otherwise.
	 */
	public boolean isCategoryChosen() {
		if (_category.getAllCategories().length == _category.getCategories().length) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Add categories to the database
	 * @param categories The list of categories to be added.
	 */
	public void storeCategories(String[] categories) {
		if (isCategoryChosen() ==  false) {
			_category.recordCategories(categories);
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
	
	public int[] getValues(String category) {
		return _clue.getClueValues(category);
	}
}
