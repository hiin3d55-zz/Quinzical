package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomeScreen {
	
	private Stage _primaryStage;
	
	private Button _pracModBtn;
	private Button _gamesModBtn;
	
	public WelcomeScreen(Stage primaryStage) {
		_primaryStage = primaryStage;
		
		_pracModBtn = new Button("The Practice Module");
		_gamesModBtn = new Button("The Games Module");
		
		// Rest of this constructor are event handlers.
		_pracModBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Practice Module");
			}
		});
        
		_gamesModBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		GamesModule gamesMod = new GamesModule(_primaryStage);
        		gamesMod.display();
        	}
        });
	}
	
	public void display() {
		GridPane welcomePane = new GridPane();
		Text welcomeMessage = new Text();
		welcomeMessage.setText("Welcome to Quinzical!\nPlease select from one of the following options:");
		
		// Display the main menu.
		welcomePane.add(welcomeMessage, 0, 0);
		welcomePane.add(_pracModBtn, 0, 1);
		welcomePane.add(_gamesModBtn, 0, 2);
				
		_primaryStage.setScene(new Scene(welcomePane,600,400));
		_primaryStage.show();
	}
}

