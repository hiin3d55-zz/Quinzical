package view;

import java.io.IOException;

import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Score;

/**
 * This class represents the screen that get shown after the user submits their answer.
 * 
 * @author Dave Shin
 *
 */
public class SolutionScreen {
	
	private BorderPane _pane;
	private Button _returnBtn;
	private String _solution;
	private Score _score;
	private Question _question;
	
	public SolutionScreen(BorderPane pane, Question question, String solution) {
		_pane = pane;
		_returnBtn = new Button("Return to Games Module");
		_returnBtn.getStyleClass().add("golden-button");
		_solution = solution;
		_score = new Score();
		_question = question;
	}
	
	public void displayCorrect() {
		Text correctMsg = new Text("Correct!");
//		
//		String sayCorrectCmd = "echo \"Correct\" | festival --tts";
//		
//		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", sayCorrectCmd);
//		try {
//			Process process = builder.start();
//			process.toString();
//		} catch (IOException e) {
//			System.out.println("Error with using festival to read out the question.");
//			e.printStackTrace();
//		}
		
		// Record the increased score.
		_score.updateScore(_question.getAmount());
		
		setUpAndShow(correctMsg);
	}
	
	public void displayIncorrect() {
		
		// Record the decreased winnings.
		_score.updateScore(-_question.getAmount());
		displayDontKnow();
	}
	
	public void displayDontKnow() {
		Text incorrectMsg = new Text("The actual answer is: " + _solution);
		
		// Sound out to the user that their attempt is incorrect and tell them the correct answer.
//		String sayIncorrectCmd = "echo \"The actual answer is " + _solution + "\" | festival --tts";		
//		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", sayIncorrectCmd);
//		try {
//			Process process = builder.start();
//			process.toString();
//		} catch (IOException e) {
//			System.out.println("Error with using festival to read out the question.");
//			e.printStackTrace();
//		}
//		
		setUpAndShow(incorrectMsg);
	}
	
	private void setUpAndShow(Text msg) {
		handleEvents();
		updateScoreText();
		
		msg.getStyleClass().addAll("normal-text", "information-text");
		
		VBox solutionBox = new VBox();
		solutionBox.getStyleClass().add("center-screen-box");
		
		solutionBox.getChildren().addAll(msg, _returnBtn);
		
		_pane.setCenter(solutionBox);
	}
	
	private void updateScoreText() {
		Text scoreText = (Text)_pane.getTop();
		scoreText.setText("Current Score: " + _score.getScore());
	}
	
	private void handleEvents() {
		_returnBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
        	public void handle(ActionEvent event) {
				GamesModule gamesMod = new GamesModule(_pane);
        		gamesMod.display();
        	}
		});
	}
}
