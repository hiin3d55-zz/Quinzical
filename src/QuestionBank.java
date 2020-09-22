

public class QuestionBank {
	private Category _category; 
	
	public QuestionBank() {
		_category = new Category();
	}
	public String[] requestCategory(boolean gameModule) {
		return _category.getCategories(gameModule);
	}
	
	public String[] requestClueForCategory(String category, boolean gameModule) {
		//Generate data first
		if (gameModule) {
			requestCategory(true);
		}
		return _category.requestClue(category, gameModule);
	}
	
	public String answerForClue(String category, String clue) {
		return _category.getClueAnswer(category, clue);
	}

}
