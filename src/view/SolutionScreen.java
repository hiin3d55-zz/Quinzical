package view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

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
		
		_adjuster = new SoundAdjuster(_solution);
	}
	
	public abstract void displayCorrect();
	
	public abstract void displayIncorrect();
	
	public void displayDontKnow() {}
}