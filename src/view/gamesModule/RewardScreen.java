package view.gamesModule;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.QuestionBank;
import model.Score;
import view.WelcomeScreen;

/**
 * This class represents the reward screen which is a screen where the reward is displayed. This screen gets displayed
 * when the user have answered all available question. On this screen, the user can view their score and has the option 
 * to play again. If the user decides to play again, the score is reset to zero.
 * 
 * @author Dave Shin
 *
 */
public class RewardScreen {
	
	private BorderPane _pane;
	private Button _playAgainBtn;
	private Score _score;
	private QuestionBank _questionBank;
	
	public RewardScreen(BorderPane pane) {
		_pane = pane;
		_playAgainBtn = new Button("Play again!");
		_playAgainBtn.getStyleClass().add("golden-button");
		
		_score = new Score();
		_questionBank = new QuestionBank(true);
	}
	
	public void display() {
		handleEvents();
		
		VBox rewardBox = new VBox();
		rewardBox.getStyleClass().add("center-screen-box");
		
		Text congratulationMsg = new Text("Congratulations! All Questions Attempted!");
		congratulationMsg.getStyleClass().add("header-msg");

		Text infoMsg = new Text("Your final score is");
		infoMsg.getStyleClass().add("normal-text");
		infoMsg.setStyle("-fx-fill: #EAEAEA;");
		
		Text scoreText = new Text(Integer.toString(_score.getScore()));
		scoreText.getStyleClass().addAll("information-text");
		scoreText.setStyle("-fx-fill: #E0FFFF;");
		
		rewardBox.getChildren().addAll(congratulationMsg, infoMsg, scoreText, _playAgainBtn);
		_pane.setCenter(rewardBox);
		_pane.getBottom().getStyleClass().removeAll("invisible-component");
	}
	
	public void handleEvents() {
		_playAgainBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
        	public void handle(ActionEvent event) {
				_score.resetScore();
				_questionBank.resetGame();
				//Updates the score
				Text scoreText = (Text)_pane.getTop();
				_score = new Score();
				scoreText.setText("Current Score: " + _score.getScore());
				
        		WelcomeScreen welcomeScrn = new WelcomeScreen(_pane);
        		welcomeScrn.display();
        	}
		});
	}
}
