package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * This class represents the screen that shows when the user tries to answer the question.
 * 
 * @author Dave Shin
 *
 */
public class GamesAnswerScreen extends AnswerScreen{
	
	private Button _dontKnowBtn;
	private Question _question;
	
	public GamesAnswerScreen(BorderPane pane, Question question) {
		super(pane, question.getClue());
		
		_dontKnowBtn = new Button("Don\'t know");
		_dontKnowBtn.getStyleClass().add("normal-button");
		
		_question = question;
	}
	
	protected void createGUI() {			
		HBox inputAndSoundBtn = new HBox();
		inputAndSoundBtn.getStyleClass().add("center-screen-box");
		
		Text instruction = new Text("Listen to the clue then answer the question.");
		instruction.getStyleClass().addAll("normal-text", "information-text");
		
		inputAndSoundBtn.getChildren().addAll(_attemptInput, _repeatBtn);
		
		HBox buttonBox = new HBox();
		buttonBox.getStyleClass().add("center-screen-box");
		buttonBox.getChildren().addAll(_submitBtn, _dontKnowBtn);
		
		_centerBox.getChildren().addAll(instruction, inputAndSoundBtn, buttonBox, _soundAdjustBox);
		_pane.setCenter(_centerBox);
	}
	
	/**
	 * Adds listeners to different buttons.
	 */
	protected void handleEvents() {
		super.handleEvents();
		_submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				boolean loseScore = true;
				
				String attempt = _attemptInput.getText();
				
				// Make it case-insensitive and trim all leading and trailing spaces.
				attempt = attempt.toLowerCase();
				attempt = attempt.trim();
				
				SolutionScreen solScrn = new GamesSolutionScreen(_pane, _question, 
						_question.getSolution()[0]);
				
				// If the attempt is an empty string, it gets treated as the same way when the Don't Know 
				// button is pressed.
				if (attempt.equals("")) {
					solScrn.displayDontKnow();
					loseScore= false;
				}
				
				// A for loop is used because there can be multiple solutions and we want to 
				// check if the attempt matches with at least one solution.
				for (String solution : _question.getSolution()) {
					solution = solution.toLowerCase();
					solution = solution.trim();
					
					//For clues that have multiple answers separated by ","
					if (solution.contains(",")) {
						String[] solutions = solution.split(",");
						String[] attemptAns = attempt.split(",");
						if (solutions.length == attemptAns.length) {
							int count = 0;
							for (int i = 0; i < solutions.length; i++) {
								if (solutions[i].trim().equals(attemptAns[i].trim())) {
									count++;
								}
							}
							if (count == solutions.length) {
								loseScore = false;
								solScrn.displayCorrect();
							}
						}
					} else if (attempt.equals(solution)) {
						solScrn.displayCorrect();
						loseScore = false;
					}
				}
				
				if (loseScore) {
					solScrn.displayIncorrect();
				}
			}
		});
		
		_dontKnowBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				SolutionScreen solScrn = new GamesSolutionScreen(_pane, _question,
						_question.getSolution()[0]);
				solScrn.displayDontKnow();
			}
		});
	}
}
