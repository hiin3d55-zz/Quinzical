package view;

import javafx.scene.layout.BorderPane;
import model.QuestionBank;

public abstract class Module {
	private BorderPane _pane;
	private QuestionBank _questionBank;
	
	public Module(BorderPane pane, QuestionBank questionBank) {
		_pane = pane;
		_questionBank = questionBank;
	}
}
