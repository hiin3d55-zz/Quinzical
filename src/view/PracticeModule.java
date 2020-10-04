package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import model.QuestionBank;

/**
 * This class represents the screen that gets displayed when the user selects Practice Module from the WelcomeScreen.
 * The user is provided with category buttons so that they can choose which category question they want to answer.
 * 
 * @author Sherman Chin, Dave Shin
 *
 */
public class PracticeModule extends Module{
	
	public PracticeModule(BorderPane pane) {
		super(pane, new QuestionBank(false));
	}
	
	/**
	 * This method lays out the GUI for Practice Module
	 */
	protected void displayScreen() {
		
		Text header = new Text("Practice Module!");
		header.getStyleClass().addAll("header-msg", "normal-text");
		Text instruction = new Text("Please choose any category");
		instruction.getStyleClass().add("normal-text");
		
		FlowPane categoryBox = new FlowPane();
		categoryBox.getStyleClass().add("category-grid");
		
		_centerBox.getChildren().addAll(header, instruction, categoryBox);
		
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
		
		_pane.setCenter(_centerBox);
	}
}
