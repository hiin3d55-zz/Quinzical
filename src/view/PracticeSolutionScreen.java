package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * This class represents the screen that gets displayed when the user has submitted the correct
 * answer or used all three attempts.
 * 
 * @author Sherman Chin, Dave Shin
 */
public class PracticeSolutionScreen {
	
	private BorderPane _pane;
	private Button _returnBtn;
	private String _clue;
	private String _solution;
	
	public PracticeSolutionScreen(BorderPane pane, String clue, String solution) {
		_pane = pane;
		_returnBtn = new Button("Return to Practice Module");
		_returnBtn.getStyleClass().add("golden-button");
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
		List<Text> messages = new ArrayList<Text>();
		messages.add(correctMsg);
		setUpAndShow(messages);
	}
	
	public void displayIncorrect() {
		Text clueMsg = new Text("Clue: " + _clue);
		clueMsg.getStyleClass().addAll("normal-text", "information-text");
		clueMsg.setWrappingWidth(500);
		clueMsg.setTextAlignment(TextAlignment.CENTER);
		
		Text answerMsg = new Text("Answer: " + _solution);
		answerMsg.getStyleClass().addAll("normal-text", "information-text");
		
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
		
		List<Text> messages  = new ArrayList<Text>();
		messages.add(clueMsg);
		messages.add(answerMsg);
		setUpAndShow(messages);
	}
	
	
	
	private void setUpAndShow(List<Text> messages) {
		handleEvents();
				
		VBox solutionBox = new VBox();
		solutionBox.getStyleClass().add("center-screen-box");
		
		solutionBox.getChildren().addAll(messages);
		solutionBox.getChildren().addAll(_returnBtn);
		_pane.setCenter(solutionBox);
	}
	
	private void handleEvents() {
		_returnBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
        	public void handle(ActionEvent event) {
        		PracticeModule practiceMod = new PracticeModule(_pane);
        		practiceMod.display();
        	}
		});
	}
}
