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
	private GridPane _gamesModPane;
	private Scene _gamesModScene;
	
	private QuestionBank _questionBank;
	private ArrayList<ArrayList<Question>> _allQuestions; // Keeps track of which clues have been revealed.
	
	public GamesModule(Stage primaryStage) {
		_primaryStage = primaryStage;
		_gamesModPane = new GridPane();
		_gamesModScene = new Scene(_gamesModPane, 600, 400);
		
		// QuestionBank retrieves the data from the backend. The argument is true because we are in Games Module.
		_questionBank = new QuestionBank(true);
		_allQuestions = new ArrayList<ArrayList<Question>>();
	}
	
	/**
	 * This method is only called once until the game is reset. This is because this method initialises the GamesModule.
	 */
	public void initialise() {
		Text instruction = new Text();
		instruction.setText("Please choose a clue to be read out. You can only choose the lowest money value "
				+ "for each category.");
		_gamesModPane.add(instruction, 0, 0);
		
		String[] categoriesStrArray = _questionBank.requestCategory();
		
		// Set out the GUI for Games Module. The end product is a screen with multiple buttons for the clues.
		int categoryIdx = 0;
		for (String categoryStr : categoriesStrArray) {
			
			Text categoryText = new Text(categoryStr + " ");
			_gamesModPane.add(categoryText, categoryIdx, 1);
			
			String[] clues = _questionBank.requestClueForCategory(categoryStr);
			
			ArrayList<Question> questions = new ArrayList<Question>();
			
			int clueIdx = 2; // Start from 2 because 0 is for the instructions and 1 is for categories.
			
			// Create the buttons for the clues.
			int valueIdx = 0;
			for (String clue : clues) {
				int amount = _questionBank.getValues(categoryStr)[valueIdx];
						
				Question question = new Question(amount, clue, categoryStr, 
						_questionBank.answerForClue(categoryStr, clue));
				
				questions.add(question);
				_gamesModPane.add(question.getButton(), categoryIdx, clueIdx);
				
				clueIdx++;
				valueIdx++;
			}
			
			_allQuestions.add(questions);
			
			categoryIdx++;
			clueIdx = 2;
			valueIdx = 0;
		}
	}
	
	public void display() {
		
		// When there are no clues left, treat the user to the Reward Screen.
		if (_questionBank.requestCategory().length == 0) {
			RewardScreen rewardScrn = new RewardScreen(_primaryStage);
			rewardScrn.display();
		} else { // Keep displaying the GamesModule if there are still clues remaining.
			handleEvents();
			_primaryStage.setScene(_gamesModScene);
			_primaryStage.show();
		}
	}
	
	public void handleEvents() {
		
		// When a clue button is pressed.
		for (ArrayList<Question> questionsForCategory : _allQuestions) {
			for (Question pressedQuestion : questionsForCategory) {
				pressedQuestion.getButton().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {	
						
						// We need to check if the clue that is pressed has the minimum value for that category because
						// only the clues that have the minimum value in their category can be selected.
						if (questionsForCategory.indexOf(pressedQuestion) == 0) {
							
							// Do operations that removes the pressed clue so that it is removed from the Games Module.
							_allQuestions.get(_allQuestions.indexOf(questionsForCategory)).remove(pressedQuestion);
							_gamesModPane.getChildren().remove(pressedQuestion.getButton());
							_questionBank.updateClue(pressedQuestion.getCategory(), pressedQuestion.getClue());
							
							// If a category contains no clues, remove that category from _allQuestions.
							if (_allQuestions.get(_allQuestions.indexOf(questionsForCategory)).isEmpty()) {
								_allQuestions.remove(questionsForCategory);
							}
										
							AnswerScreen answerScrn = new AnswerScreen(_primaryStage, pressedQuestion, 
									GamesModule.this);
							answerScrn.display();
						}
					}
				});
			}
		}
	}
}
