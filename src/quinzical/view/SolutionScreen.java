package quinzical.view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import quinzical.speech.SoundAdjuster;

/**
 * This class represents a Solution Screen. It is mostly used for reusing codes between different screens
 * in Games Module and Practice Module solution screen.
 * 
 * @author Sherman, Dave
 *
 */
public abstract class SolutionScreen {
	protected BorderPane _pane;
	protected String _solution;
	protected Button _returnBtn;
	protected VBox _centerBox;
	protected SoundAdjuster _adjuster;
	
	public SolutionScreen(BorderPane pane, String solution, Button returnBtn) {
		_pane = pane;
		_solution = solution;
		
		_returnBtn = returnBtn;
		_returnBtn.getStyleClass().add("golden-button");
		
		_centerBox = new VBox();
		_centerBox.getStyleClass().add("center-screen-box");
		
		_adjuster = new SoundAdjuster(_solution, true);
	}
	
	/**
	 * Displays the screen that lets the user know that they are correct.
	 */
	public abstract void displayCorrect();
	
	/**
	 * Displays the screen that lets the user know that they are incorrect.
	 */
	public abstract void displayIncorrect();
	
	/**
	 * Displays the screen when the user presses the don't know button.
	 */
	public void displayDontKnow() {}
}