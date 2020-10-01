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
	private QuestionBank _questionBank;
	private GridPane _gamesModPane;
	private Scene _gamesModScene;
	
	public GamesModule(Stage primaryStage) {
		_primaryStage = primaryStage;
		_questions = new ArrayList<Question>();
		_gamesModPane = new GridPane();
		_gamesModScene = new Scene(_gamesModPane, 600, 400);
		
		// QuestionBank retrieves the data from the backend. The argument is true because we are in Games Module.
		_questionBank = new QuestionBank(true);
	}
	
	public void initialise() {
		Text instruction = new Text();
		instruction.setText("Please choose a clue to be read out. You can only choose the lowest money value "
				+ "for each category.");
		_gamesModPane.add(instruction, 0, 0);
		
		String[] categoriesStrArray = _questionBank.requestCategory();
		
		int categoryIdx = 0;
		for (String categoryStr : categoriesStrArray) {
			Text categoryText = new Text(categoryStr + " ");
			_gamesModPane.add(categoryText, categoryIdx, 1);
			
			String[] clues = _questionBank.requestClueForCategory(categoryStr);
			
			int clueIdx = 2; // Start from 2 because 0 is for the instructions and 1 is for categories.
			
			int valueIdx = 0;
			for (String clue : clues) {
				int amount = _questionBank.getValues(categoryStr)[valueIdx];
						
				Question question = new Question(amount, clue, categoryStr, 
						_questionBank.answerForClue(categoryStr, clue));
				_questions.add(question);
				_gamesModPane.add(question.getButton(), categoryIdx, clueIdx);
				
				clueIdx++;
				valueIdx++;
				amount += 100;
			}
			
			categoryIdx++;
			clueIdx = 2;
			valueIdx = 0;
		}
	}
	
	public void display() {
		handleEvents();
		_primaryStage.setScene(_gamesModScene);
		_primaryStage.show();
	}
	
	public GridPane getGamesModPane() {
		return _gamesModPane;
	}
	
	public void handleEvents() {
		// When a clue button is pressed.
		for (Question pressedQuestion : _questions) {
			pressedQuestion.getButton().setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					if (pressedQuestion.checkIfItIsMin()) {
						_questions.remove(pressedQuestion);
						_gamesModPane.getChildren().remove(pressedQuestion.getButton());
						
						_questionBank.updateClue(pressedQuestion.getCategory(), pressedQuestion.getClue());
								
						// Make the next minimum clue the minimum clue because the current minimum clue is removed.
						for (Question question : _questions) {
							if ( question.getCategory().equals(pressedQuestion.getCategory()) 
									&& question.getAmount() == (pressedQuestion.getAmount() + 100) ) {
								question.nowMin();
							}
						}
									
						AnswerScreen answerScrn = new AnswerScreen(_primaryStage, pressedQuestion, GamesModule.this);
						answerScrn.display();
					}
				}
			});
		}
	}
}
