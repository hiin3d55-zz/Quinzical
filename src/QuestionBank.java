

public class QuestionBank {
	private Category _category; 
	
	public QuestionBank() {
		_category = new Category();
	}
	public String[] requestCategory(boolean gameModule) {
		return _category.getCategories(gameModule);
	}
	
	public String[] requestClueForCategory(String category, boolean gameModule) {
		return _category.requestClue(category, gameModule);
	}

}
