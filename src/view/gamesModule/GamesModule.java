package view.gamesModule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.QuestionBank;
import view.Module;
import view.Question;

/**
 * This class represents the screen of Games Module. It displays the question
 * board and the user is able to choose the question that they want to answer.
 * 
 * @author Dave Shin, Sherman Chin
 *
 */
public class GamesModule extends Module {
	private ArrayList<ArrayList<Question>> _allQuestions; // Keeps track of which clues have been revealed.

	public GamesModule(BorderPane pane) {
		super(pane, new QuestionBank(true));

		// QuestionBank retrieves the data from the backend.
		_allQuestions = new ArrayList<ArrayList<Question>>();
	}

	/**
	 * Depending if all clues have been asked, a reward screen / Games Module screen
	 * will be layed out
	 */
	protected void displayScreen() {

		// When there are no clues left, treat the user to the Reward Screen.
		if (_questionBank.requestCategory().length == 0) {
			RewardScreen rewardScrn = new RewardScreen(_pane);
			rewardScrn.display();
		} else { // Keep displaying the GamesModule if there are still clues remaining.
			createGUI();
			handleEvents();
		}
	}

	/**
	 * This method lays out the GUI for Games Module
	 */
	private void createGUI() {

		Text header = new Text("Games Module!");
		header.getStyleClass().add("header-msg");
		Text instruction1 = new Text("Please choose a clue to be read out");
		instruction1.getStyleClass().add("normal-text");
		Text instruction2 = new Text("You can only choose the lowest money value for each category.");
		instruction2.getStyleClass().add("normal-text");

		_centerBox.getChildren().addAll(header, instruction1, instruction2);

		String[] categoriesStrArray = _questionBank.requestCategory();

		HBox clueGrid = new HBox();
		clueGrid.getStyleClass().addAll("center-screen-box", "clue-grid");

		// Set out the GUI for Games Module. The end product is a screen with multiple
		// buttons for the clues.
		for (String categoryStr : categoriesStrArray) {
			VBox categoryColumn = new VBox();
			categoryColumn.getStyleClass().addAll("center-screen-box");

			Text categoryText = new Text(categoryStr);
			categoryText.getStyleClass().add("normal-text");
			categoryText.setStyle("-fx-fill: #EAEAEA");

			categoryColumn.getChildren().add(categoryText);

			String[] clues = _questionBank.requestClueForCategory(categoryStr);

			ArrayList<Question> questions = new ArrayList<Question>();

			// Create the buttons for the clues.
			int valueIdx = 0;
			for (String clue : clues) {
				int amount = _questionBank.getValues(categoryStr)[valueIdx];

				Question question = new Question(amount, clue, categoryStr,
						_questionBank.answerForClue(categoryStr, clue));

				questions.add(question);

				Button clueButton = question.getButton();

				if (valueIdx == 0) {
					clueButton.getStyleClass().add("golden-button");
				} else {
					clueButton.getStyleClass().add("non-clickable-button");
				}
				categoryColumn.getChildren().add(question.getButton());
				valueIdx++;
			}

			_allQuestions.add(questions);

			// Add checks if the category is International
			if (categoryStr.equals("International")) {

				// Don't show International category because haven't finished two categories
				if (categoriesStrArray.length > 4) {
					clueGrid.getChildren().remove(categoryColumn);

				} else {
					// Add special styling to the International category.
					VBox unlockedBox = new VBox();
					unlockedBox.getStyleClass().addAll("center-screen-box", "boundary-box");
					unlockedBox.setStyle("-fx-background-color: transparent");

					categoryColumn.setAlignment(Pos.TOP_CENTER);

					// This parent node is just to give height to the component so that the layout
					// looks better.
					VBox container = new VBox();
					container.setMinHeight(270);
					container.getChildren().add(categoryColumn);

					unlockedBox.getChildren().add(container);

					try {
						Image image = new Image(new FileInputStream("resources/lock-unlocked-24.png"));
						ImageView imageView = new ImageView(image);

						imageView.setFitHeight(30);
						imageView.setFitWidth(30);

						imageView.setPreserveRatio(true);						

						unlockedBox.getChildren().add(imageView);

					} catch (FileNotFoundException e) {
						Text notifyText = new Text("Unlocked!");
						notifyText.getStyleClass().add("normal-text");
						unlockedBox.getChildren().add(notifyText);
					}

					clueGrid.getChildren().add(unlockedBox);
				}

			} else {
				clueGrid.getChildren().add(categoryColumn);
			}
		}

		_centerBox.getChildren().add(clueGrid);
		_pane.setCenter(_centerBox);
	}

	/**
	 * Add listeners to each buttons
	 */
	private void handleEvents() {

		// When a clue button is pressed.
		for (ArrayList<Question> questionsForCategory : _allQuestions) {
			for (Question pressedQuestion : questionsForCategory) {
				pressedQuestion.getButton().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {

						// We need to check if the clue that is pressed has the minimum value for that
						// category because
						// only the clues that have the minimum value in their category can be selected.
						if (questionsForCategory.indexOf(pressedQuestion) == 0) {

							// Do operations that removes the pressed clue so that it is removed from the
							// Games Module.
							_allQuestions.get(_allQuestions.indexOf(questionsForCategory)).remove(pressedQuestion);
							_questionBank.updateClue(pressedQuestion.getCategory(), pressedQuestion.getClue());

							// If a category contains no clues, remove that category from _allQuestions.
							if (_allQuestions.get(_allQuestions.indexOf(questionsForCategory)).isEmpty()) {
								_allQuestions.remove(questionsForCategory);
							}

							GamesAnswerScreen answerScrn = new GamesAnswerScreen(_pane, pressedQuestion);
							answerScrn.display();
							answerScrn.startTimer();
						}
					}
				});
			}
		}
	}
}
