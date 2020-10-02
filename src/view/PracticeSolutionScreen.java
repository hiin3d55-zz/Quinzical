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

/**
 * This class represents the screen that gets displayed when the user has submitted the correct
 * answer or used all three attempts.
 * 
 * @author Sherman Chin, Dave Shin
 */
public class PracticeSolutionScreen {
	
//	private Stage _primaryStage;
	private BorderPane _pane;
	private Button _returnBtn;
	private String _clue;
	private String _solution;
	
	public PracticeSolutionScreen(BorderPane pane, String clue, String solution) {
//		_primaryStage = primaryStage;
		_pane = pane;
		_returnBtn = new Button("Return");
		_clue = clue;
		_solution = solution;
	}
	
	public void displayCorrect() {
		Text correctMsg = new Text("Correct!");
		String sayCorrectCmd = "echo \"Correct\" | festival --tts";
		
//		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", sayCorrectCmd);
//		try {
//			Process process = builder.start();
//		} catch (IOException e) {
//			System.out.println("Error with using festival to read out the question.");
//			e.printStackTrace();
//		}
//				
		setUpAndShow(correctMsg);
	}
	
	public void displayIncorrect() {
		Text incorrectMsg = new Text("Clue: " + _clue + "\nThe actual answer is: " + _solution);
		
		// Sound out to the user that their attempt is incorrect and tell them the correct answer.
		String sayIncorrectCmd = "echo \"The actual answer is " + _solution + "\" | festival --tts";		
//		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", sayIncorrectCmd);
//		try {
//			Process process = builder.start();
//			process.toString();
//		}
//		catch (IOException e) {
//			System.out.println("Error with using festival to read out the question.");
//			e.printStackTrace();
//		}
		setUpAndShow(incorrectMsg);
	}
	
	public void setUpAndShow(Text msg) {
		handleEvents();
		
		BorderPane solutionPane = new BorderPane();
		VBox solutionBox = new VBox();
		
		solutionBox.getChildren().addAll(msg, _returnBtn);
		
		solutionPane.setCenter(solutionBox);
		
//		_primaryStage.setScene(new Scene(solutionPane, 600, 400));
//		_primaryStage.show();
	}
	
	public void handleEvents() {
		_returnBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
        	public void handle(ActionEvent event) {
        		PracticeModule practiceMod = new PracticeModule(_pane);
        		practiceMod.display();
        	}
		});
	}
}
