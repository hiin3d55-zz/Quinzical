package view;

import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import model.Score;

/**
 * This class represents the screen that get shown after the user submits their answer.
 * 
 * @author Dave Shin
 *
 */
public class GamesSolutionScreen extends SolutionScreen{
	
	private Score _score;
	private Question _question;
	
	public GamesSolutionScreen(BorderPane pane, Question question, String solution) {
		super(pane, solution, new Button("Return to Games Module"));
		_score = new Score();
		_question = question;
	}
	
	public void displayCorrect() {
		Text correctMsg = new Text("Correct!");
		
		speak("Correct");
		
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
		speak("The actual answer is " + _solution);

		setUpAndShow(incorrectMsg);
	}
	
	private void setUpAndShow(Text msg) {
		handleEvents();
		updateScoreText();
		
		msg.getStyleClass().addAll("normal-text", "information-text");
		
		_centerBox.getChildren().addAll(msg, _returnBtn);
		
		_pane.setCenter(_centerBox);
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