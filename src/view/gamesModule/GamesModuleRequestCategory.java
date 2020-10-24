package view.gamesModule;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import model.QuestionBank;
import view.Module;

/**
 * This class represents the screen where users choose five categories to play.
 * @author se2062020
 *
 */
public class GamesModuleRequestCategory extends Module {

	public GamesModuleRequestCategory(BorderPane pane) {
		super(pane, new QuestionBank(true));
	}

	@Override
	protected void displayScreen() {
		
		Text header = new Text("Games Module!");
		header.getStyleClass().add("header-msg");
		
		Text instruction = new Text("Please choose any five categories");
		instruction.getStyleClass().add("normal-text");
		
		Text warning = new Text("Only five categories!");
		warning.getStyleClass().addAll("normal-text", "invisible-component");
		
		FlowPane unselectedCategoryBox = new FlowPane();
		unselectedCategoryBox.getStyleClass().add("category-grid");
				
		FlowPane selectedCategoryBox = new FlowPane();
		selectedCategoryBox.getStyleClass().addAll("center-screen-box");
		
		String[] categories = _questionBank.requestCategory();
		
		//Add listeners to category buttons 
		for (String category: categories) {
			Button categoryBtn = new Button(category);
			categoryBtn.getStyleClass().add("golden-button");
			
			categoryBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					System.out.println(categoryBtn.getParent().toString());
					
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
				storeSelectedCategories(selectedCategoryBox);
				GamesModule gamesMod = new GamesModule(_pane);
				gamesMod.display();
			}
		});
		
		_centerBox.getChildren().addAll(header, instruction, unselectedCategoryBox, selectedCategoryBox, warning, confirmBtn);
		
		_pane.setCenter(_centerBox);
	}
	
	/**
	 * Add chosen categories to the database model.
	 * @param selectedCategoryBox The parent node that contains all five categories buttons chosed.
	 */
	private void storeSelectedCategories(FlowPane selectedCategoryBox) {
		List<Node> selectedButtons = selectedCategoryBox.getChildren();
		String[] categories = new String[selectedButtons.size()];
		
		for (int i = 0; i < categories.length; i++) {
			categories[i] = ((Button)selectedButtons.get(i)).getText();
		}
		_questionBank.storeCategories(categories);
	}

}
