package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PracticeSolutionScreen {
	private String _solution;
	private Stage _primaryStage;
	private Button _returnBtn;
	
	public PracticeSolutionScreen(Stage primaryStage, String solution) {
		_solution = solution;
		_primaryStage = primaryStage;
		_returnBtn = new Button("Return");
		_returnBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
        	public void handle(ActionEvent event) {
        		PracticeModule practiceMod = new PracticeModule(_primaryStage);
        		practiceMod.display();
        	}
		});
	}
	
	public void displayCorrect() {
		Text correctMsg = new Text("Correct!");
		String sayCorrectCmd = "echo \"Correct\" | festival --tts";
		
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", sayCorrectCmd);
		try {
			Process process = builder.start();
		} catch (IOException e) {
			System.out.println("Error with using festival to read out the question.");
			e.printStackTrace();
		}
				
		setUpAndShow(correctMsg);
	}
	
	public void displayIncorrect() {
		Text incorrectMsg = new Text("The actual answer is: " + _solution);
		
		// Sound out to the user that their attempt is incorrect and tell them the correct answer.
		String sayIncorrectCmd = "echo \"The actual answer is " + _solution + "\" | festival --tts";		
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", sayIncorrectCmd);
		try {
			Process process = builder.start();
			process.toString();
		}
		catch (IOException e) {
			System.out.println("Error with using festival to read out the question.");
			e.printStackTrace();
		}
		setUpAndShow(incorrectMsg);
	}
	
	public void setUpAndShow(Text msg) {
		BorderPane solutionPane = new BorderPane();
		VBox solutionBox = new VBox();
		
		solutionBox.getChildren().addAll(msg, _returnBtn);
		
		solutionPane.setCenter(solutionBox);
		
		_primaryStage.setScene(new Scene(solutionPane, 600, 400));
		_primaryStage.show();
	}
	
}
