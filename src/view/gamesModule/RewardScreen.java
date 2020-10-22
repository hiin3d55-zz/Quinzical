package view.gamesModule;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
	private Button _saveGameBtn;
	private Button _playAgnBtn;
	private Score _score;
	private QuestionBank _questionBank;
	private TextField _userName;
	private VBox _rewardBox;
	
	public RewardScreen(BorderPane pane) {
		_pane = pane;
		
		_rewardBox = new VBox();
		
		_saveGameBtn = new Button("Save");
		_saveGameBtn.getStyleClass().add("golden-button");
		
		_playAgnBtn = new Button("Play Again");
		_playAgnBtn.getStyleClass().add("golden-button");
		
		_userName = new TextField();
		
		_score = new Score();
		_questionBank = new QuestionBank(true);
	}
	
	public void display() {
		handleEvents();
		
		_rewardBox.getStyleClass().add("center-screen-box");
		
		Text congratulationMsg = new Text("Congratulations! All Questions Attempted!");
		congratulationMsg.getStyleClass().add("header-msg");

		Text infoMsg = new Text("Your final score is");
		infoMsg.getStyleClass().add("normal-text");
		infoMsg.setStyle("-fx-fill: #EAEAEA;");
		
		Text scoreText = new Text(Integer.toString(_score.getScore()));
		scoreText.getStyleClass().addAll("information-text");
		scoreText.setStyle("-fx-fill: #E0FFFF;");
		
		Text saveScoreText = new Text("Save your score under the name: ");
		saveScoreText.getStyleClass().add("information-text");
		
		
		_rewardBox.getChildren().addAll(congratulationMsg, infoMsg, scoreText, saveScoreText, _userName, _saveGameBtn);
		_pane.setCenter(_rewardBox);
		_pane.getBottom().getStyleClass().removeAll("invisible-component");
	}
	
	private void displaySaved() {
		VBox userSavedBox = new VBox();
		userSavedBox.getStyleClass().add("center-screen-box");
		Text header = new Text(_userName.getText() + ", You are ");
		header.getStyleClass().add("information-text");
		header.setWrappingWidth(600);
		header.setTextAlignment(TextAlignment.CENTER);
		
		Text userRanking = new Text("Number " + 1 + "!");
		userRanking.getStyleClass().add("header-msg");
		
		userSavedBox.getChildren().addAll(header, userRanking, _playAgnBtn);
		
		_pane.setCenter(userSavedBox);
		_pane.getBottom().getStyleClass().add("invisible-component");
	}
	
	public void handleEvents() {
		_saveGameBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
        	public void handle(ActionEvent event) {
				//save score here
				//display score saved screen
				displaySaved();
				
				_score.resetScore();
				_questionBank.resetGame();
				//Updates the score
				Text scoreText = (Text)_pane.getTop();
				_score = new Score();
				scoreText.setText("Current Score: " + _score.getScore());
        	}
		});
		
		_playAgnBtn.setOnAction(new EventHandler<ActionEvent>() {
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
