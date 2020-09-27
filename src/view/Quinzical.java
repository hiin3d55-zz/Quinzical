package view;

import javafx.application.Application;
import javafx.stage.Stage;

public class Quinzical extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Quinzical");
		
		WelcomeScreen welcomeScrn = new WelcomeScreen(primaryStage);
		welcomeScrn.display();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
