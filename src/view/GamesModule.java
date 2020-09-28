package view;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.QuestionBank;

public class GamesModule {
	
	private Stage _primaryStage;
	private ArrayList<Question> _questions;
	
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
		
		QuestionBank questionBank = new QuestionBank(true);
		String[] categoriesStrArray = questionBank.requestCategory();
		
		
		int categoryIdx = 0;
		for (String categoryStr : categoriesStrArray) {
			Text categoryText = new Text(categoryStr + " ");
			gamesModPane.add(categoryText, categoryIdx, 1);
			
			String[] clues = questionBank.requestClueForCategory(categoryStr);
			int amount = 100;
			int clueIdx = 2;
			for (String clue : clues) {
				Question question = new Question(amount, clue);

				_questions.add(question);
				gamesModPane.add(question.getButton(), categoryIdx, clueIdx);
				
				clueIdx++;
				amount += 100;
			}
			
			categoryIdx = 2;
			clueIdx++;
			amount = 100;
		}
		
		
		
		_primaryStage.setScene(new Scene(gamesModPane, 600, 400));
		_primaryStage.show();
	}
}
