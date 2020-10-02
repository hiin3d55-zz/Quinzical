package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
	private Question _question;
	
	public AnswerScreen(BorderPane pane, Question question) {
		_pane = pane;
		
		_attemptInput = new TextField();
		_attemptInput.setMaxWidth(200);
		
		_submitBtn = new Button("Submit");
		_submitBtn.getStyleClass().add("golden-button");
		
		_dontKnowBtn = new Button("Don\'t know");
		_dontKnowBtn.getStyleClass().add("normal-button");
		
		_question = question;
	}
	
	public void display() {	
		VBox answerBox = new VBox();
		answerBox.getStyleClass().add("center-screen-box");
		
		Text instruction = new Text("Listen to the clue then answer the question.");
		instruction.getStyleClass().add("normal-text");
		
		HBox buttonBox = new HBox();
		buttonBox.getStyleClass().add("center-screen-box");
		buttonBox.getChildren().addAll(_submitBtn, _dontKnowBtn);
		
		handleEvents();
		
		answerBox.getChildren().addAll(instruction, _attemptInput, buttonBox);
		_pane.setCenter(answerBox);

		speakClue();
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
	}
	
	public void speakClue() {
		
		// Bash command for speaking out the clue.
//		String speakClueCmd = "echo \"" + _question.getClue() + "\" | festival --tts";
//		
//		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", speakClueCmd);
//		try {
//			Process process = builder.start();
//			process.toString(); // This line does not do anything. It is just here so that the 
//								// variable of process is used.
//		}
//		catch (IOException e) {
//			System.out.println("Error with using festival to read out the question.");
//			e.printStackTrace();
//		}
	}
}
