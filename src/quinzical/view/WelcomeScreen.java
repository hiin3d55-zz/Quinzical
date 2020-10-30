package quinzical.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import quinzical.model.QuestionBank;
import quinzical.view.gamesModule.GamesModule;
import quinzical.view.practiceModule.PracticeModule;
import quinzical.view.gamesModule.GamesModuleRequestCategory;

/**
 * This class represents the screen that gets displayed when the user starts the game.
 * 
 * @author Dave Shin
 *
 */
public class WelcomeScreen {

	private BorderPane _pane;
	private Button _pracModBtn;
	private Button _gamesModBtn;
	private Button _restartBtn;
	private Button _leaderBoardBtn;

	public WelcomeScreen(BorderPane pane) {
		_pane = pane;
		_pracModBtn = new Button("Practice Module");
		_pracModBtn.getStyleClass().addAll("golden-button");
		
		_gamesModBtn = new Button("Games Module");
		_gamesModBtn.getStyleClass().addAll("golden-button");
		
		_restartBtn = new Button("Restart Game");
		_restartBtn.getStyleClass().add("golden-button");
		
		_leaderBoardBtn = new Button("Leader Board");
		_leaderBoardBtn.getStyleClass().add("golden-button");

	}

	public void display() {
		handleEvents();
		
		Text welcomeMessage = new Text("Welcome to Quinzical!");
		welcomeMessage.getStyleClass().add("header-msg");
		
		Text optionsText = new Text("Please select from one of the following options:");
		optionsText.getStyleClass().add("normal-text");
		// Display the main menu.
		VBox welcomeBox = new VBox();
		welcomeBox.getChildren().addAll(welcomeMessage, optionsText, _gamesModBtn, _pracModBtn, _leaderBoardBtn, _restartBtn);
		welcomeBox.getStyleClass().add("center-screen-box");
		VBox.setMargin(welcomeMessage, new Insets(0, 0, 15, 0));
		
		_pane.setCenter(welcomeBox);
		
		//Remove sound buttons and macron box
		_pane.setLeft(null);
		_pane.setRight(null);
		
		//Hiddens the main menu button at the bottom
		_pane.getBottom().getStyleClass().add("invisible-component");
	}
	
	public void handleEvents() {
		_pracModBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PracticeModule pracMod = new PracticeModule(_pane);
				pracMod.display();
			}
		});

		_gamesModBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				QuestionBank questionBank = new QuestionBank(true);
				
				//If user has not chosen five categories to play with
				if (questionBank.isCategoryChosen() == false) {
					GamesModuleRequestCategory gamesModReqCategory = new GamesModuleRequestCategory(_pane);
					gamesModReqCategory.display();
					
				} else {
					GamesModule gamesMod = new GamesModule(_pane);
					gamesMod.display();
				}
			}
		});
		
		_restartBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				RestartScreen restartScrn = new RestartScreen(_pane);
				restartScrn.display();
			}
		});
		
		_leaderBoardBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LeaderBoardScreen leaderBoardScrn = new LeaderBoardScreen(_pane);
				leaderBoardScrn.display();
			}
		});
	}
}

