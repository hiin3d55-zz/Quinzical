package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * This class represents an Answer Screen. It is mostly used for reusing codes between different screens
 * in Practice Module and Games Module answer screen.
 * @author Sherman, Dave
 *
 */
public abstract class AnswerScreen {
	protected BorderPane _pane;
	protected Button _submitBtn;
	protected Button _repeatBtn;
	protected TextField _attemptInput;
	protected VBox _centerBox;
	protected VBox _soundAdjustBox;
	protected StackPane _macrons;

	
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
		
		_macrons = new StackPane();
		_macrons.getStyleClass().addAll("macron-box");
		initialiseMacronBox();
	}
	
	private void initialiseMacronBox() {
		HBox buttonBox = new HBox();
		buttonBox.getStyleClass().addAll("macron-button-box", "center-screen-box");
		
		MacronButtonEvent macronEvent = new MacronButtonEvent();
		
		Button macron1  = new Button("ā");
		macron1.setOnAction(macronEvent);
		macron1.getStyleClass().addAll("golden-button", "macron-button");
		Button macron2 = new Button("ē");
		macron2.setOnAction(macronEvent);
		macron2.getStyleClass().addAll("golden-button", "macron-button");
		Button macron3 = new Button("ī");
		macron3.setOnAction(macronEvent);
		macron3.getStyleClass().addAll("golden-button", "macron-button");
		Button macron4  = new Button("ō");
		macron4.setOnAction(macronEvent);
		macron4.getStyleClass().addAll("golden-button", "macron-button");
		Button macron5 = new Button("ū");
		macron5.setOnAction(macronEvent);
		macron5.getStyleClass().addAll("golden-button", "macron-button");
		
		buttonBox.getChildren().addAll(macron1, macron2, macron3, macron4, macron5);
		
		Text text = new Text("Macrons");
		text.getStyleClass().addAll("information-text", "normal-text");
		
		_macrons.getChildren().addAll(text, buttonBox);
	}
	
	/**
	 * Displays the GUI for respective answer screen and also speaks the clue after.
	 */
	public void display() {
		createGUI();
		handleEvents();
		_adjuster.speak(_adjuster.getText());
	}
	
	/**
	 * Update the text displaying speed of speech.
	 */
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
	
	private class MacronButtonEvent implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			String macronText = ((Button)e.getSource()).getText();
			_attemptInput.setText(_attemptInput.getText() + macronText);
			
		}
		
	}
}