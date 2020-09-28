package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class Question {

	private int _amount;
	private String _clue;
	private String _category;
	private Button _clueBtn;
	private boolean _isMin;
	
	public Question(int amount, String clue, String category) {
		_amount = amount;
		_clue = clue;
		_category = category;
		_clueBtn = new Button("$" + String.valueOf(amount));
		if (amount == 100) {
			_isMin = true;
		} else {
			_isMin = false;
		}
		
		_clueBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (_isMin) {
					System.out.println("hello hi");
				}
			}
		});
	}
	
	public int getAmount() {
		return _amount;
	}
	
	public Button getButton() {
		return _clueBtn;
	}
	
	public boolean checkIfItIsMin() {
		return _isMin;
	}
	
	public String getCategory() {
		return _category;
	}
	
	public void nowMin() {
		_isMin = true;
	}
}
