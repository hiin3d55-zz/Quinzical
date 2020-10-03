package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * This class represents the screen that gets displayed when the user is trying to answer the question.
 * 
 * @author Sherman Chin, Dave Shin
 *
 */
public class PracticeAnswerScreen extends AnswerScreen{
	
	private String _clue;
	private String[] _answers;
	private int _remainingAttempts;
	
	private Text _hint;
	private Text _wrongText;
	private Text _attemptsCountText;
	
	public PracticeAnswerScreen(BorderPane pane, String clue, String[] answers) {
		super(pane, clue);
		
		_clue = clue;
		_answers = answers;
		_remainingAttempts = 4; // The user is allowed four attempts at one question.
		
		_hint = new Text("Hint: The first letter of the answer is \"" + _answers[0].charAt(0) + "\"");
		_hint.getStyleClass().addAll("normal-text", "invisible-component");
		
		_wrongText = new Text("Incorrect!");
		_wrongText.getStyleClass().addAll("header-msg", "normal-text", "invisible-component");
		
		_attemptsCountText = new Text("Number of attempts remaining: " + Integer.toString(_remainingAttempts));
		_attemptsCountText.getStyleClass().add("normal-text");
		
	}
	
	protected void createGUI() {
		
		Text instruction = new Text("Clue: " + _clue);
		instruction.getStyleClass().addAll("normal-text", "information-text");
		instruction.setWrappingWidth(500);
		instruction.setTextAlignment(TextAlignment.CENTER);
		
		HBox inputAndSoundBtn = new HBox();
		inputAndSoundBtn.getStyleClass().add("center-screen-box");
		inputAndSoundBtn.getChildren().addAll(_attemptInput, _repeatBtn);
		
		_centerBox.getChildren().addAll(_wrongText, instruction, inputAndSoundBtn, _submitBtn, _attemptsCountText, _hint);
		_pane.setCenter(_centerBox);
	}
	
	/**
	 * Adds listeners to buttons
	 */
	protected void handleEvents() {
		super.handleEvents();
		_submitBtn.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				boolean correct = false;
				
				String attempt = _attemptInput.getText();
				
				// Make it case-insensitive and trim all leading and trailing spaces.
				attempt = attempt.toLowerCase();
				attempt = attempt.trim();
				
				PracticeSolutionScreen solScrn = new PracticeSolutionScreen(_pane, _clue, _answers[0]);
				
				// A for loop is used because there can be multiple solutions and we want to 
				// check if the attempt matches with at least one solution.
				for (String solution : _answers) {
					solution = solution.toLowerCase();
					solution = solution.trim();
					
					if (attempt.equals(solution)) {
						solScrn.displayCorrect();
						correct = true;
					}
				}
				
				if (!correct) {
					_remainingAttempts--;
					
					// Only add wrongText when two attempts remain to prevent from duplicate 
					// children from being added.
					_wrongText.getStyleClass().remove("invisible-component");
					speak("Incorrect");
					
					if (_remainingAttempts == 1) {
						_hint.getStyleClass().remove("invisible-component");
						speak(_hint.getText());
					} else if (_remainingAttempts < 1) {
						solScrn.displayIncorrect();
					}

					_attemptsCountText.setText("Number of attempts remaining: " + (_remainingAttempts));
				}
			}
		});
	}
}
