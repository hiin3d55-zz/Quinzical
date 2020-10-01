package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.QuestionBank;
import model.Score;

/**
 * This class represents the reward screen which is a screen where the reward is displayed. This screen gets displayed
 * when the user have answered all available question. On this screen, the user can view their score and has the option 
 * to play again. If the user decides to play again, the score is reset to zero.
 * 
 * @author Dave Shin
 *
 */
public class RewardScreen {
	
	private Stage _primaryStage;
	private Button _playAgainBtn;
	private Score _score;
	private QuestionBank _questionBank;
	
	public RewardScreen(Stage primaryStage) {
		_primaryStage = primaryStage;
		_playAgainBtn = new Button("Play again!");
		_score = new Score();
		_questionBank = new QuestionBank(true);
	}
	
	public void display() {
		handleEvents();
		
		GridPane _rewardPane = new GridPane();
		Text rewardMsg = new Text("Congratualtions! You attempted all questions. Your final score is: " 
									+ Integer.toString(_score.getScore()));
		
		_rewardPane.add(rewardMsg, 0, 0);
		_rewardPane.add(_playAgainBtn, 0, 1);
		
		_primaryStage.setScene(new Scene(_rewardPane, 600, 400));
		_primaryStage.show();
	}
	
	public void handleEvents() {
		_playAgainBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
        	public void handle(ActionEvent event) {
				_score.resetScore();
				_questionBank.resetGame();
        		WelcomeScreen welcomeScrn = new WelcomeScreen(_primaryStage);
        		welcomeScrn.display();
        	}
		});
	}
}
