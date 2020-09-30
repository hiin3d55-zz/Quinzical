package view;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.QuestionBank;

/**
 * This class represents the screen of Games Module. It displays the question board and the user is able to choose the
 * question that they want to answer.
 * 
 * @author Dave Shin
 *
 */
public class GamesModule {
	
	private Stage _primaryStage;
	private ArrayList<Question> _questions; // List of questions that are displayed on screen.
	
	public GamesModule(Stage primaryStage) {
		_primaryStage = primaryStage;
		_questions = new ArrayList<Question>();
	}
	
	public void display() {
		GridPane gamesModPane = new GridPane();
		Text instruction = new Text();
		instruction.setText("Please choose a clue to be read out. You can only choose the lowest money value "
				+ "for each category.");
		gamesModPane.add(instruction, 0, 0);
		
		// QuestionBank retrieves the data from the backend. The argument is true because we are in Games Module.
		QuestionBank questionBank = new QuestionBank(true); 
		String[] categoriesStrArray = questionBank.requestCategory();
		
		int categoryIdx = 0;
		
		for (String categoryStr : categoriesStrArray) {
			Text categoryText = new Text(categoryStr + " ");
			gamesModPane.add(categoryText, categoryIdx, 1);
			
			String[] clues = questionBank.requestClueForCategory(categoryStr);
			
			int amount = 100; // $100 is the minimum amount.
			int clueIdx = 2; // Start from 2 because 0 is for the instructions and 1 is for categories.
			
			for (String clue : clues) {
				Question question = new Question(amount, clue, categoryStr);
				_questions.add(question);
				gamesModPane.add(question.getButton(), categoryIdx, clueIdx);
				
				clueIdx++;
				amount += 100;
			}
			
			categoryIdx++;
			clueIdx = 2;
			amount = 100;
		}
		
		// When a clue button is pressed.
		for (Question pressedQuestion : _questions) {
			pressedQuestion.getButton().setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if (pressedQuestion.checkIfItIsMin()) {
						
						System.out.println("hello hi");
						
						_questions.remove(pressedQuestion);
						gamesModPane.getChildren().remove(pressedQuestion.getButton());
						
						// Make the next minimum clue the minimum clue because the current minimum clue is removed.
						for (Question question : _questions) {
							if ( question.getCategory().equals(pressedQuestion.getCategory()) 
									&& question.getAmount() == (pressedQuestion.getAmount() + 100) ) {
								question.nowMin();
							}
						}
						
						AnswerScreen answerScrn = new AnswerScreen(_primaryStage, pressedQuestion);
						answerScrn.display();
					}
				}
			});
		}
		
		_primaryStage.setScene(new Scene(gamesModPane, 600, 400));
		_primaryStage.show();
	}
}
