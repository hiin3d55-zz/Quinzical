package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.QuestionBank;

public abstract class Module {
	protected BorderPane _pane;
	protected QuestionBank _questionBank;
	protected VBox _centerBox;
	protected String _clue;
	
	public Module(BorderPane pane, QuestionBank questionBank) {
		_pane = pane;
		_questionBank = questionBank;
		_centerBox = new VBox();
		_centerBox.getStyleClass().add("center-screen-box");
	}
	
	public abstract void display();
}
