package quinzical;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import quinzical.model.Score;
import quinzical.view.WelcomeScreen;

/**
 * Quinzical game (Main class).
 * 
 * @author Dave Shin
 *
 */
public class Quinzical extends Application {
	private BorderPane _pane;
	private WelcomeScreen _welcomeScrn;
	
	public Quinzical() {
		_pane = new BorderPane();
		_pane.getStyleClass().add("background-screen");
		_welcomeScrn = new WelcomeScreen(_pane);
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Quinzical");
		
		setUpHeader();
		setUpFooter();
		
		_welcomeScrn.display();
		
		Scene scene = new Scene(_pane, 1280, 720);
		
		scene.getStylesheets().add("quinzical/application.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Sets the Header of Quinzical, which is showing the score of user.
	 */
	private void setUpHeader() {
		Score score = new Score();
		
		Text scoreText = new Text("Current Score: " + score.getScore());
		scoreText.getStyleClass().add("information-text");
		scoreText.setStyle("-fx-font-size: 23px");
		
		_pane.setTop(scoreText);
		BorderPane.setAlignment(scoreText, Pos.TOP_RIGHT);
	}
	
	/**
	 * Sets the footer of Quinzical, which is presenting a Main Menu button.
	 */
	private void setUpFooter() {
		Button menuBtn = new Button("Main Menu");
		menuBtn.getStyleClass().addAll("golden-button", "menu-button");
		menuBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				_welcomeScrn.display();
			}
		});
		_pane.setBottom(menuBtn);
		BorderPane.setAlignment(menuBtn, Pos.CENTER);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
