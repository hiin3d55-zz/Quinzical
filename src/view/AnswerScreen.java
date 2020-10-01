package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AnswerScreen {
	
	private Stage _primaryStage;
	private TextField _attemptInput;
	private Button _submitBtn;
	private Button _dontKnowBtn;
	private GamesModule _gamesMod;
	
	private Question _question;
	
	public AnswerScreen(Stage primaryStage, Question question, GamesModule gamesMod) {
		_primaryStage = primaryStage;
		_attemptInput = new TextField("Type your answer here");
		_submitBtn = new Button("Submit");
		_dontKnowBtn = new Button("Don\'t know");
		_question = question;
		_gamesMod = gamesMod;
	}
	
	public void display() {
		GridPane answerPane = new GridPane();
		Text instruction = new Text();
		instruction.setText("Listen to the clue then answer the question.");
		answerPane.add(instruction, 0, 0);
		
		answerPane.add(_attemptInput, 0, 1);
		answerPane.add(_submitBtn, 0, 2);
		answerPane.add(_dontKnowBtn, 1, 2);
		
		handleEvents();
		
		_primaryStage.setScene(new Scene(answerPane, 600, 400));
		_primaryStage.show();
		
		speakClue();
	}
	
	public void handleEvents() {
		_submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				boolean correct = false;
				
				String attempt = _attemptInput.getText();
				attempt = attempt.toLowerCase();
				attempt = attempt.trim();
				
				SolutionScreen solScrn = new SolutionScreen(_primaryStage, _question, _gamesMod, 
						_question.getSolution()[0]);
				
				if (attempt.equals("") || attempt.equals("Type your answer here")) {
					solScrn.displayDontKnow();
				}
				
				for (String solution : _question.getSolution()) {
					solution = solution.toLowerCase();
					solution = solution.trim();
					
					if (attempt.equals(solution)) {
						solScrn.displayCorrect();
						correct = true;
					}
				}
				
				if (!correct) {
					solScrn.displayIncorrect();
				}
			}
		});
		
		_dontKnowBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				SolutionScreen solScrn = new SolutionScreen(_primaryStage, _question, _gamesMod, 
						_question.getSolution()[0]);
				solScrn.displayDontKnow();
			}
		});
	}
	
	public void speakClue() {
		
		// Bash command for speaking out the clue.
		String speakClueCmd = "echo \"" + _question.getClue() + "\" | festival --tts";
		
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
