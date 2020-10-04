package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class AnswerScreen {
	protected BorderPane _pane;
	protected Button _submitBtn;
	protected Button _repeatBtn;
	protected TextField _attemptInput;
	protected VBox _centerBox;
	
	protected String _clue;
	
	protected SoundAdjuster _adjuster;
	protected Text _currentSpeed;
	
	public AnswerScreen(BorderPane pane, String clue) {
		_pane = pane;
		_centerBox = new VBox();
		_centerBox.getStyleClass().add("center-screen-box");
		
		_attemptInput = new TextField();

		_submitBtn = new Button("Submit");
		_submitBtn.getStyleClass().add("golden-button");
		
		_repeatBtn = new Button("Repeat Clue");
		_repeatBtn.getStyleClass().add("golden-button");
		
		_clue = clue;
		
		_adjuster = new SoundAdjuster(_clue);
		_currentSpeed = new Text(_adjuster.getSpeed() + " (Default)");
	}
	
	/**
	 * Displays the GUI for respective answer screen and also speaks the clue after.
	 */
	public void display() {
		createGUI();
		handleEvents();
		_adjuster.speak(_adjuster.getText());
	}
	
	protected void updateSpeed() {
		_centerBox.getChildren().remove(_currentSpeed);
		
		if (_adjuster.getSpeed().equals("1.0")) {
			_currentSpeed = new Text(_adjuster.getSpeed() + " (Deafult)");
		} else {
			_currentSpeed = new Text(_adjuster.getSpeed());
		}

		_centerBox.getChildren().add(_currentSpeed);
	}
	
	/**
	 * Creates the GUI required for the screen
	 */
	protected abstract void createGUI();
	
	/**
	 * Add a listener to the repeat button which speaks the clue again
	 */
	protected void handleEvents() {
		_repeatBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				_adjuster.speak(_adjuster.getText());
			}
		});
		
		_adjuster.getFasterBtn().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				_adjuster.fasterSpeed();
				updateSpeed();
			}
		});
		
		_adjuster.getSlowerBtn().setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				_adjuster.slowerSpeed();
				updateSpeed();
			}
		});
	}
}