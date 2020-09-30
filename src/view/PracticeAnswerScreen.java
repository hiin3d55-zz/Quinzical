package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
		
		Button submit = new Button("Submit");
		
		submit.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				if (attempt.getText().toLowerCase() != _answer.toLowerCase() && _count > 1) {
					_count--;
					if (_count == 1) {
						System.out.println("two tries");
					}
					attemptsCountText.setText("Number of attempts remaining: " + (_count));
				} else {
					System.out.println(attempt.getText().toLowerCase() == _answer.toLowerCase());
				}
			}
		});
		
		Text hint = new Text("Hint: The first letter of the answer is \"" + _answer.charAt(0) + "\"");
		
		answerBox.getChildren().addAll(instruction, attempt, submit, attemptsCountText, hint);
		
		answerPane.setCenter(answerBox);
		
		_primaryStage.setScene(new Scene(answerPane, 600, 400));
		_primaryStage.show();
	}
	
//	public void speakClue() {
//		
//		// Bash command for speaking out the clue.
//		String speakClueCmd = "echo \"" + _question + "\" | festival --tts";
//		
//		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", speakClueCmd);
//		try {
//			Process process = builder.start();
//		}
//		catch (IOException e) {
//			System.out.println("Error with using festival to read out the question.");
//			e.printStackTrace();
//		}
//	}
}
