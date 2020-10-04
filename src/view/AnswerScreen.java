package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class AnswerScreen {
	protected BorderPane _pane;
	protected Button _submitBtn;
	protected Button _repeatBtn;
	protected TextField _attemptInput;
	protected VBox _centerBox;
	protected VBox _soundAdjustBox;
	
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
		
		_soundAdjustBox = new VBox();
		_soundAdjustBox.getStyleClass().addAll("center-screen-box", "sound-box");
		
		_currentSpeed = new Text(_adjuster.getSpeed() + " (Default)");
		_currentSpeed.getStyleClass().add("normal-text");
		
		_soundAdjustBox.getChildren().addAll(_adjuster.getFasterBtn(), _currentSpeed, _adjuster.getSlowerBtn());
		_adjuster.getFasterBtn().getStyleClass().add("golden-button");
		_adjuster.getSlowerBtn().getStyleClass().add("golden-button");		
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
		if (_adjuster.getSpeed().equals("1.0")) {
			_currentSpeed.setText(_adjuster.getSpeed() + " (Deafult)");
		} else {
			_currentSpeed.setText(_adjuster.getSpeed());
		}
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