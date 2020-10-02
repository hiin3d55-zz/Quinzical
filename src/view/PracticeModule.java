package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.QuestionBank;

/**
 * This class represents the screen that gets displayed when the user selects Practice Module from the WelcomeScreen.
 * The user is provided with category buttons so that they can choose which category question they want to answer.
 * 
 * @author Sherman Chin, Dave Shin
 *
 */
public class PracticeModule {
	
	private BorderPane _pane;
	private QuestionBank _questionBank;
	
	public PracticeModule(BorderPane pane) {
		_pane = pane;
		_questionBank = new QuestionBank(false); // False because we are in Practice Module.
	}
	
	public void display() {
		VBox pracModBox = new VBox();
		pracModBox.getStyleClass().add("center-screen-box");
		
		Text instruction = new Text("Please choose any category");
		instruction.getStyleClass().add("normal-text");
		
		FlowPane categoryBox = new FlowPane();
		categoryBox.getStyleClass().add("category-grid");
		
		pracModBox.getChildren().addAll(instruction, categoryBox);
		
		String[] categories = _questionBank.requestCategory();
		
		// Detect the button press.
		for (String category: categories) {
			Button categoryBtn = new Button(category);
			categoryBtn.getStyleClass().add("golden-button");
			
			categoryBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					String[] clue = _questionBank.requestClueForCategory(category);
					String[] answer = _questionBank.answerForClue(category, clue[0]);
					PracticeAnswerScreen answerScrn = new PracticeAnswerScreen(_pane, clue[0], answer);
					answerScrn.display();
				}
			});
			categoryBox.getChildren().add(categoryBtn);
		}
		
		_pane.setCenter(pracModBox);
		
		//Shows the main menu button at the bottom
		_pane.getBottom().getStyleClass().remove("invisible-component");
	}
}
