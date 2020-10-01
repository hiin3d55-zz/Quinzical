package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.QuestionBank;

public class PracticeModule {
	
	private Stage _primaryStage;
	private QuestionBank _questionBank;
	
	public PracticeModule(Stage primaryStage) {
		_primaryStage = primaryStage;
		_questionBank = new QuestionBank(false);
	}
	
	public void display() {
		BorderPane pracModPane = new BorderPane();
		VBox pracModBox = new VBox();
		
		Text instruction = new Text();
		instruction.setText("Please choose any category");
		pracModBox.getChildren().add(instruction);
		
		String[] categories = _questionBank.requestCategory();
		
		for (String category: categories) {
			Button categoryBtn = new Button(category);
			categoryBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					String[] clue = _questionBank.requestClueForCategory(category);
					String[] answer = _questionBank.answerForClue(category, clue[0]);
					PracticeAnswerScreen answerScrn = new PracticeAnswerScreen(_primaryStage, clue[0], answer[0]);
					answerScrn.display();
				}
			});
			pracModBox.getChildren().add(categoryBtn);
		}
		
		pracModPane.setCenter(pracModBox);
		
		_primaryStage.setScene(new Scene(pracModPane, 600, 400));
		_primaryStage.show();
	}
}
