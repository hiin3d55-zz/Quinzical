package quinzical.view.gamesModule;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import quinzical.model.QuestionBank;
import quinzical.view.Module;
import quinzical.view.gamesModule.GamesModule;

/**
 * This class represents the screen where users choose five categories to play.
 * @author Sherman
 *
 */
public class GamesModuleRequestCategory extends Module {

	public GamesModuleRequestCategory(BorderPane pane) {
		super(pane, new QuestionBank(true));
	}

	/**
	 * Displays the screen where the user can select categories.
	 */
	@Override
	protected void displayScreen() {
		
		Text header = new Text("Games Module!");
		header.getStyleClass().add("header-msg");
		
		Text instruction = new Text("Please choose any five categories");
		instruction.getStyleClass().add("normal-text");
		
		Text warning = new Text("Choose upto and only five categories!");
		warning.getStyleClass().addAll("normal-text", "invisible-component");
		
		FlowPane unselectedCategoryBox = new FlowPane();
		unselectedCategoryBox.getStyleClass().add("category-grid");
		
		FlowPane selectedCategoryBox = new FlowPane();
		selectedCategoryBox.getStyleClass().addAll("category-grid");
		selectedCategoryBox.setMinHeight(75);
		
		String[] categories = _questionBank.requestCategory();
		
		//Add listeners to category buttons 
		for (String category: categories) {
			Button categoryBtn = new Button(category);
			categoryBtn.getStyleClass().add("golden-button");
			
			categoryBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {		
					
					//Only max five categories can be selected
					if (categoryBtn.getParent().equals(unselectedCategoryBox) && selectedCategoryBox.getChildren().size() < 5) {
						selectedCategoryBox.getChildren().add(categoryBtn);
						
						categoryBtn.getStyleClass().remove("golden-button");
						categoryBtn.getStyleClass().add("normal-button");
						
					//If user wants to de-select a category they chosed
					} else if (categoryBtn.getParent().equals(selectedCategoryBox)){
						unselectedCategoryBox.getChildren().add(categoryBtn);
						
						categoryBtn.getStyleClass().remove("normal-button");
						categoryBtn.getStyleClass().add("golden-button");
						
					} else {
						//Displays warning because user selected more than five categories.
						warning.getStyleClass().remove("invisible-component");
					}
				}
			});
			
			unselectedCategoryBox.getChildren().add(categoryBtn);
		}
		
		Button confirmBtn = new Button("Confirm");
		confirmBtn.getStyleClass().add("golden-button");
		
		confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (storeSelectedCategories(selectedCategoryBox) == false) {
					warning.getStyleClass().remove("invisible-component");
				} else {
					GamesModule gamesMod = new GamesModule(_pane);
					gamesMod.display();
				}
			}
		});
		
		Text selectText = new Text("Selected");
		selectText.getStyleClass().add("information-text");
		
		VBox selectionBox = new VBox();
		selectionBox.getStyleClass().addAll("boundary-box", "center-screen-box");
		selectionBox.getChildren().addAll(selectText, selectedCategoryBox, confirmBtn);
		
		_centerBox.getChildren().addAll(header, instruction, unselectedCategoryBox, selectionBox, warning);
		_pane.setCenter(_centerBox);
	}
	
	/**
	 * Add chosen categories to the database model.
	 * @param selectedCategoryBox The parent node that contains all five categories buttons chosen.
	 * @return Return false if storing categories failed (have not selected five categories). Returns true if successful.
	 */
	private boolean storeSelectedCategories(FlowPane selectedCategoryBox) {
		List<Node> selectedButtons = selectedCategoryBox.getChildren();
		
		if (selectedButtons.size() != 5) {
			return false;
		}
		
		String[] categories = new String[selectedButtons.size()];
		
		for (int i = 0; i < categories.length; i++) {
			categories[i] = ((Button)selectedButtons.get(i)).getText();
		}
		_questionBank.storeCategories(categories);
		
		return true;
	}

}
