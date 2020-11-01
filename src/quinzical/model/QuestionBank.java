package quinzical.model;

import quinzical.model.category.Category;
import quinzical.model.category.GamesModuleCategory;
import quinzical.model.category.PracticeModuleCategory;
import quinzical.model.clue.Clue;
import quinzical.model.clue.GamesModuleClue;
import quinzical.model.clue.PracticeModuleClue;

/**
 * This class acts as an API which interacts with the question bank.
 * @author Sherman
 *
 */
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
	 * Stores categories into the database
	 * @param categories The list of categories to be added.
	 */
	public void storeCategories(String[] categories) {
		if (isCategoryChosen() ==  false) {
			_category.recordCategories(categories);
		}
	}
	
	/**
	 * Retrieve an array of categories depending if it is for Games Module or Practice Module
	 * @return	An array of categories.
	 */
	public String[] requestCategory() {
		return _category.getCategories();
	}
	
	/**
	 * Retrieve all the clues for a given category. The categories retrieved depends on type of Module.
	 * Games Module - If clues have not been requested before for that category, randomly generate five for it,
	 * 				  else return clues stored in database. If all clues have been attempted, return null.
	 * Practice Module - Return an array of clues of length 1 (only 1 clue).
	 * @return An array of clues.
	 */
	public String[] requestClueForCategory(String category) {
		return _clue.getClues(category);
	}
	
	/**
	 *  Get an answer for the specified clue in a category.
	 *  @param category The category that the clue is in.
	 *  @param clue The specified clue that an answer is required.
	 *  @return An array of answers. Some clues have multiple answers to it.
	 */
	public String[] answerForClue(String category, String clue) {
		return _clue.getAnswer(category, clue);
	}
	
	/**
	 * Reset all of the stored clues and categories.
	 */
	public void resetGame() {
		_category.removeCategoryData();
	}
	
	/**
	 * Remove a clue in a category from the database.
	 */
	public void updateClue(String category, String clue) {
		_clue.update(category,clue);
	}
	
	/**
	 * Provide values to clues in a category. This method must be called only for Games Module
	 * @return An array of values. The size of this array depends on how many clues are left (not answered) in the database.
	 */
	public int[] getValues(String category) {
		return _clue.getClueValues(category);
	}
}
