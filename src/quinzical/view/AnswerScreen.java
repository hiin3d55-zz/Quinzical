package quinzical.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import quinzical.speech.SoundAdjuster;

/**
 * This class represents an Answer Screen. It is mostly used for reusing codes between different screens
 * in Practice Module and Games Module answer screen.
 * 
 * @author Sherman, Dave
 *
 */
public abstract class AnswerScreen {
	protected BorderPane _pane;
	protected StackPane _macrons;
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
		_centerBox.setStyle("-fx-spacing: 25px;");
		
		_attemptInput = new TextField();

		_submitBtn = new Button("Submit");
		_submitBtn.getStyleClass().add("golden-button");
		
		try {
			createImageForSoundButton();
		} catch (FileNotFoundException e) {
			_repeatBtn = new Button("Repeat Clue");
		}
		_repeatBtn.getStyleClass().add("golden-button");
		
		_clue = clue;
		
		_adjuster = new SoundAdjuster(_clue);
		
		_soundAdjustBox = new VBox();
		_soundAdjustBox.getStyleClass().addAll("center-screen-box", "sound-box", "left-right-box");
		
		_currentSpeed = new Text(_adjuster.getSpeed() + " (Default)");
		_currentSpeed.getStyleClass().add("normal-text");
		_currentSpeed.setStyle("-fx-fill: #EAEAEA");
		
		_soundAdjustBox.getChildren().addAll(_adjuster.getFasterBtn(), _currentSpeed, _adjuster.getSlowerBtn());
		_adjuster.getFasterBtn().getStyleClass().add("golden-button");
		_adjuster.getSlowerBtn().getStyleClass().add("golden-button");		
		
		_macrons = new StackPane();
		_macrons.getStyleClass().addAll("macron-box", "left-right-box");
		initialiseMacronBox();
		
		BorderPane.setAlignment(_macrons, Pos.CENTER);
		BorderPane.setAlignment(_soundAdjustBox, Pos.CENTER);
	}
	
	/**
	 * This method initializes the repeat clue button with a sound image if the image can be found 
	 * from the resources folder.
	 * Throws an exception if not found.
	 * @throws FileNotFoundException
	 */
	private void createImageForSoundButton() throws FileNotFoundException {
		Image image = new Image(new FileInputStream("resources/volume.png"));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(30);
		imageView.setPreserveRatio(true);
		
		_repeatBtn = new Button();
		_repeatBtn.setPrefSize(30, 30);
		_repeatBtn.setGraphic(imageView);		
	}
	
	/**
	 * Sets up the macron box.
	 */
	private void initialiseMacronBox() {
		VBox buttonBox = new VBox();
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
		text.getStyleClass().add("information-text");
		
		_macrons.getChildren().addAll(text, buttonBox);
	}
	
	/**
	 * Displays the GUI for respective answer screen and also speaks the clue after.
	 */
	public void display() {
		createGUI();
		handleEvents();
		_pane.setLeft(_macrons);
		_pane.setRight(_soundAdjustBox);
		_adjuster.speak(_adjuster.getText());
	}
	
	/**
	 * Update the text displaying speed of speech.
	 */
	protected void updateSpeed() {		
		if (_adjuster.getSpeed().equals("1.0")) {
			_currentSpeed.setText(_adjuster.getSpeed() + " (Default)");
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
	
	/**
	 * This class handles events for the five different macron buttons.
	 * 
	 * @author Sherman
	 *
	 */
	private class MacronButtonEvent implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			String macronText = ((Button)e.getSource()).getText();
			int position = _attemptInput.getCaretPosition();
			_attemptInput.insertText(position, macronText);			
		}
		
	}
}