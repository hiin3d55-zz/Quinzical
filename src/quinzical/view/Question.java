package quinzical.view;

import javafx.scene.control.Button;

/**
 * This class contains all information that has to do with a specific question.
 * 
 * @author Dave Shin
 *
 */
public class Question {

	private int _amount;
	private String _clue;
	private String _category;
	private Button _clueBtn;
	private String[] _solution;
	
	public Question(int amount, String clue, String category, String[] solution) {
		_amount = amount;
		_clue = clue;
		_category = category;
		_clueBtn = new Button("$" + String.valueOf(amount));
		_solution = solution;
	}
	
	public int getAmount() {
		return _amount;
	}
	
	public Button getButton() {
		return _clueBtn;
	}
	
	public String getCategory() {
		return _category;
	}

	public String[] getSolution() {
		return _solution;
	}
	
	public String getClue() {
		return _clue;
	}
}
