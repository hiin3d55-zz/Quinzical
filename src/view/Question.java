package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class Question {

	private int _amount;
	private String _clue;
	private Button _clueBtn;
	
	public Question(int amount, String clue) {
		_amount = amount;
		_clue = clue;
		_clueBtn = new Button("$" + String.valueOf(_amount));
		
		_clueBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (amount == 100) {
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
}
