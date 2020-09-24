
public class QuestionBank {
	private Category _category; 
	private Clue _clue;

	
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
	
	public String answerForClue(String category, String clue) {
		String ans = _clue.getAnswer(category, clue);
		return ans;
	}
}
