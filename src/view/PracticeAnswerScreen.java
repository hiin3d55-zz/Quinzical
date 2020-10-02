package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class represents the screen that gets displayed when the user is trying to answer the question.
 * 
 * @author Sherman Chin, Dave Shin
 *
 */
public class PracticeAnswerScreen{
	
	private String _clue;
	private String[] _answers;
	private int _remainingAttempts;
	
	private Stage _primaryStage;
	private Button _submitBtn;
	private TextField _attemptInput;
	private Text _hint;
	private Text _wrongText;
	private Text _attemptsCountText;
	private VBox _answerBox;
	
	public PracticeAnswerScreen(Stage primaryStage, String clue, String[] answers) {
		_clue = clue;
		_answers = answers;
		_remainingAttempts = 3; // The user is allowed three attempts at one question.
		
		_primaryStage = primaryStage;
		_submitBtn = new Button("Submit");
		_attemptInput = new TextField();
		_hint = new Text("Hint: The first letter of the answer is \"" + _answers[0].charAt(0) + "\"");
		_wrongText = new Text("Incorrect!");
		_attemptsCountText = new Text("Number of attempts remaining: " + Integer.toString(_remainingAttempts));
		_answerBox = new VBox();
	}
	
	public void display() {
		handleEvents();
		
		BorderPane answerPane = new BorderPane();
		
		Text instruction = new Text();
		instruction.setText("Clue: " + _clue);
		speak(_clue);
		
		_answerBox.getChildren().addAll(instruction, _attemptInput, _submitBtn, _attemptsCountText);
		answerPane.setCenter(_answerBox);
		
		_primaryStage.setScene(new Scene(answerPane, 600, 400));
		_primaryStage.show();
	}
	
	public void handleEvents() {
		_submitBtn.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				
				boolean correct = false;
				
				String attempt = _attemptInput.getText();
				
				// Make it case-insensitive and trim all leading and trailing spaces.
				attempt = attempt.toLowerCase();
				attempt = attempt.trim();
				
				PracticeSolutionScreen solScrn = new PracticeSolutionScreen(_primaryStage, _clue, _answers[0]);
		
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
					if (_remainingAttempts == 2) {
						_answerBox.getChildren().add(_wrongText);
						speak("Incorrect");
					}
					
					if (_remainingAttempts == 1) {
						_answerBox.getChildren().add(_hint);
						speak(_hint.getText());
					} else if (_remainingAttempts < 1) {
						solScrn.displayIncorrect();
					}

					_attemptsCountText.setText("Number of attempts remaining: " + (_remainingAttempts));
				}
			}
		});
	}
	
	public void speak(String speech) {
		
		// Bash command for speaking out the clue.
		String speakClueCmd = "echo \"" + speech + "\" | festival --tts";
		
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", speakClueCmd);
		try {
			Process process = builder.start();
			process.toString();
		}
		catch (IOException e) {
			System.out.println("Error with using festival to read out the question.");
			e.printStackTrace();
		}
	}
}
