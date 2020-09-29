package view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SolutionScreen {
	
	private Stage _primaryStage;
	private Button _returnBtn;
	private String _solution;
	
	public SolutionScreen(Stage primaryStage) {
		_primaryStage= primaryStage;
		_returnBtn = new Button("Return");
		_solution = "Not yet implemented.";
		
		_returnBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
        	public void handle(ActionEvent event) {
        		GamesModule gamesMod = new GamesModule(_primaryStage);
        		gamesMod.display();
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
		
		// Record the increased winnings.
		updateWinnings(true);
		
		setUpAndShow(correctMsg);
	}
	
	public void displayIncorrect() {
		Text incorrectMsg = new Text("Incorrect. The actual answer is: " + _solution);
		
		// Sound out to the user that their attempt is incorrect and tell them the correct answer.
		String sayIncorrectCmd = "echo \"Incorrect the actual answer is " + _solution + "\" | festival --tts";		
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", sayIncorrectCmd);
		try {
			Process process = builder.start();
			process.toString();
		}
		catch (IOException e) {
			System.out.println("Error with using festival to read out the question.");
			e.printStackTrace();
		}
		
		// Record the decreased winnings.
		updateWinnings(false);
		
		setUpAndShow(incorrectMsg);
	}
	
	public void setUpAndShow(Text msg) {
		GridPane _solutionPane = new GridPane();
		_solutionPane.add(msg, 0, 0);
		_solutionPane.add(_returnBtn, 0, 1);
		
		_primaryStage.setScene(new Scene(_solutionPane, 600, 400));
		_primaryStage.show();
	}
	
	public void updateWinnings(boolean correct) {
		// Not yet implemented.
	}
}
