package view;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * This class represents the screen that gets displayed when the user has submitted the correct
 * answer or used all three attempts.
 * 
 * @author Sherman Chin, Dave Shin
 */
public class PracticeSolutionScreen extends SolutionScreen{
	
	private String _clue;
	
	public PracticeSolutionScreen(BorderPane pane, String clue, String solution) {
		super(pane, solution, new Button("Return to Practice Module"));
		_clue = clue;
	}
	
	/**
	 * Create GUI texts informing user is correct.
	 */
	public void displayCorrect() {
		Text correctMsg = new Text("Correct!");
		correctMsg.getStyleClass().addAll("normal-text", "information-text");

		speak("Correct");
		
		List<Text> messages = new ArrayList<Text>();
		messages.add(correctMsg);
		setUpAndShow(messages);
	}
	
	/**
	 * Create GUI texts informing user is not correct
	 */
	public void displayIncorrect() {
		Text clueMsg = new Text("Clue: " + _clue);
		clueMsg.getStyleClass().addAll("normal-text", "information-text");
		clueMsg.setWrappingWidth(500);
		clueMsg.setTextAlignment(TextAlignment.CENTER);
		
		speak("The actual answer is " + _solution);
		
		Text answerMsg = new Text("Answer: " + _solution);
		answerMsg.getStyleClass().addAll("normal-text", "information-text");
		
		// Sound out to the user that their attempt is incorrect and tell them the correct answer.
		
		List<Text> messages  = new ArrayList<Text>();
		messages.add(clueMsg);
		messages.add(answerMsg);
		setUpAndShow(messages);
	}
	
	/**
	 * Create GUI components and layout screen for practice solution screen
	 * @param messages The texts informing if user is correct / incorrect
	 */
	private void setUpAndShow(List<Text> messages) {
		handleEvents();
				
		VBox solutionBox = new VBox();
		solutionBox.getStyleClass().add("center-screen-box");
		
		solutionBox.getChildren().addAll(messages);
		solutionBox.getChildren().addAll(_returnBtn);
		_pane.setCenter(solutionBox);
	}
	
	/**
	 * Add listeners to the return button back to Practice Module.
	 */
	private void handleEvents() {
		_returnBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
        	public void handle(ActionEvent event) {
        		PracticeModule practiceMod = new PracticeModule(_pane);
        		practiceMod.display();
        	}
		});
	}
}
