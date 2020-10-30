package quinzical.view.gamesModule;

import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import quinzical.model.Score;
import quinzical.view.Question;
import quinzical.view.SolutionScreen;

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
	
	/**
	 * Create GUI texts informing user is correct and increase user's score
	 */
	public void displayCorrect() {
		Text correctMsg = new Text("Correct!");
		
		_adjuster.speak("Correct");
		
		// Record the increased score.
		_score.updateScore(_question.getAmount());
		
		setUpAndShow(correctMsg);
	}
	
	/**
	 * Create GUI texts informing user is not correct and decrease user's score
	 */
	public void displayIncorrect() {
		// Record the decreased winnings.
		_score.updateScore(-_question.getAmount());
		displayDontKnow();
	}
	
	/**
	 * Create GUI texts informing user did not get the answer correct / no answer given.
	 */
	public void displayDontKnow() {
		Text incorrectMsg = new Text("The actual answer is: " + _solution);
		
		// Sound out to the user that their attempt is incorrect and tell them the correct answer.
		_adjuster.speak("The actual answer is " + _solution);

		setUpAndShow(incorrectMsg);
	}
	
	/**
	 * Create GUI components and layout screen for games solution screen
	 * @param messages The texts informing if user is correct / incorrect
	 */
	private void setUpAndShow(Text msg) {
		handleEvents();
		updateScoreText();
		
		msg.getStyleClass().add("information-text");
		
		_centerBox.getChildren().addAll(msg, _returnBtn);
		
		_pane.setCenter(_centerBox);
		
		//Remove sound buttons and macron box
		_pane.setLeft(null);
		_pane.setRight(null);
	}
	
	/**
	 * Update the user's score.
	 */
	private void updateScoreText() {
		Text scoreText = (Text)_pane.getTop();
		scoreText.setText("Current Score: " + _score.getScore());
	}
	
	/**
	 * Add listeners to return button, taking user back to Games Module screen.
	 */
	private void handleEvents() {
		_returnBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
        	public void handle(ActionEvent event) {
				GamesModule gamesMod = new GamesModule(_pane);
        		gamesMod.display();
        		_adjuster.stopSpeech();
        	}
		});
	}
}