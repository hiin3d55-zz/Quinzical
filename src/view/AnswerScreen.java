package view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * This class represents the screen that shows when the user tries to answer the question.
 * 
 * @author Dave Shin
 *
 */
public class AnswerScreen {
	
	private BorderPane _pane;
	private TextField _attemptInput;
	private Button _submitBtn;
	private Button _dontKnowBtn;
	private Button _repeatBtn;
	private Question _question;
	private SoundAdjuster _adjuster;
	private Text _currentSpeed;
	private VBox _answerBox;
	
	public AnswerScreen(BorderPane pane, Question question) {
		_pane = pane;
		
		_attemptInput = new TextField();
		
		_submitBtn = new Button("Submit");
		_submitBtn.getStyleClass().add("golden-button");
		
		_dontKnowBtn = new Button("Don\'t know");
		_dontKnowBtn.getStyleClass().add("normal-button");
		
		_repeatBtn = new Button("Repeat Clue");
		_repeatBtn.getStyleClass().add("golden-button");
		
		_question = question;
		
		_adjuster = new SoundAdjuster(_question.getClue());
		
		_currentSpeed = new Text(_adjuster.getSpeed() + " (Default)");
		
		_answerBox = new VBox();
	}
	
	public void display() {	
		
		_answerBox.getStyleClass().add("center-screen-box");
		
		HBox inputAndSoundBtn = new HBox();
		inputAndSoundBtn.getStyleClass().add("center-screen-box");
		
		Text instruction = new Text("Listen to the clue then answer the question.");
		instruction.getStyleClass().addAll("normal-text", "information-text");
		
		inputAndSoundBtn.getChildren().addAll(_attemptInput, _repeatBtn);
		
		HBox buttonBox = new HBox();
		buttonBox.getStyleClass().add("center-screen-box");
		buttonBox.getChildren().addAll(_submitBtn, _dontKnowBtn);
		
		handleEvents();
		
		_answerBox.getChildren().addAll(instruction, inputAndSoundBtn, buttonBox, _adjuster.getFasterBtn(), 
				_adjuster.getSlowerBtn(), _currentSpeed);
		_pane.setCenter(_answerBox);
		
		_adjuster.speak();
	}
	
	public void updateSpeed() {
		_answerBox.getChildren().remove(_currentSpeed);
		
		if (_adjuster.getSpeed().equals("1.0")) {
			_currentSpeed = new Text(_adjuster.getSpeed() + " (Deafult)");
		} else {
			_currentSpeed = new Text(_adjuster.getSpeed());
		}

		_answerBox.getChildren().add(_currentSpeed);
	}
	
	public void handleEvents() {
		_submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				boolean loseScore = true;
				
				String attempt = _attemptInput.getText();
				
				// Make it case-insensitive and trim all leading and trailing spaces.
				attempt = attempt.toLowerCase();
				attempt = attempt.trim();
				
				SolutionScreen solScrn = new SolutionScreen(_pane, _question, 
						_question.getSolution()[0]);
				
				// If the attempt is an empty string, it gets treated as the same way when the Don't Know 
				// button is pressed.
				if (attempt.equals("")) {
					solScrn.displayDontKnow();
					loseScore= false;
				}
				
				// A for loop is used because there can be multiple solutions and we want to 
				// check if the attempt matches with at least one solution.
				for (String solution : _question.getSolution()) {
					solution = solution.toLowerCase();
					solution = solution.trim();
					
					if (attempt.equals(solution)) {
						solScrn.displayCorrect();
						loseScore = false;
					}
				}
				
				if (loseScore) {
					solScrn.displayIncorrect();
				}
			}
		});
		
		_dontKnowBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				SolutionScreen solScrn = new SolutionScreen(_pane, _question,
						_question.getSolution()[0]);
				solScrn.displayDontKnow();
			}
		});
		
		_repeatBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				_adjuster.speak();
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
