package quinzical.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import quinzical.model.QuestionBank;


/**
 * This class represents the Module Screen. It is mostly used for reusing codes between different screens
 * in Practice Module and Games Module choosing clue screen.
 * 
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
	
	/**
	 * Displays the screen of the module.
	 */
	public void display() {
		displayScreen();
		
		//Shows the main menu button at the bottom
		_pane.getBottom().getStyleClass().removeAll("invisible-component");
	}

	/**
	 * Displays the screen of the module. This method is used for template method pattern.
	 */
	protected abstract void displayScreen();
}
