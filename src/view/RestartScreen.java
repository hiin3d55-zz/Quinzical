package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import model.QuestionBank;
import model.Score;

/**
 * This class represents the screen when users wants to restart the game.
 * @author se2062020
 *
 */
public class RestartScreen {
	private BorderPane _pane;
	private Button _yesBtn;
	private Button _noBtn;
	private Button _returnBtn;
	
	public RestartScreen(BorderPane pane) {
		_pane = pane;
		_yesBtn = new Button("Yes");
		_yesBtn.getStyleClass().add("golden-button");
		
		_noBtn = new Button("No");
		_noBtn.getStyleClass().add("normal-button");
		
		_returnBtn = new Button("Return to main menu");
		_returnBtn.getStyleClass().add("golden-button");
	}
	
	/**
	 * Displays the screen asking user for restart confirmation
	 */
	public void display() {
		VBox centerBox = new VBox();
		centerBox.getStyleClass().add("center-screen-box");
		
		Text header = new Text("Confirm to Restart Game?");
		header.getStyleClass().addAll("header-msg");
		
		centerBox.getChildren().addAll(header, _yesBtn, _noBtn);
		
		handleEvents();
		
		_pane.setCenter(centerBox);
		
	}
	
	/**
	 * Displays a screen telling the user the game has successfully restarted
	 */
	private void displayRestarted() {
		VBox centerBox = new VBox();
		centerBox.getStyleClass().add("center-screen-box");
		
		Text restartedHeader = new Text("Game has been restarted!");
		restartedHeader.getStyleClass().addAll("header-msg");
	
		
		centerBox.getChildren().addAll(restartedHeader, _returnBtn);
		
		_pane.setCenter(centerBox);
	}
	
	private void handleEvents() {
		_yesBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				QuestionBank questionBank = new QuestionBank(true);
				questionBank.resetGame();
				
				Score score = new Score();
				score.resetScore();
				
				Text scoreText = (Text)_pane.getTop();
				score = new Score();
				scoreText.setText("Current Score: " + score.getScore());
				
				displayRestarted();
			}
		});
		
		_noBtn.setOnAction(new ReturnWelcomeScreen());
		_returnBtn.setOnAction(new ReturnWelcomeScreen());
	}
	
	private class ReturnWelcomeScreen implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			WelcomeScreen welcomeScrn = new WelcomeScreen(_pane);
			welcomeScrn.display();
		}
		
	}
	

}
