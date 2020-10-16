package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.QuestionBank;


/**
 * This class represents the Module Screen. It is mostly used for reusing codes between different screens
 * in Practice Module and Games Module choosing clue screen.
 * @author Sherman, Dave
 *
 */
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
	

	
	public void display() {
		displayScreen();
		//Shows the main menu button at the bottom
		_pane.getBottom().getStyleClass().remove("invisible-component");
	}

	protected abstract void displayScreen();
}
