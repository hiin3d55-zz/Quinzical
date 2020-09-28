package view;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.QuestionBank;

public class GamesModule {
	
	private Stage _primaryStage;
	private ArrayList<Button> _buttons;
	
	public GamesModule(Stage primaryStage) {
		_primaryStage = primaryStage;
		_buttons = new ArrayList<Button>();
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
			
			categoryIdx++;
		}
		
		_primaryStage.setScene(new Scene(gamesModPane, 600, 400));
		_primaryStage.show();
	}
}
