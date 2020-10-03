package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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

	public WelcomeScreen(BorderPane pane) {
		_pane = pane;
		_pracModBtn = new Button("Practice Module");
		_pracModBtn.getStyleClass().addAll("golden-button");
		_gamesModBtn = new Button("Games Module");
		_gamesModBtn.getStyleClass().addAll("golden-button");
	}

	public void display() {
		handleEvents();
		
		Text welcomeMessage = new Text("Welcome to Quinzical!");
		welcomeMessage.getStyleClass().addAll("header-msg", "normal-text");
		
		Text optionsText = new Text("Please select from one of the following options:");
		optionsText.getStyleClass().add("normal-text");
		// Display the main menu.
		VBox welcomeBox = new VBox();
		welcomeBox.getChildren().addAll(welcomeMessage, optionsText, _gamesModBtn,_pracModBtn);
		welcomeBox.getStyleClass().add("center-screen-box");
		VBox.setMargin(welcomeMessage, new Insets(0, 0, 15, 0));
		
		_pane.setCenter(welcomeBox);
		
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
				GamesModule gamesMod = new GamesModule(_pane);
				gamesMod.display();
			}
		});
	}
}

