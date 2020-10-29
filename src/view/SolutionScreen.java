package view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import speech.SoundAdjuster;

/**
 * This class represents a Solution Screen. It is mostly used for reusing codes between different screens
 * in Games Module and Practice Module solution screen.
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
		
		_adjuster = SoundAdjuster.getInstance(_solution);
	}
	
	public abstract void displayCorrect();
	
	public abstract void displayIncorrect();
	
	public void displayDontKnow() {}
}