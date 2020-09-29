package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AnswerScreen {
	
	private Stage _primaryStage;
	private TextField _attempt;
	private Button _submitBtn;
	private Button _dontKnowBtn;
	
	public AnswerScreen(Stage primaryStage) {
		_primaryStage = primaryStage;
		_attempt = new TextField("Type your answer here");
		_submitBtn = new Button("Submit");
		_dontKnowBtn = new Button("Don\'t know");
		
		_submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.out.println("Submit!");
			}
		});
		
		_dontKnowBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				GamesModule gamesMod = new GamesModule(_primaryStage);
        		gamesMod.display();
			}
		});
	}
	
	public void display() {
		GridPane answerPane = new GridPane();
		Text instruction = new Text();
		instruction.setText("Listen to the clue then answer the question.");
		answerPane.add(instruction, 0, 0);
		
		answerPane.add(_attempt, 0, 1);
		answerPane.add(_dontKnowBtn, 0, 2);
		
		_primaryStage.setScene(new Scene(answerPane, 600, 400));
		_primaryStage.show();
	}
}
