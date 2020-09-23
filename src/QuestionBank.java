

public class QuestionBank {
	private Category _category; 
	private Clue _clue;

	
	public QuestionBank() {
		_clue = new Clue();
		_category = new Category();
	}
	public String[] requestCategory(boolean gameModule) {
		return _category.getCategories(gameModule);
	}
	
	public String[] requestClueForCategory(String category, boolean gameModule) {
		return _clue.getClues(category, gameModule);
	}
	
	public String answerForClue(String category, String clue) {
		return _clue.getAnswer(category, clue);
	}

}
