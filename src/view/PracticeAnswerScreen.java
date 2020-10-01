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

public class PracticeAnswerScreen{
	private String _clue;
	private String _answer;
	private Stage _primaryStage;
	private int _count;
	
	public PracticeAnswerScreen(Stage primaryStage, String clue, String answer) {
		_clue = clue;
		_answer = answer;
		_primaryStage = primaryStage;
		_count = 4;
	}
	
	public void display() {
		BorderPane answerPane = new BorderPane();
		VBox answerBox = new VBox();
		
		Text instruction = new Text();
		instruction.setText("Clue: " + _clue);
		
		TextField attempt = new TextField("Type your answer here");
		Text attemptsCountText = new Text("Number of attempts remaining: " + (_count));
		speak("You have four attempts");
		
		Text hint = new Text("Hint: The first letter of the answer is \"" + _answer.charAt(0) + "\"");
		Text wrongText = new Text("Wrong!");
		
		PracticeSolutionScreen solutionScrn = new PracticeSolutionScreen(_primaryStage, _answer);
		
		Button submit = new Button("Submit");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				if (attempt.getText().toLowerCase() != _answer.toLowerCase()) {
					speak("Incorrect");
					_count--;
					if (_count == 1) {
						//make hint text visible
						speak(hint.getText());
						System.out.println("two tries");
					}else if (_count < 1) {
						solutionScrn.displayIncorrect();
						System.out.println("too much attempts");
					}
					attemptsCountText.setText("Number of attempts remaining: " + (_count));
				} else {
					solutionScrn.displayCorrect();
				}
			}
		});
		

		
		answerBox.getChildren().addAll(instruction, attempt, submit, attemptsCountText, hint, wrongText);
		
		answerPane.setCenter(answerBox);
		
		_primaryStage.setScene(new Scene(answerPane, 600, 400));
		_primaryStage.show();
	}
	
	public void speak(String speech) {
		
		// Bash command for speaking out the clue.
		String speakClueCmd = "echo \"" + speech + "\" | festival --tts";
		
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", speakClueCmd);
		try {
			Process process = builder.start();
		}
		catch (IOException e) {
			System.out.println("Error with using festival to read out the question.");
			e.printStackTrace();
		}
	}
}
